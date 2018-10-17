package com.epam.esm.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The Local date deserializer.
 */
public class LocalDateDeserializer extends StdDeserializer<LocalDate> {
    /**
     * Instantiates a new Local date deserializer.
     */
    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return LocalDate.parse(jsonParser.readValueAs(String.class));
    }
}
