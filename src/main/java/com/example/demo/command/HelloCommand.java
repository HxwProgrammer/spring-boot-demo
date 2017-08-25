package com.example.demo.command;

import org.springframework.stereotype.Component;

import com.example.demo.message.hello.HelloReq;
import com.example.demo.message.hello.HelloRes;
import com.example.demo.message.hello.WorldReq;
import com.example.demo.message.hello.WorldRes;
import com.example.demo.mvc.annotation.JarvisCommand;
import com.example.demo.mvc.annotation.JarvisController;

import lombok.extern.slf4j.Slf4j;

/**
 * packet example
 * url: ws://localhost:10001/api
 * body: {"command":"/helloCommand/hello","params":{"id":1,"name":"guest"}}
 */
@Slf4j
@Component
@JarvisController
public class HelloCommand {

	@JarvisCommand(uri = "/hello", description = "hello")
	public HelloRes hello(final HelloReq request) {
		log.info("hello:{}", request);
		return new HelloRes(request);
	}

	@JarvisCommand(uri = "/world", description = "world")
	public WorldRes world(final WorldReq request) {
		log.info("world:{}", request);
		return new WorldRes(request);
	}

}
