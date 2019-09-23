package com.example.bootcamp.webflux.demobootcampreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@EnableSwagger2WebFlux
@SpringBootApplication
@EnableCircuitBreaker
public class DemoBootcampReactiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoBootcampReactiveApplication.class, args);
  }
}
