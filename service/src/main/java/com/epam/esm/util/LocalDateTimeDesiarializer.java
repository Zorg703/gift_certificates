package com.epam.esm.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * The type Local date time desiarializer.
 */
public class LocalDateTimeDesiarializer extends StdDeserializer<LocalDateTime> {
    /**
     * Instantiates a new Local date time desiarializer.
     */
    public LocalDateTimeDesiarializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return LocalDateTime.parse(p.readValueAs(String.class));
    }
}
