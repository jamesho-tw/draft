package com.example.attendance.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.example.attendance.data.model.entity")
@EnableJpaRepositories(basePackages = {"com.example.attendance.data.repository"})
@ComponentScan(basePackages = {"com.example.attendance.data.service"})
public class ResourceConfiguration {

}
