package com.example.myword;

import com.example.myword.service.S3Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MywordApplication {

	public static void main(String[] args) {
		SpringApplication.run(MywordApplication.class, args);
	}

}
