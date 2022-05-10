package ru.tsu.hits.springweb1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class DeviceDto {

    private UUID id;

    @NotBlank(message = "Имя должно быть задано")
    private String name;

    @Min(value = 10L)
    private Long price;

}
