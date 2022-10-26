package com.estudio.springboot.backend.apirest.springbootbackendapirest.models.services;

import com.estudio.springboot.backend.apirest.springbootbackendapirest.models.dao.IClienteDao;
import com.estudio.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService{

    private IClienteDao iClienteDao;

    public ClienteServiceImpl(IClienteDao iClienteDao) {
        this.iClienteDao=iClienteDao;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Cliente> findAdll() {
       return (List<Cliente>) iClienteDao.findAll();
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return iClienteDao.save(cliente);
    }

    @Override
    @Transactional
    public void delete(long id) {
    iClienteDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<Cliente> findbYId(Long id) {
        return iClienteDao.findById(id);
    }
}
