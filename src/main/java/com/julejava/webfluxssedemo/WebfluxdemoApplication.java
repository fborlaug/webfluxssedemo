package com.julejava.webfluxssedemo;

import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.julejava.webfluxssedemo.domain.Person;
import com.julejava.webfluxssedemo.repository.PersonRepository;

@SpringBootApplication
public class WebfluxdemoApplication {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public static void main(String[] args) {
    SpringApplication.run(WebfluxdemoApplication.class, args);
  }

  @Bean
  CommandLineRunner process(PersonRepository repo) {
    return args -> {
      Stream
          .of("Thorin", "Balin", "Bifur", "Bofur", "Bombur", "Dori", "Dwalin", "Fili", "Gloin", "Kili", "Nori", "Oin")
          .forEach(name -> repo.save(new Person(name, new Random().nextInt(40))));

      repo.findAll().forEach(s -> logger.info(s.getName()));
    };
  }
}
