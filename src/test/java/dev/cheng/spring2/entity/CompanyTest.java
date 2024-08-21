package dev.cheng.spring2.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;

import dev.cheng.spring2.repo.JpaCompanyRepo;

@SpringBootTest
class CompanyTest {

    private static final Logger log = LoggerFactory.getLogger(CompanyTest.class);
    @Autowired
    private JpaCompanyRepo jpaCompanyRepo;
    @PersistenceContext
    private EntityManager em;

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
                .expiryDate(LocalDate.of(0, 1, 1))
                .build();

        jpaCompanyRepo.save(company);
    }

    @Test
    void testFindOne() {
        Company company = jpaCompanyRepo.findById(1L).orElseThrow();
        assertNotNull(company);

        log.info("company: {}", company);
    }

    @Test
    void testInvalidDate() {
        LocalDate invalidDate = LocalDate.of(0, 1, 1);
        Company company = Company.builder()
                .phone(faker.phoneNumber().phoneNumber())
                .address(faker.address().fullAddress())
                .name(faker.company().name())
                .expiryDate(invalidDate)
                .build();
        Company saved = jpaCompanyRepo.save(company);
        log.info("saved: {}", saved);

        em.clear();

        Company dbCompany = jpaCompanyRepo.findById(saved.getId()).orElseThrow();
        assertEquals(invalidDate, dbCompany.getExpiryDate());
    }
}