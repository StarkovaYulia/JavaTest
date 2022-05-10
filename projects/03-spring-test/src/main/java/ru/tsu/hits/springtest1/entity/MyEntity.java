package ru.tsu.hits.springtest1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_table")
public class MyEntity {

    @Id
    private String id;

    private String name;

    public static MyEntity create(String name) {
        return new MyEntity(UUID.randomUUID().toString(), name);
    }

}
