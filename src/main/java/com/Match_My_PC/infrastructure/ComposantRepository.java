package com.Match_My_PC.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComposantRepository extends CrudRepository<ComposantEntity, Long> {

    List<ComposantEntity> findByPCEntityId(Long pcEntityId);
    List<ComposantEntity> findByPCEntity(PCEntity pcEntity);

}
