package com.estudio.springboot.backend.apirest.springbootbackendapirest.models.services;

import com.estudio.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    public List<Cliente>findAdll();

    public Cliente save(Cliente cliente);

    public void delete(long id);

    public Optional<Cliente> findbYId(Long id);
}
