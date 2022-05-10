package ru.tsu.hits.springtest1;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.tsu.hits.springtest1.entity.MyEntity;
import ru.tsu.hits.springtest1.repository.MyRepository;
import ru.tsu.hits.springtest1.service.MyService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class MyServiceTests {

    @MockBean
    private MyRepository myRepository;

    @SpyBean
    private MyService myService;

    @DisplayName("Репозиторий должен вернуть корректный name")
    @SneakyThrows
    @Test
    void shouldReturnTrueName() {
        Mockito.when(myRepository.findFirstByName("My Name"))
                .thenReturn(Optional.of(new MyEntity(UUID.randomUUID().toString(), "Some Name")));

        var entity = myRepository.findFirstByName("My Name");

        assertEquals("Some Name", entity.get().getName());
    }

    @DisplayName("Сервис должен вернуть корректный name")
    @SneakyThrows
    @Test
    void shouldReturnTrueNameFromService() {
        Mockito.when(myRepository.findFirstByName("My Name"))
                .thenReturn(Optional.of(new MyEntity(UUID.randomUUID().toString(), "Some Name 2")));

        var dto = myService.getByName("My Name");

        assertEquals("Some Name 2", dto.getName());
    }

    @Test
    void shouldPlusCorrectly() {
        assertEquals(3, myService.plus(1, 2));
        assertThrows(RuntimeException.class, () -> myService.plus(0, 0));
    }

}
