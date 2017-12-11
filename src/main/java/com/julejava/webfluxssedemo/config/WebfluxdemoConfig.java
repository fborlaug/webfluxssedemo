package com.julejava.webfluxssedemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.SubscribableChannel;

@Configuration
public class WebfluxdemoConfig {

  @Bean
  public SubscribableChannel personChannel() {
    return MessageChannels.publishSubscribe().get();
  }
}
