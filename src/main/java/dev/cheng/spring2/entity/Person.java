package dev.cheng.spring2.entity;

import dev.cheng.spring2.util.PersonNameConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @Convert(converter = PersonNameConverter.class)
    private PersonName personName;
}
