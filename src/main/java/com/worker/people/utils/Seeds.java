package com.worker.people.utils;

import com.worker.people.domain.entities.Role;
import com.worker.people.domain.entities.User;
import com.worker.people.repositories.RoleRepository;
import com.worker.people.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Seeds {
        private final RoleRepository roleRepository;
        private final UserRepository userRepository;

        @Autowired
        public Seeds(RoleRepository roleRepository, UserRepository userRepository) {
            this.roleRepository = roleRepository;
            this.userRepository = userRepository;
        }

        @PostConstruct
        public void insertEntities() {
            if(roleRepository.count() == 0L ){
                Role role1 = new Role();
                Role role2 = new Role();
                Role role3 = new Role();
                role1.setAuthority("ADMIN");
                role2.setAuthority("USER");
                role3.setAuthority("ROOT");
                this.roleRepository.save(role1);
                this.roleRepository.save(role2);
                this.roleRepository.save(role3);
            }
            if(userRepository.findByUsername("test1").isEmpty()){
                Role rootRole = roleRepository.findByAuthority("ROOT");
                Set<Role> roles = new HashSet<>();
                roles.add(rootRole);
                User user = new User();
                user.setFirstName("test");
                user.setUsername("test1");
                user.setPassword("$2a$10$KURw5EsV1UhHMLx/ipBHc.Z9gArKD20lCoYl225LdBpe7wSpoy1ca");
                user.setAuthorities(roles);
                user.setAccountNonExpired(user.isAccountNonExpired());
                user.setAccountNonLocked(user.isAccountNonLocked());
                user.setCredentialsNonExpired(user.isCredentialsNonExpired());
                user.setEnabled(user.isEnabled());
                userRepository.save(user);
            }
        }
}
