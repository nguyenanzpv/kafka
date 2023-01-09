package com.java.accountService;

import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.ui.ModelMap;

@SpringBootApplication
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setFieldMatchingEnabled(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE) //su dung thuoc tinh private
				.setMatchingStrategy(MatchingStrategies.STRICT); //map chinh xac cac ten
		return modelMapper;
	}

	@Bean
	NewTopic notification(){
		//topic name, partition number,replication number
		//replication thuong bang so luong broker
		return new NewTopic("notification",2,(short)1);
	}

	@Bean
	NewTopic statistic(){
		//topic name, partition number,replication number
		//replication thuong bang so luong broker
		return new NewTopic("statistic",1,(short)1);
	}
}
