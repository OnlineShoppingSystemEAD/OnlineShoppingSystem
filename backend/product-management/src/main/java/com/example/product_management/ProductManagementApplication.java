package com.example.product_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class ProductManagementApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ProductManagementApplication.class, args);

		Item item = context.getBean(Item.class);

	}

}
