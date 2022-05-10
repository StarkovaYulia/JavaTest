package ru.tsu.hits.springtest1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tsu.hits.springtest1.dto.MyDto;
import ru.tsu.hits.springtest1.service.MyService;

import java.util.UUID;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;


    @GetMapping("/logic")
    public ResponseEntity<MyDto> getByIdTests(
            @RequestParam("id") String id,
            @RequestHeader(value = "Name-For-Model", required = false) String name
    ) {
        if ("wrong-id".equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new MyDto(id, name == null ? "True Model" : name));
    }

    @PostMapping
    public MyDto createEntity(@RequestBody MyDto dto) {
        return myService.createWithName(dto.getName());
    }

    @GetMapping("/{id}")
    public MyDto getById(@PathVariable("id") UUID id) {
        return myService.findById(id.toString());
    }

}
