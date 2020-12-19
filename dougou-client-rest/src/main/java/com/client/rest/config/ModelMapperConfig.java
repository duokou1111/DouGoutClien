package com.client.rest.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class ModelMapperConfig {
    private Converter<Date, String> dateToStringConverter = new AbstractConverter<Date, String>() {
        @Override
        protected String convert(Date date) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            return date == null ? null : simpleDateFormat.format(date);
        }
    };

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // 官方配置说明： http://modelmapper.org/user-manual/configuration/
        modelMapper.getConfiguration().setFullTypeMatchingRequired(false);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        modelMapper.addConverter(dateToStringConverter);

        return modelMapper;
    }
}
