package com.Blogging_Platform_API.Blogging_Platform_API.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    public static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            // Set timezone to Jakarta
            builder.timeZone(TimeZone.getTimeZone("Asia/Jakarta"));

            // Create JavaTimeModule with custom serializer for LocalDateTime
            JavaTimeModule module = new JavaTimeModule();
            module.addSerializer(java.time.LocalDateTime.class, new LocalDateTimeSerializer(CUSTOM_FORMATTER));
            builder.modules(module);

            // Set date format (this affects serialization of dates)
            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
        };
    }
}