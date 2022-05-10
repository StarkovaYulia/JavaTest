package ru.tsu.hits.springweb1.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor(staticName = "create")
public class ErrorDto {

    private final Date timestamp;

    private final Integer status;

    private final String message;

}
