package ru.tsu.hits.hitsspringsecurity;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HitsSpringSecurityApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Пользователю, прошедшему аутентикацию должно вренуться 200 OK")
    @WithMockUser(username = "admin", password = "123")
    @SneakyThrows
    void should200WhenBasicAuthPresent() {
        mockMvc.perform(get("/hits/me"))
                .andExpect(status().isOk())
                .andExpect(content().string("admin"));
    }

}
