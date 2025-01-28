package com.xandy.spring_rest.services;

import com.xandy.spring_rest.entities.Client;
import com.xandy.spring_rest.exceptions.CpfUniqueException;
import com.xandy.spring_rest.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository repository;


    @Transactional
    public Client save(Client client) {
        try {
            return repository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueException(String.format("CPF: %s Can't be saved, An user already is using this CPF", client.getCpf()));
        }
    }
}
