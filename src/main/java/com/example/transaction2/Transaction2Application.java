package com.example.transaction2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication()
@EnableScheduling
public class Transaction2Application {

	public static void main(String[] args) {
		SpringApplication.run(Transaction2Application.class, args);
	}
}
