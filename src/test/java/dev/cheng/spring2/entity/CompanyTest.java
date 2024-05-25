package dev.cheng.spring2.entity;

import com.github.javafaker.Faker;
import dev.cheng.spring2.repo.JpaCompanyRepo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CompanyTest {

    private static final Logger log = LoggerFactory.getLogger(CompanyTest.class);
    @Autowired
    private JpaCompanyRepo jpaCompanyRepo;

    private final Faker faker = new Faker();

    @Test
    void testSave() {
        ContactPerson contactPerson = ContactPerson.builder()
                .phone(faker.phoneNumber().phoneNumber())
                .firstName(faker.name().firstName())
                .lastName(faker.address().lastName())
                .build();
        Company company = Company.builder()
                .phone(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .name(faker.company().name())
                .contactPerson(contactPerson)
                .build();

        jpaCompanyRepo.save(company);
    }

    @Test
    void testFindOne() {
        Company company = jpaCompanyRepo.findById(1L).orElseThrow();
        assertNotNull(company);

        log.info("company: {}", company);
    }
}