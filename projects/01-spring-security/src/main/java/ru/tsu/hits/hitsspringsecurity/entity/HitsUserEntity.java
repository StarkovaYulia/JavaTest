package ru.tsu.hits.hitsspringsecurity.entity;

import lombok.Data;

@Data
public class HitsUserEntity {

    public HitsUserEntity(String login, String password, String... permissions) {
        this.login = login;
        this.password = password;
        this.permissions = permissions;
    }

    private String login;

    private String password;

    private String[] permissions;

}
