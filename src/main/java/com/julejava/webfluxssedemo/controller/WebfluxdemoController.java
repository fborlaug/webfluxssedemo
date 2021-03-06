package com.julejava.webfluxssedemo.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.julejava.webfluxssedemo.domain.Person;
import com.julejava.webfluxssedemo.repository.PersonRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WebfluxdemoController {

  private final SubscribableChannel personChannel;
  private final PersonRepository repo;

  public WebfluxdemoController(SubscribableChannel personChannel, PersonRepository repo) {
    this.personChannel = personChannel;
    this.repo = repo;
  }

  @GetMapping("/person")
  public Flux<Person> list() {
    return Flux.fromStream(this.repo.getAll());
  }

  @GetMapping("/person/{id}")
  public Mono<Optional<Person>> findById(@PathVariable String id) {
    return Mono.just(this.repo.findById(id));
  }

  @GetMapping(value = "/person-watcher", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Person> streamPersonCreated() {
    return Flux.create(sink -> {
      MessageHandler handler = message -> sink.next((Person) message.getPayload());
      personChannel.subscribe(handler);
      sink.onCancel(() -> personChannel.unsubscribe(handler));
    });
  }

  @PostMapping(value = "/person", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void createPerson(@RequestBody Person person) {
    if (person != null && person.getName() != null) {
      repo.save(person);
      personChannel.send(MessageBuilder.withPayload(person).build());
    }
  }
}