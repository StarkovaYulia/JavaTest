package ru.tsu.hits.springweb1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PatchDeviceDto {

    private Optional<String> name;

    private Optional<Long> price;

}
