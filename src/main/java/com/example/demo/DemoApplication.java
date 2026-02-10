package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.util.AESUtil;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		String plain = "Raji@2728";

	    String encrypted = AESUtil.encrypt(plain);
	    String decrypted = AESUtil.decrypt(encrypted);

	    System.out.println("Plain     : " + plain);
	    System.out.println("Encrypted : " + encrypted);
	    System.out.println("Decrypted : " + decrypted);
		SpringApplication.run(DemoApplication.class, args);
	}

}
