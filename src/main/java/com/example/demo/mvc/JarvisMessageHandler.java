package com.example.demo.mvc;

import java.lang.reflect.Method;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.constant.chat.ChatStatusCode;
import com.example.demo.message.JarvisErrorEntity;
import com.example.demo.message.JarvisResponseEntity;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JarvisMessageHandler {

	@Autowired
	private Gson gson;

	@Autowired
	private ApplicationContext applicationContext;

	@AllArgsConstructor
	class ParsedJsonCommand {
		String[] commandArray;
		String params;
	}

	class ControllerInfo {
		String controllerClassName;
		String controllerMethodName;
	}

	class CommandInfo {
		Object clazz;
		Method method;
	}

	public void execute(final WebSocketSession session, final String payload) throws Exception {
		try {
			final ParsedJsonCommand parsedInfo = parseCommand(payload);
			final String params = parsedInfo.params;

			final ControllerInfo controllerInfo = getControllerInfo(parsedInfo);
			final JarvisCommandEntity entity = JarvisCommandHandler.getJarvisCommandEntityMap(controllerInfo.controllerClassName);

			final CommandInfo commandInfo = getCommandInfo(entity, controllerInfo.controllerMethodName);
			final Object p = prepareInvokeParams(commandInfo.method, params);

			Object res = null;
			try {
				res = commandInfo.method.invoke(commandInfo.clazz, session, p);
			} catch (Exception e) {
				e.printStackTrace();
			}

			final String responseJson = gson.toJson(new JarvisResponseEntity(ChatStatusCode.OK, res));

			session.sendMessage(new TextMessage(responseJson));

			log.info("requestJson : {}", payload);
			log.info("responseJson : {}", responseJson);

		} catch (Exception e) {
			e.printStackTrace();
			session.sendMessage(new TextMessage(gson.toJson(new JarvisResponseEntity(ChatStatusCode.INTERNAL_SERVER_ERROR, new JarvisErrorEntity(e.getMessage())))));
		}
	}

	private ParsedJsonCommand parseCommand(final String payload) throws Exception {
		if (StringUtils.isEmpty(payload)) {
			throw new IllegalStateException("payload is empty");
		}

		final JSONObject jo = new JSONObject(payload);
		final String command = jo.getString("command");

		if (command == null) {
			throw new IllegalStateException(String.format("could not find command : command is null, payload : %s", payload));
		}

		return new ParsedJsonCommand(command.split("/", 3), jo.getString("params"));
	}

	private ControllerInfo getControllerInfo(final ParsedJsonCommand parsedInfo) throws Exception {
		final ControllerInfo info = new ControllerInfo();
		info.controllerClassName = null;
		StringBuilder controllerMethodName = new StringBuilder("/");
		for (String c : parsedInfo.commandArray) {
			if ("".equals(c)) {
				continue;
			}

			if (info.controllerClassName == null) {
				info.controllerClassName = c;
				continue;
			}

			controllerMethodName.append(c);
		}

		info.controllerMethodName = controllerMethodName.toString();

		return info;
	}

	private CommandInfo getCommandInfo(final JarvisCommandEntity entity, final String controllerMethodName) throws Exception {
		if (entity == null) {
			throw new IllegalStateException("could not find command : JarvisCommandEntity is null");
		}

		final CommandInfo info = new CommandInfo();

		info.clazz = applicationContext.getBean(entity.getBeanName());

		if (info.clazz == null) {
			throw new IllegalStateException(String.format("could not find command : bean (%s) is null ", entity.getBeanName()));
		}

		info.method = entity.getMethodMap().get(controllerMethodName);

		if (info.method == null) {
			throw new IllegalStateException(String.format("could not find command : method (%s) is null", controllerMethodName));
		}

		return info;
	}

	private Object prepareInvokeParams(final Method method, final String params) {
		Object p = null;

		final Class<?>[] paramTypes = method.getParameterTypes();

		if (paramTypes.length > 0) {
			Class<?> pType = paramTypes[1];
			p = gson.fromJson(params, pType);
		}

		log.info("paramTypes:{}", p);

		return p;
	}

}
