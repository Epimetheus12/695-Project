package com.worker.people.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "roles")
public class Role implements GrantedAuthority {

    @Id
    private String id;
    private String authority;

    public Role( ) {
    }

    @Override
    public String getAuthority( ) {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
