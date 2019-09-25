package com.example.bootcamp.webflux.demobootcampreactive.controller;

import com.example.bootcamp.webflux.demobootcampreactive.model.Student;
import com.example.bootcamp.webflux.demobootcampreactive.repository.StudentRepository;
import com.example.bootcamp.webflux.demobootcampreactive.service.impl.StudentServiceImpl;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;

/** The type Student controller test. */
@ExtendWith(SpringExtension.class)
@SpringBootTest

public class StudentControllerTest {

  @Autowired
  private ApplicationContext applicationContext;
  @Autowired
  private StudentRepository studentRepository;
  private WebTestClient client;
  private List<Student> expectedProducts;


  @BeforeEach
  void setUp() {
    client = WebTestClient
      .bindToApplicationContext(applicationContext)
      .configureClient()
      .baseUrl("/api/v1.0/students")
      .build();

    Flux<Student> initData = studentRepository.deleteAll()
      .thenMany(Flux.just(
        Student.builder().id("12345").fullName("omar").gender("male").birthday(LocalDate.of(1993, 2, 3)).academicPeriod("2").address("1").typeDocument("dni").document("73674232").build(),
        Student.builder().id("s1234").fullName("jose").gender("male").birthday(LocalDate.of(1997, 2, 3)).academicPeriod("2").address("1").typeDocument("dni").document("76786787").build())
        .flatMap(studentRepository::save))
      .thenMany(studentRepository.findAll());

    expectedProducts = initData.collectList().block();
  }

 @Test
  void create() {
    Student expectedProduct = expectedProducts.get(0);

    client.post().uri("/").body(Mono.just(expectedProduct), Student.class).exchange()
      .expectStatus().isCreated();


  }

  @Test
  void findAll() {

    client.get().uri("/").exchange()
      .expectStatus().isOk();
  }

  @Test
  void findById() {

    String title = "12345";
    client.get().uri("/{title}", title).exchange()
      .expectStatus().isOk();
  }

  @Test
  void update() {

    Student expectedProduct = expectedProducts.get(0);

    client.put().uri("/{id}", expectedProduct.getId()).body(Mono.just(expectedProduct), Student.class).exchange()
      .expectStatus().isOk();
  }

  @Test
  void eliminar() {

    Student productToDelete = expectedProducts.get(0);
    client.delete().uri("/{id}", productToDelete.getId()).exchange()
      .expectStatus().isNoContent();
  }

  @Test
  void findByDocument() {

    String title = "76786787";
    client.get().uri("/document/{title}", title).exchange()
      .expectStatus().isOk();

  }

  @Test
  void findByFullName() {

    String title = "jose";
    client.get().uri("/name/{title}", title).exchange()
      .expectStatus().isOk();

  }

  @Test
  void findBybirthdayBetween() {

    LocalDate date1 = LocalDate.of(1800,03,02);
    LocalDate date2 = LocalDate.of(2000,03,02);


      client.get().uri("/date/{date1}/{date2}", date1,date2).exchange()
        .expectStatus().isOk().
    expectBodyList(Student.class);

  }


}
