package com.example.bootcamp.webflux.demobootcampreactive.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** The type Student. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Students")
public class Student {


  @Id private String id;
  private String fullName;
  @NotEmpty
  private String gender;

  @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
  private LocalDate birthday;
  private String address;
  private String academicPeriod;
  private String typeDocument;
  @Size(min = 8, max = 8)
  private String document;



  /**
   * Instantiates a new Student.
   *
   * @param fullName the full name
   * @param gender the gender
   * @param birthday the birthday
   * @param address the address
   * @param academicPeriod the academic period
   * @param typeDocument the type document
   * @param document the document
   */

  }




