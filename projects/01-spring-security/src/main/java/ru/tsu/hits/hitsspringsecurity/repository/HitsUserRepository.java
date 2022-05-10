package ru.tsu.hits.hitsspringsecurity.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.hitsspringsecurity.entity.HitsUserEntity;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class HitsUserRepository {

    private final PasswordEncoder passwordEncoder;

    private Map<String, HitsUserEntity> users;

    @PostConstruct
    private void init() {
        users = Stream.of(
                        new HitsUserEntity("admin", "123", "CAN_POST_2"),
                        new HitsUserEntity("ivan", "123")
                )
                .peek(u -> u.setPassword(passwordEncoder.encode(u.getPassword())))
                .collect(Collectors.toMap(HitsUserEntity::getLogin, u -> u));
    }

    public Optional<HitsUserEntity> findById(String username) {
        return Optional.ofNullable(users.get(username));
    }

}
