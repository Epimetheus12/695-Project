package com.worker.people;

import com.worker.people.domain.entities.Role;
import com.worker.people.repositories.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class PeopleApplication {

    public static void main(String[] args) {

/*        System.out.println("password:" + new BCryptPasswordEncoder().encode("123456.Workers"));*/
        SpringApplication.run(PeopleApplication.class, args);
    }
}
