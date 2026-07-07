package org.acme.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.user.entity.UserEntity;
import org.acme.user.kafka.UsersEventsProducer;
import org.acme.user.repository.UserRepository;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class);

    @Inject
    UserRepository userRepository;

    @Inject
    UsersEventsProducer usersEventsProducer;

    // metodi di gestione dei dati dell'utente
    public List<UserEntity> getAllUsers(){
        return userRepository.listAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findByIdOptional(id);
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
        usersEventsProducer.sendUserCreatedEvent(user.getId(),  user.getName(), user.getEmail());
        log.info("User creato correttamente con id: " + user.getId());
        return user;
    }

    @Transactional
    public UserEntity updateUser(Long id, UserEntity newData){
        UserEntity entity = userRepository.findById(id);
        if(entity == null){
            log.info("User non trovato con id: " + id);
            return null;
        }
        entity.setName(newData.getName());
        entity.setEmail(newData.getEmail());
        usersEventsProducer.sendUserUpdatedEvent(entity.getId(), entity.getName(), entity.getEmail());
        log.info("Utente aggiornato con id: " + id);
        return entity;
    }

    @Transactional
    public boolean deleteUser(Long id){
        boolean deleted = userRepository.deleteById(id);
        if(deleted){
            usersEventsProducer.sendUserDeletedEvent(id);
        }
        return deleted;
    }

}
