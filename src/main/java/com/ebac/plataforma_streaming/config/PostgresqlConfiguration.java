package com.ebac.plataforma_streaming.config;

import com.ebac.plataforma_streaming.utils.Criptografia;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgresqlConfiguration {


    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/plataforma_streaming")
                .username(Criptografia.getUser())
                .password(Criptografia.getPassword())
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
