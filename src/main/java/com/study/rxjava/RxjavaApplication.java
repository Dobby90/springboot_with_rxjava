package com.study.rxjava;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.subscribers.DisposableSubscriber;

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

		// 4. Sleep
		Flowable.just("a!!!!", "b!!!!", "c!!!!")
		.flatMap( f -> {	
		return Flowable.just(f).delay(3000L, TimeUnit.MILLISECONDS); // 1000밀리초 늦게 데이터를 통지하는 Flowable을 생성한다
		})
		.subscribe(System.out::println);

		// 5-1. Error Handling
		Flowable.just(1, 3, 5, 0, 2, 4)
		// 받은 데이터로 100을 나눈다
		.map(f -> 100 / f)
		// .onErrorReturnItem(0) // 에러가 발생하면 0을 통지한다
		.subscribe(
			new DisposableSubscriber<Integer>() {
				@Override
				public void onNext(Integer data) {
				System.out.println("data=" + data);
				}

				@Override
				public void onError(Throwable error) {
				System.out.println("error1=" + error);
				}

				@Override
				public void onComplete() {
				System.out.println("완료");
				}
			}
		);

		// 5-2
		Flowable.just(1, 3, 5, 0, 2, 4)
		.map(f -> 100 / f) // 받은 데이터로 100을 나눈다
		.onErrorReturnItem(9999) // 에러가 발생하면 0을 통지한다
		.subscribe( f -> {
		System.out.println("data=" + f);
		},
		err -> System.out.println("error2=" + err),
		() -> System.out.println("완료") // 세 번째 인자: 완료 통지 시
		); // 구독한다

		// 6. FlatMap
		Flowable.just("A", "", "B", "", "C") // 인자의 데이터를 통지하는 Flowable을 생성한다
		.flatMap(f -> { // flatMap 메서드로 빈 문자를 제거하거나 소문자로 변환한다
			if ("".equals(f)) {
				return Flowable.empty(); // 빈 문자라면 빈 Flowable을 반환한다
			} else {
				return Flowable.just(f.toLowerCase()); // 소문자로 변환한 데이터가 담긴 Flowable을 반환한다
			}
		})
		.subscribe(System.out::println);

		// 7. OnNext
		Flowable.range(1, 5)
		.doOnNext(data -> System.out.println("--- 기존 데이터: " + data)) // 데이터 통지 시 로그를 출력한다
		.filter(data -> data % 2 == 0) // 짝수만 통지한다
		.doOnNext(data -> System.out.println("------ filter 적용 후 데이터: " + data)) // 데이터 통지 시 로그를 출력한다
		.subscribe(System.out::println);

		// 8. OnError
		Flowable.range(1, 5)
		.doOnError(error -> System.out.println("기존 데이터: " + error.getMessage())) // 에러 통지 시 로그를 출력한다
		.map(data -> {
			if (data == 3) { // 데이터가 '3'일 때 에러가 발생한다
				throw new Exception("예외 발생");
			}
		return data;
		})
		.doOnError(error -> System.out.println("--- map 적용 후: " + error.getMessage())) // 에러 통지 시 로그를 출력한다
		.onErrorReturn(e -> {
			if(e instanceof NumberFormatException) {
				e.printStackTrace();
			}
			else {
				e.getMessage();
			}
		return -1;
		})
		.subscribe(System.out::println);

		// 9. OnComplete
		Flowable.range(1, 5)
		.doOnComplete(() -> System.out.println("doOnComplete")) // 완료 통지 시 로그를 출력한다
		.subscribe(System.out::println
		,
		err -> System.out.println("error3=" + err),
		() -> System.out.println("완료") // 세 번째 인자: 완료 통지 시
		);

	}

}
