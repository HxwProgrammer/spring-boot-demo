package com.example.demo04;

import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetOrderCommand extends HystrixCommand<Order> {

	private final int orderId;

	protected GetOrderCommand(final int orderId) {
		super(Setter
			.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
			.andCommandKey(HystrixCommandKey.Factory.asKey("GetOrderCommand"))
			.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetOrderCommandPool"))
		);
		this.orderId = orderId;
	}

	// 커맨드 실행
	@Override
	protected Order run() throws Exception {
		//TimeUnit.SECONDS.sleep(3); // fallback 테스트 용으로 실패하게 하려면 3초 Thread sleep 을 준다.
		return new Order(orderId, "success");
	}

	// 커맨드 실행 실패시 fallback 메소드
	@Override
	protected Order getFallback() {
		return new Order(-1, "fallback");
	}

	public static void main(String[] args) throws Exception {
		// HystrixCommand 기본설정
		// Timeout hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds = 1000

		// execute() : Synchronous Execution
		Order result = new GetOrderCommand(1).execute();
		log.info("result:{}", result);

		// queue() : Asynchronous Execution
		Order asyncResult = new GetOrderCommand(2).queue().get();
		log.info("asyncResult:{}", asyncResult);

		TimeUnit.SECONDS.sleep(3);

	}
}

@Data
class Order {
	private int orderId;
	private String name;

	public Order(final int orderId) {
		this.orderId = orderId;
	}

	public Order(final int orderId, final String name) {
		this.orderId = orderId;
		this.name = name;
	}
}
