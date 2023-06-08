package com.example.demo.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;

import jakarta.annotation.*;
import jakarta.servlet.*;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.*;
import software.amazon.awssdk.services.s3.*;

@Configuration
@EnableMethodSecurity
public class CustomConfig {

	@Value("${aws.accessKeyId}")
	private String accessKey;

	@Value("${aws.secretAccessKey}")
	private String secretKey;

	@Value("${aws.s3.bucketUrl}")
	private String bucketUrl;

	@Autowired
	private ServletContext application;

	// 빈이 만들어지자마자 바로 실행해라
	@PostConstruct
	public void init() {
		application.setAttribute("bucketUrl", bucketUrl);
	}

	@Bean
	public S3Client s3client() {

		AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
		AwsCredentialsProvider provider = StaticCredentialsProvider.create(credentials);


		S3Client s3client = S3Client.builder().credentialsProvider(provider).region(Region.AP_NORTHEAST_2).build();

		return s3client;

	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	// 암호 필터할 때 사용하는 것 
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception {
		
		// csrf 보안을 사용하지 않겠다는 것
		http.csrf().disable();
		
		http.formLogin().loginPage("/login").defaultSuccessUrl("/main");
		
		// 로그아웃 페이지 설정
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/main");
		

		
		return http.build();
	}

}
