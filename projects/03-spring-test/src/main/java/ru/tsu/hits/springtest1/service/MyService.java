package ru.tsu.hits.springtest1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.springtest1.dto.MyDto;
import ru.tsu.hits.springtest1.entity.MyEntity;
import ru.tsu.hits.springtest1.exception.NotFoundException;
import ru.tsu.hits.springtest1.repository.MyRepository;

@Service
@RequiredArgsConstructor
public class MyService {

    private final MyRepository myRepository;

    @Transactional
    public MyDto createWithName(String name) {
        var entity = myRepository.save(MyEntity.create(name));
        return MyDto.from(entity);
    }

    @Transactional(readOnly = true)
    public MyDto findById(String id) {
        return MyDto.from(myRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public MyDto getByName(String name) {
        return MyDto.from(myRepository.findFirstByName(name)
                .orElseThrow(NotFoundException::new));
    }

    public Integer plus(Integer x, Integer y) {
        if (x == 0 && y == 0) {
            throw new RuntimeException();
        }
        return x + y;
    }

}
