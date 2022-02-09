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

		// 2-1
		Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
		.filter(f -> f % 2 == 0)	// 짝수에 해당하는 데이터만 통지한다
		.map(f -> f * 100)			// 데이터를 100배로 변환한다
		.subscribe(f -> System.out.println("data=" + f));

	}

}
