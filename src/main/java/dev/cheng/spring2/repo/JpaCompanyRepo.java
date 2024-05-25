package dev.cheng.spring2.repo;

import dev.cheng.spring2.entity.Company;
import org.springframework.data.repository.CrudRepository;

public interface JpaCompanyRepo extends CrudRepository<Company, Long> {
}
