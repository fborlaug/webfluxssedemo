package com.julejava.webfluxssedemo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Person {

  private String id;
  private String name;
  private Integer age;

  public Person(String name, Integer age) {
    this.name = name;
    this.age = age;
  }
}