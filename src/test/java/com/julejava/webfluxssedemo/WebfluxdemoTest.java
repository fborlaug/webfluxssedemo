package com.julejava.webfluxssedemo;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.julejava.webfluxssedemo.domain.Person;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class WebfluxdemoTest {

  @Test
  public void fluxTest() throws Exception {
    CountDownLatch countDownLatch = new CountDownLatch(1);

    Flux<Person> persons = Flux.just(createPerson("Thorin"), createPerson("Balin"), createPerson("Bifur"), createPerson("Bofur"));

    persons.delayElements(Duration.ofSeconds(1))
        .doOnComplete(countDownLatch::countDown)
        .subscribe(person -> log.info(person.getName()));

    countDownLatch.await();
  }

  private Person createPerson(String name) {
    return new Person(name, new Random().nextInt(40));
  }
}