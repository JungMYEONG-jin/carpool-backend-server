package com.mate.carpool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.mate")
@Configuration
public class JpaConfig {
}
