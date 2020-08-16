package com.oss.pii.demo.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;

import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.oss.pii.demo.annotations.Mask;
import com.oss.pii.demo.annotations.Sensitive;
import com.oss.pii.demo.utils.MaskUtils;

import java.io.IOException;
import java.lang.reflect.Field;

public class GenericMaskSerializer extends StdSerializer<Object> implements ResolvableSerializer {

    private final JsonSerializer<?> defaultSerializer;

    private final ObjectMapper mapper;

    protected GenericMaskSerializer(Class<?> vc, JsonSerializer<?> defaultSerializer, ObjectMapper mapper) {
        super(vc, false);
        this.defaultSerializer = defaultSerializer;
        this.mapper = mapper;
    }

    public static SimpleModule getModule(final ObjectMapper mapper) {
        SimpleModule module = new SimpleModule();
        module.setSerializerModifier(new BeanSerializerModifier() {
            @Override
            public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
                if (BeanSerializer.class.isAssignableFrom(serializer.getClass())) {
                    return new GenericMaskSerializer(beanDesc.getBeanClass(), serializer, mapper);
                }
                return serializer;
            }
        });
        return module;
    }

    @Override
    public void resolve(SerializerProvider provider) throws JsonMappingException {
        ((ResolvableSerializer) defaultSerializer).resolve(provider);
    }

    @Override
    public void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSerializer) throws IOException {
        serialize(value, gen, provider);
    }
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
