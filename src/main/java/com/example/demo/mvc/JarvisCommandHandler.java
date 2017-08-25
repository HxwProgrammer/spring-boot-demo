package com.example.demo.mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.example.demo.mvc.annotation.JarvisCommand;
import com.example.demo.mvc.annotation.JarvisController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JarvisCommandHandler {

	private static Map<String, JarvisCommandEntity> jarvisCommandHolder = new HashMap<>();

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void initialize() {
		final Map<String, Object> list = applicationContext.getBeansWithAnnotation(JarvisController.class);

		list.forEach((k, v) -> {
			// @JarvisController bean
			final Object bean = applicationContext.getBean(k);
			final Method[] methods = bean.getClass().getMethods();

			final JarvisCommandEntity entity = new JarvisCommandEntity();
			entity.setBeanName(k);

			for (final Method m : methods) {
				final JarvisCommand command = m.getDeclaredAnnotation(JarvisCommand.class);
				if (command != null && !"".equals(command.uri())) {
					log.info("JarvisCommand method:{} uri:{}, desc:{}", m.getName(), command.uri(), command.description());
					entity.getMethodMap().put(command.uri(), m);
					jarvisCommandHolder.put(k, entity);
				}
			}
		});

		jarvisCommandHolder.forEach((k, v) -> log.info("jarvisCommand:{} {}", k, v));
	}

	public static JarvisCommandEntity getJarvisCommandEntityMap(final String command) {
		return jarvisCommandHolder.get(command);
	}

}
