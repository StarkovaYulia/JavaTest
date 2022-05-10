package ru.tsu.hits.springweb1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.tsu.hits.springweb1.dto.DeviceDto;
import ru.tsu.hits.springweb1.dto.PatchDeviceDto;
import ru.tsu.hits.springweb1.exception.BadRequestException;
import ru.tsu.hits.springweb1.exception.NotFoundException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping(value = "/devices", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

    private final Map<UUID, DeviceDto> deviceDtoMap = new ConcurrentHashMap<>();

    /**
     * Инициализация списка устройств
     */
    @PostConstruct
    public void init() {
        var id = UUID.fromString("56272c39-2d09-462d-a058-447fabc02ce6");
        deviceDtoMap.put(id, DeviceDto.create(id, "Intel Core-i7 10700K", 25000L));
    }

    /**
     * Получение списка всех устройств
     */
    @GetMapping
    public Collection<DeviceDto> getAllDevices() {
        return deviceDtoMap.values();
    }

    /**
     * Создание устройства
     */
    @PostMapping
    public DeviceDto createDevice(@Validated @RequestBody DeviceDto dto) {
        dto.setId(UUID.randomUUID());
        deviceDtoMap.put(dto.getId(), dto);
        return dto;
    }

    /**
     * Изменение устройства
     */
    @PutMapping("/{id}")
    public DeviceDto updateDevice(@RequestBody DeviceDto dto, @PathVariable UUID id) {
        var item = deviceDtoMap.get(id);
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("Имя не задано");
        }
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        return item;
    }

    @PatchMapping("/{id}")
    public DeviceDto patchDevice(@PathVariable("id") UUID id, @RequestBody PatchDeviceDto dto) {
        var item = deviceDtoMap.get(id);
        if (dto.getName() != null) {
            item.setName(dto.getName().orElse(null));
        }
        if (dto.getPrice() != null) {
            item.setPrice(dto.getPrice().orElse(null));
        }
        return item;
    }


    /**
     * Когда хотим сразу на контроллере захардкодить HTTP-статус
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/no-access")
    public DeviceDto getNotFound() {
        return DeviceDto.create(UUID.randomUUID(), "123", 456L);
    }

    /**
     * Когда хотим принять какой-либо заголовок запрса (Header)
     */
    @GetMapping("/headers")
    public DeviceDto getWithHeaders(@RequestHeader(value = "Client-ID", required = false) String clientId) {
        return DeviceDto.create(UUID.randomUUID(), clientId + "-device", 10000L);
    }

    /**
     * Если хотим в контроллере более тонко настраивать ответ
     */
    @GetMapping(value = "/{id}/custom")
    public ResponseEntity<DeviceDto> getCustomResponse(@PathVariable UUID id) {
        var item = deviceDtoMap.get(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
                .header("device-dto-id", item.getId().toString())
                .body(item);
    }

    /**
     * Можем указать иной Content-Type так, что Spring Boot поймёт во что должен сериализоваться класс
     */
    @GetMapping(value = "/as-xml", produces = MediaType.APPLICATION_XML_VALUE)
    public DeviceDto getProduces() {
        return DeviceDto.create(UUID.randomUUID(), "Samsung B-Die 8GiB", 9000L);
    }

    /**
     * Указание Exception-а со статусом
     */
    @GetMapping("/exception")
    public DeviceDto getForExceptionWithStatus() {
        throw new NotFoundException("Что-то пошло не так!");
//        throw new BadRequestException("Что-то пошло не так!");
    }

    /**
     * Пример работы с PathVariable
     */
    @GetMapping("/{y}/plus/{x}")
    public String plusPaths(@PathVariable(value = "x") Long x, @PathVariable("y") Long y) {
        log.info("X = {}, Y = {}", x, y);
        return String.valueOf(x + y);
    }

    /**
     * Пример работы с RequestParam
     */
    @GetMapping("/plus")
    public String plusRequests(@RequestParam("x") Long x, @RequestParam(value = "y", required = false, defaultValue = "1") Long y) {
        log.info("X = {}, Y = {}", x, y);
        return String.valueOf(x + y);
    }

}
