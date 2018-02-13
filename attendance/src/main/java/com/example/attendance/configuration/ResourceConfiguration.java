package com.example.attendance.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// XXX: none necessary
@Configuration
@EntityScan("com.example.attendance.data.model.entity")
@EnableJpaRepositories(basePackages = {"com.example.attendance.data.repository"})
@ComponentScan(basePackages = {"com.example.attendance.data.service"})
public class ResourceConfiguration {

  protected static final Logger logger = LoggerFactory.getLogger(ResourceConfiguration.class);

  public ResourceConfiguration() {
    logger.debug("ResourceConfiguration...");
  }

}
