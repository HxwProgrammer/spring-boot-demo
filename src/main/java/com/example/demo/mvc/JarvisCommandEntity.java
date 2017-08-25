package com.example.demo.mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
public class JarvisCommandEntity {
	private String beanName;
	private Map<String, Method> methodMap = new HashMap<>();
}
