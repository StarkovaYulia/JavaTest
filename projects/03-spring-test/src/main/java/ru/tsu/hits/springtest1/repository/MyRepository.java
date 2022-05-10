package ru.tsu.hits.springtest1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.springtest1.entity.MyEntity;

import java.util.Optional;

public interface MyRepository extends JpaRepository<MyEntity, String> {

    Optional<MyEntity> findFirstByName(String name);

}
