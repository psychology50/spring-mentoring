package com.likelion.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {
}
