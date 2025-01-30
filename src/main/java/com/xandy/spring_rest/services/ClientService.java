package com.xandy.spring_rest.services;

import com.xandy.spring_rest.entities.Client;
import com.xandy.spring_rest.exceptions.CpfUniqueException;
import com.xandy.spring_rest.exceptions.EntityNotFoundException;
import com.xandy.spring_rest.repository.ClientRepository;
import com.xandy.spring_rest.repository.projection.ClientProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Page<ClientProjection> findAll(Pageable pageable) {
        return repository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Client id:%s Can't be found", id)));
    }

    @Transactional(readOnly = true)
    public Client findByUserId(Long id) {
        return repository.findByUserId(id);
    }


    @Transactional(readOnly = true)
    public Client findByCpf(String cpf) {
        return repository.findByCpf(cpf).orElseThrow(
                () -> new EntityNotFoundException(String.format("Client with CPF: %s is not find ", cpf))
        );
    }
}
