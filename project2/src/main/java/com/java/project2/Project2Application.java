package com.java.project2;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
//tu gen created date, update date...
//auditing truoc khi save data to db
@EnableJpaAuditing
//@EnableScheduling
@EnableAsync
public class Project2Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Project2Application.class, args);
	}

	//bean luu giu lang mac dinh
	@Bean
	public LocaleResolver localeResolver(){
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("en"));
		return slr;
	}
	//hello?lang=en ; kiem trang request co lang hay ko
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor(){
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}
	//dang ky local vao registry cua spring
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setFieldMatchingEnabled(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE) //su dung thuoc tinh private
				.setMatchingStrategy(MatchingStrategies.STRICT); //map chinh xac cac ten
		return modelMapper;
	}

	//Quartz
	/*@Scheduled(fixedDelay = 5000)
	public void hello(){
		System.out.println("hello");
	}*/

	@Bean
	JsonMessageConverter converter(){
		return new JsonMessageConverter();
	}

}
