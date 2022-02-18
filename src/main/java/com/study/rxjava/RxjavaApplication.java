package com.study.rxjava;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.reactivex.Flowable;
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
		.subscribe(f -> System.out.println("2-1) data=" + f));

		// 2-2
		Flowable<Integer> flowable = Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
		.filter(f -> f % 2 == 0)	// 짝수에 해당하는 데이터만 통지한다
		.map(f -> f * 100);			// 데이터를 100배로 변환한다

		// 구독하고 받은 데이터를 출력한다
		flowable.subscribe(f -> System.out.println("2-2) data=" + f));

		// 2-3
		Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)	// 인자의 데이터를 순서대로 통지하는 Flowable을 생성한다
		.filter(f -> f % 2 == 0)	// 짝수에 해당하는 데이터만 통지한다
		.map(f -> f * 100)			// 데이터를 100배로 변환한다
		.subscribe(System.out::println);

		// 3-1. 일반적인 방법
		// 리스트에서 Iterator를 얻는다
		List<String> listArr = Arrays.asList("a", "b", "c");
		Iterator<String> iterator = listArr.iterator();
		while (iterator.hasNext()) {
			String value = iterator.next();
			System.out.println(value);
		}

		// 3-2. RxJava
		// 리스트로 Flowable을 생성한다
		List<String> listFlow = Arrays.asList("a", "b", "c");
		Flowable<String> flowableList = Flowable.fromIterable(listFlow);

		// 처리를 시작한다
		flowableList.subscribe(System.out::println);

	}

}
