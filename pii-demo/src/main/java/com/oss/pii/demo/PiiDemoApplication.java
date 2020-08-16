package com.oss.pii.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oss.pii.demo.config.GenericMaskSerializer;
import com.oss.pii.demo.entity.Customer;
import com.oss.pii.demo.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PiiDemoApplication implements CommandLineRunner {
	private final CustomerService service;

	public PiiDemoApplication(CustomerService service) {
		this.service = service;
	}

//	@Bean
//	public ObjectMapper objectMapper(){
//		System.out.println("********************************");
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.registerModule(GenericMaskSerializer.getModule(mapper));
//		return mapper;
//	}

	public static void main(String[] args) {
		SpringApplication.run(PiiDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.saveCustomer(new Customer("Venkat","R","2000200020023233"));
		service.saveCustomer(new Customer("John","C","2227266266262662"));
		service.saveCustomer(new Customer("Peter","M","3343434234232223"));
		service.saveCustomer(new Customer("Nick","G","1332234234232223"));
	}
}
