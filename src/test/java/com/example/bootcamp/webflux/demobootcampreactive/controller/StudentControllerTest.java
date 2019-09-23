package com.example.bootcamp.webflux.demobootcampreactive.controller;

import com.example.bootcamp.webflux.demobootcampreactive.model.Student;
import com.example.bootcamp.webflux.demobootcampreactive.service.impl.StudentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

/** The type Student controller test. */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
public class StudentControllerTest {

  @Autowired
  private WebTestClient client;

  @MockBean
  private StudentServiceImpl studentService;


  /**
   * Create.
   */
  @Test
  public void create() {

    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("73674232");

    studentService.save(student);

    client
      .post()
      .uri("/api/v1.0/students")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .accept(MediaType.APPLICATION_JSON_UTF8)
      .body(Mono.just(student), Student.class)
      .exchange()
      .expectStatus()
      .isCreated()

    ;

  }

  @Test
  public void findAll() {

    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("73674232");

    when(studentService.findAll()).thenReturn(Flux.just(student));


    client
      .get()
      .uri("/api/v1.0/students")
      .accept(MediaType.APPLICATION_JSON_UTF8)
      .exchange()
      .expectStatus()
      .isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .expectBodyList(Student.class)
    ;

  }

  @Test
  public void findById() {

    Student student = new Student();
    student.setId("S123");
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("73674232");


    when(studentService.findById(student.getId())).thenReturn(Mono.just(student));

    client
      .get()
      .uri("/api/v1.0/students" + "/S123")
      .accept(MediaType.APPLICATION_JSON_UTF8)
      .exchange()
      .expectStatus()
      .isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .expectBody(Student.class)
      .consumeWith(
        response -> {
          Student p = response.getResponseBody();
          Assertions.assertThat(p.getId()).isNotEmpty();
          Assertions.assertThat(p.getId().length() > 0).isTrue();
        });
  }

  @Test
  public void update() {


  }


  @Test
  public void findByDocument() {

    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("73674232");

    when(studentService.findByDocument(student.getDocument())).thenReturn(Mono.just(student));


    client
      .get()
      .uri("/api/v1.0/students/document/73674232")
      .accept(MediaType.APPLICATION_JSON_UTF8)
      .exchange()
      .expectStatus()
      .isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .expectBody(Student.class)
      .consumeWith(
        response -> {
          Student p = response.getResponseBody();
          Assertions.assertThat(p.getDocument()).isNotEmpty();
          Assertions.assertThat(p.getDocument().length() > 0).isTrue();
        });
  }


  @Test
  public void findByFullName() {

    Student student = new Student();
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("73674232");

    when(studentService.findByFullName(student.getFullName())).thenReturn(Mono.just(student));


    client
      .get()
      .uri("/api/v1.0/students/name/cristohper")
      .accept(MediaType.APPLICATION_JSON_UTF8)
      .exchange()
      .expectStatus()
      .isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .expectBody(Student.class)
      .consumeWith(
        response -> {
          Student p = response.getResponseBody();
          Assertions.assertThat(p.getFullName()).isNotEmpty();
          Assertions.assertThat(p.getFullName().length() > 0).isTrue();
        });
  }

  @Test
  public void eliminar() {
    Student student = new Student();
    student.setId("1234");
    student.setFullName("cristohper");
    student.setGender("male");
    student.setBirthday(LocalDate.of(1965, 01, 25));
    student.setAddress("jwe2932");
    student.setAcademicPeriod("3 Cycle");
    student.setTypeDocument("dni");
    student.setDocument("73674232");

    client
      .delete()
      .uri("/api/v1.0/students/1234")
      .accept(MediaType.APPLICATION_JSON_UTF8)
      .exchange()
      .expectStatus()
      .isOk();

  }
}