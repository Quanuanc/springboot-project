package dev.cheng.spring2.entity;

import com.github.javafaker.Faker;
import dev.cheng.spring2.repo.JpaPersonRepo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PersonTest {

    private static final Logger log = LoggerFactory.getLogger(PersonTest.class);
    @Autowired
    private JpaPersonRepo jpaPersonRepo;

    private final Faker faker = new Faker();

    @Test
    void testSave() {
        PersonName personName = PersonName.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
        Person person = Person.builder().personName(personName).build();
        jpaPersonRepo.save(person);
    }

    @Test
    void testFindById() {
        Person person = jpaPersonRepo.findAll().iterator().next();
        assertNotNull(person);
        log.info("person: {}", person);
    }
}