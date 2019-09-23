package com.example.bootcamp.webflux.demobootcampreactive.service;

import com.example.bootcamp.webflux.demobootcampreactive.model.Student;
import java.time.LocalDate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/** The interface Student service. */
public interface StudentService {

  /**
   * Find all flux.
   *
   * @return the flux
   */
  public Flux<Student> findAll();

  /**
   * Find by id mono.
   *
   * @param id the id
   * @return the mono
   */
  public Mono<Student> findById(String id);

  /**
   * Save mono.
   *
   * @param producto the producto
   * @return the mono
   */
  public Mono<Student> save(Student producto);

  /**
   * Delete mono.
   *

   */
  public Mono<Void> delete(Student student);



  /**
   * Find by document mono.
   *
   * @param document the document
   * @return the mono
   */
  public Mono<Student> findByDocument(String document);

  /**
   * Find full name mono.
   *
   * @param name the name
   * @return the mono
   */
  public Mono<Student> findByFullName(String name);

  /**
   * Find bybirthday between flux.
   *
   * @param date1 the date 1
   * @param date2 the date 2
   * @return the flux
   */
  public Flux<Student> findBybirthdayBetween(LocalDate date1, LocalDate date2);

}
