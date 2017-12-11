package com.julejava.webfluxssedemo.repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.julejava.webfluxssedemo.domain.Person;

import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;


@Component
public class PersonRepository {

  private ConcurrentHashMap<String, Person> all = new ConcurrentHashMap<>();

  public void save(Person person) {
    person.setId(randomUUID().toString());
    all.put(person.getId(), person);
  }

  public Stream<Person> getAll() {
    return all.values().stream();
  }

  public Optional<Person> findById(String id) {
    return ofNullable(all.get(id));
  }

  public Iterable<Person> findAll() {
    return all.values();
  }
}