package ru.tsu.hits.springtest1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tsu.hits.springtest1.entity.MyEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyDto {

    private String id;

    private String name;

    public static MyDto from(MyEntity entity) {
        return new MyDto(entity.getId(), entity.getName());
    }

}
