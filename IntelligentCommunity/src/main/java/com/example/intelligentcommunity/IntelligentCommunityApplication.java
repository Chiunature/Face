package com.example.intelligentcommunity;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.intelligentcommunity.mapper")
public class IntelligentCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntelligentCommunityApplication.class, args);
	}

}
