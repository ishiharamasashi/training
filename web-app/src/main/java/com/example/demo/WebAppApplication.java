package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Bootアプリケーションのエントリーポイントとなるクラスです。
 * <p>
 * このクラスは{@code @SpringBootApplication} アノテーションが付与されており、Spring
 * Bootアプリケーションのメインクラスとして機能します。
 * </p>
 */
@SpringBootApplication
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}

}
