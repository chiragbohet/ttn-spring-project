package com.chiragbohet.ecommerce.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;

// ref : https://www.baeldung.com/hibernate-persist-json-object, https://www.baeldung.com/java-map-to-string-conversion

@Log4j2
@Converter
public class MetadataConverter implements AttributeConverter<Map<String,String>,String> {


    // TODO : Why is @Autowired here not working? null pointer exception
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> map) {

        String metadata = null;

        try{
            metadata = objectMapper.writeValueAsString(map);
        }
        catch (final JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return metadata;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String dbData) {

        Map<String,String> map = null;

        try
        {
            map = objectMapper.readValue(dbData, Map.class);
        }
        catch (final IOException e) {
            log.error("JSON reading error", e);
        }

        return map;

    }
}
