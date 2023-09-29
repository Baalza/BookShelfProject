package com.bookshelf2.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private PasswordEncoder encoder;
	@Test
	void contextLoads() {
		System.out.println(encoder.encode("pass"));
	}

}
