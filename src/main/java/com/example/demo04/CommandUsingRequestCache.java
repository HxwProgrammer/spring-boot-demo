package com.example.demo04;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandUsingRequestCache extends HystrixCommand<Boolean> {

	private final int value;

	protected CommandUsingRequestCache(final int value) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.value = value;
	}

	@Override
	protected Boolean run() throws Exception {
		return value == 0 || value % 2 == 0;
	}

	@Override
	protected String getCacheKey() {
		return String.valueOf(value);
	}

	public static void main(String[] args) {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();

		Boolean result1 = new CommandUsingRequestCache(2).execute();
		log.info("result1:{}", result1);

		Boolean result2 = new CommandUsingRequestCache(1).execute();
		log.info("result2:{}", result2);

		Boolean result3 = new CommandUsingRequestCache(0).execute();
		log.info("result3:{}", result3);

		Boolean result4 = new CommandUsingRequestCache(58672).execute();
		log.info("result4:{}", result4);

		// command response cache 테스트
		CommandUsingRequestCache cacheCommand1 = new CommandUsingRequestCache(333);
		CommandUsingRequestCache cacheCommand2 = new CommandUsingRequestCache(333);

		log.info("cacheCommand1, result:{}, isResponseFromCache:{}", cacheCommand1.execute(), cacheCommand1.isResponseFromCache()); // isResponseFromCache : false
		log.info("cacheCommand2, result:{}, isResponseFromCache:{}", cacheCommand2.execute(), cacheCommand2.isResponseFromCache()); // isResponseFromCache : true

		context.shutdown();
	}

}
