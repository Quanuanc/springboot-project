package dev.cheng.spring2.repo;

import dev.cheng.spring2.entity.Gender;
import dev.cheng.spring2.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaUserRepo extends CrudRepository<User, Integer> {
    List<User> findAllByGender(Gender gender);
}
