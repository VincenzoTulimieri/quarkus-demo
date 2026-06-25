package org.acme.user.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.user.entity.UserEntity;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    public UserEntity findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public UserEntity findByName(String name) {
        return find("name", name).firstResult();
    }
}
