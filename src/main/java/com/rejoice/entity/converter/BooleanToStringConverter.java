package com.rejoice.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return (value != null && value) ? "T" : "F";
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return "T".equals(value);
    }
}