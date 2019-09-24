package com.example.bootcamp.webflux.demobootcampreactive.controller;

import com.example.bootcamp.webflux.demobootcampreactive.model.Student;
import com.example.bootcamp.webflux.demobootcampreactive.service.StudentService;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** The type Student controller. */
@RestController
@RequestMapping("/api/v1.0/students")
public class StudentController {

  private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

  @Autowired
  private StudentService studentService;

  /**
   * Create mono.
   *
   * @param student the student
   * @return the mono
   */

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Student> create(@Valid @RequestBody Student student) {
    return
      studentService.save(student);
  }


  /**
   * Find all mono.
   *
   * @return the mono
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<Student>>> findAll() {

    logger.info("Request received at the controller. findall: " );
    return Mono.just(
      ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(studentService.findAll()));
  }

  /**
   * Find by id mono.
   *
   * @param id the id
   * @return the mono
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Student>> findById(@PathVariable String id) {
    logger.info("Request received at the controller. PersonId: " + id);
    return studentService
      .findById(id)
      .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p));

  }



  /**
   * Update mono.
   *
   * @param student the student
   * @param id      the id
   * @return the mono
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Student>> update(
    @RequestBody Student student, @PathVariable String id) {
    return studentService
      .findById(id)
      .flatMap(
        p -> {
          p.setFullName(student.getFullName());
          p.setGender(student.getGender());
          p.setBirthday(student.getBirthday());
          p.setAddress(student.getAddress());
          p.setAcademicPeriod(student.getAcademicPeriod());
          p.setTypeDocument(student.getTypeDocument());
          p.setDocument(student.getDocument());

          return studentService.save(p);
        })
      .map(
        p ->

          ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Eliminar mono.
   *
   * @param id the id
   * @return the mono
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id){
    return studentService.findById(id).flatMap(p ->
      studentService.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
      .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));

  }



  /**
   * Find by document mono.
   *
   * @param document the document
   * @return the mono
   */


  @GetMapping("document/{document}")
  public  Mono<ResponseEntity<Student>> findByDocument(@PathVariable String document) {
    return studentService
      .findByDocument(document)
      .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p));

  }


  /**
   * Find by full name mono.
   *
   * @param name the name
   * @return the mono
   */
  @GetMapping("name/{name}")
  public Mono<ResponseEntity<Student>> findByFullName(@PathVariable String name) {
    return studentService
      .findByFullName(name)
      .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());

  }

  /**
   * Find bybirthday between flux.
   *
   * @param date1 the date 1
   * @param date2 the date 2
   * @return the flux
   */
  @GetMapping("date/{date1}/{date2}")
  public Flux<Student> findBybirthdayBetween(
      @PathVariable("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
      @PathVariable("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
    return studentService.findBybirthdayBetween(date1, date2);
  }
}
