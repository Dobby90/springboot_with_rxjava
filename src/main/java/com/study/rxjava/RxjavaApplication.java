package com.study.rxjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.reactivex.Observable;

@SpringBootApplication
public class RxjavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RxjavaApplication.class, args);

		/**
		 * RxJava Example
		 */
		// 1
		Observable.just("Hello", "World")
		.subscribe(System.out::println); // 발행을 해야 메서드가 실행 됨.

	}

}
