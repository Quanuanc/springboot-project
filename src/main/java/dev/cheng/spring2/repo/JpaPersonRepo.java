package dev.cheng.spring2.repo;

import dev.cheng.spring2.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface JpaPersonRepo extends CrudRepository<Person, Long> {
}
