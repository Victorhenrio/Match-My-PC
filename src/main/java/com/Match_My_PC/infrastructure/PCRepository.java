package com.Match_My_PC.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PCRepository extends CrudRepository<PCEntity, Long> {

}
