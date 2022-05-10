package ru.tsu.hits.springtest1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.tsu.hits.springtest1.dto.MyDto;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class MyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("При получении по несуществующему id получаем not found")
    @SneakyThrows
    @Test
    void shouldNotFoundWhenUnknownId() {
        mockMvc.perform(get("/tests/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Можем получить созданный entity")
    @SneakyThrows
    @Test
    void shouldCorrectlyGetCreatedEntity() {
        var jsonReq = objectMapper.writeValueAsString(new MyDto(null, "Test Name 123"));

        // создание entity
        var jsonResp = mockMvc.perform(post("/tests").content(jsonReq).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var dto = objectMapper.readValue(jsonResp, MyDto.class);

        // получение данных по id
        mockMvc.perform(get("/tests/" + dto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").value("Test Name 123"));
    }

    @SneakyThrows
    @Test
    void shouldCorrectNameByHeader() {
        mockMvc.perform(get("/tests/logic")
                        .param("id", "some-id-123")
                        .header("Name-For-Model", "Test 456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test 456"))
                .andExpect(jsonPath("$.id").value("some-id-123"));
    }

}
