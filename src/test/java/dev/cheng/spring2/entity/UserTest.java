package dev.cheng.spring2.entity;

import com.github.javafaker.Faker;
import dev.cheng.spring2.repo.JpaUserRepo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {
    private static final Logger log = LoggerFactory.getLogger(UserTest.class);
    private final Faker faker = new Faker();
    @Autowired
    private JpaUserRepo jpaUserRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testSave() {
        List<User> users = new ArrayList<>(100);
        Random random = new Random();
        Gender[] genders = Gender.values();
        for (int i = 0; i < 100; i++) {
            Date birthday = faker.date().birthday();
            int genderSeed = random.nextInt(2);
            User user = User.builder()
                    .email(faker.internet().emailAddress())
                    .name(faker.name().fullName())
                    .password(faker.internet().password())
                    .birthDate(LocalDate.ofInstant(birthday.toInstant(), ZoneId.systemDefault()))
                    .gender(genders[genderSeed])
                    .build();
            users.add(user);
        }
        jpaUserRepo.saveAll(users);
    }

    @Test
    void testGetAge() {
        Random random = new Random();
        int id = random.nextInt(100);
        User user = jpaUserRepo.findById(id).orElseThrow();
        Integer age = user.getAge();
        assertNotNull(age);
        log.info("user birth: {}, age: {}", user.getBirthDate(), age);
    }

    @Test
    void testFindByGender() {
        List<User> females = jpaUserRepo.findAllByGender(Gender.FEMALE);
        assertNotEquals(0, females.size());
    }

    @Test
    void testNewState() {
        User user = User.builder().build();
        boolean contains = entityManager.contains(user);
        assertFalse(contains);
        log.info("user is at New(Transient) state");
    }

    @Test
    @Transactional
//    @Commit
        // JPA will auto rollback in test
    void testManagedState() {
        User user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .gender(Gender.MALE)
                .build();

        jpaUserRepo.save(user);

        boolean contains = entityManager.contains(user);
        assertTrue(contains);

        user.setEmail(faker.internet().emailAddress());
        jpaUserRepo.save(user);
        entityManager.flush();

        log.info("user is at Managed(Persistent) state");
    }

}