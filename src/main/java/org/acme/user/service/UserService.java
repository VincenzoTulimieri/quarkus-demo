package org.acme.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.user.entity.UserEntity;
import org.acme.user.repository.UserRepository;

import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    // metodi di gestione dei dati dell'utente
    public List<UserEntity> getAllUsers(){
        return userRepository.listAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity getUserByName(String name) {
        return userRepository.findByName(name);
    }

    // metodi di gestione degli utenti
    @Transactional
    public UserEntity createUser(UserEntity user){
        userRepository.persist(user);
        return user;
    }

    @Transactional
    public UserEntity updateUser(Long id, UserEntity newData){
        UserEntity entity = userRepository.findById(id);
        entity.setName(newData.getName());
        entity.setEmail(newData.getEmail());
        return entity;
    }

    @Transactional
    public boolean deleteUser(Long id){
        return userRepository.deleteById(id);
    }

}
