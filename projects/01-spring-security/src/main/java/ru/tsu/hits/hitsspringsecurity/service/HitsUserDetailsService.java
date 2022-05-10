package ru.tsu.hits.hitsspringsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tsu.hits.hitsspringsecurity.repository.HitsUserRepository;

@Service
@RequiredArgsConstructor
public class HitsUserDetailsService implements UserDetailsService {

    private final HitsUserRepository hitsUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return hitsUserRepository.findById(username)
                .map(u -> User.builder()
                        .username(u.getLogin())
                        .password(u.getPassword())
                        .authorities(u.getPermissions())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не зарегистрирован: " + username));
    }

}
