package com.oss.pii.demo.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.oss.pii.demo.annotations.Mask;
import com.oss.pii.demo.annotations.Sensitive;
import com.oss.pii.demo.utils.MaskUtils;

import java.io.IOException;
import java.lang.reflect.Field;

public class MaskSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Class<?> objectClass = obj.getClass();
        jsonGenerator.writeStartObject();
        while (objectClass != null) {
            if (objectClass.getName().equalsIgnoreCase("java.lang.Object")) {
                break;
            }
            if (objectClass.isAnnotationPresent(Sensitive.class)) {
                for (Field field : objectClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        if (field.isAnnotationPresent(Mask.class)) {
                            int keepLastDigits = field.getAnnotation(Mask.class).keepLastDigits();
                            int keepFirstDigits = field.getAnnotation(Mask.class).keepFirstDigits();
                            char maskChar=field.getAnnotation(Mask.class).maskChar();
                            String maskedValue = MaskUtils.mask((String) field.get(obj), keepFirstDigits, keepLastDigits,maskChar);
                            jsonGenerator.writeStringField(field.getName(), maskedValue);
                        } else {
                            jsonGenerator.writeObjectField(field.getName(), field.get(obj));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            objectClass = objectClass.getSuperclass();
        }

        jsonGenerator.writeEndObject();
    }
}
