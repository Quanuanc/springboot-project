package dev.cheng.spring2.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.util.StringUtils;

import dev.cheng.spring2.entity.PersonName;

@Converter
public class PersonNameConverter implements AttributeConverter<PersonName, String> {

    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(PersonName personName) {
        if (personName == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (StringUtils.hasText(personName.getFirstName())) {
            sb.append(personName.getFirstName());
            sb.append(SEPARATOR);
        }

        if (StringUtils.hasText(personName.getLastName())) {
            sb.append(personName.getLastName());
        }

        return sb.toString();
    }

    @Override
    public PersonName convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return null;
        }

        PersonName personName = new PersonName();
        if (!dbData.contains(SEPARATOR)) {
            personName.setLastName(dbData);
            return personName;
        }

        String[] split = dbData.split(SEPARATOR);
        return PersonName.builder()
                .firstName(split[0])
                .lastName(split[1])
                .build();
    }
}
