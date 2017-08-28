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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JarvisMessageHandler {

	@Autowired
	private Gson gson;

	@Autowired
	private ApplicationContext applicationContext;

	public void execute(final WebSocketSession session, final String payload) throws Exception {
		try {

			if (StringUtils.isEmpty(payload)) {
				throw new IllegalStateException("payload is empty");
			}

			final JSONObject jo = new JSONObject(payload);
			final String command = jo.getString("command");

			if (command == null) {
				throw new IllegalStateException("could not find command");
			}

			final String[] commandArray = command.split("/", 3);

			String controllerClassName = null;
			final StringBuilder controllerMethodName = new StringBuilder("/");
			for (String c : commandArray) {
				if ("".equals(c)) {
					continue;
				}

				if (controllerClassName == null) {
					controllerClassName = c;
					continue;
				}

				controllerMethodName.append(c);
			}

			final JarvisCommandEntity entity = JarvisCommandHandler.getJarvisCommandEntityMap(controllerClassName);

			if (entity == null) {
				throw new IllegalStateException("could not find command");
			}

			final String params = jo.getString("params");

			final Object clazz = applicationContext.getBean(entity.getBeanName());

			if (clazz == null) {
				throw new IllegalStateException("could not find command");
			}

			final Method method = entity.getMethodMap().get(controllerMethodName.toString());

			if (method == null) {
				throw new IllegalStateException("could not find command");
			}

			Object p = null;

			final Class<?>[] paramTypes = method.getParameterTypes();

			if (paramTypes.length > 0) {
				Class<?> pType = paramTypes[1];
				p = gson.fromJson(params, pType);
			}

			log.info("paramTypes:{}", p);

			Object responseData = null;
			try {
				responseData = method.invoke(clazz, session, p);
			} catch (Exception e) {
				e.printStackTrace();
			}

			log.info("responseData:{}", responseData);

			session.sendMessage(new TextMessage(gson.toJson(new JarvisResponseEntity(ChatStatusCode.OK, responseData))));

		} catch (Exception e) {
			e.printStackTrace();
			session.sendMessage(new TextMessage(gson.toJson(new JarvisResponseEntity(ChatStatusCode.INTERNAL_SERVER_ERROR, new JarvisErrorEntity(e.getMessage())))));
		}

	}

}
