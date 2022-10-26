package com.estudio.springboot.backend.apirest.springbootbackendapirest.controllers;

import com.estudio.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;
import com.estudio.springboot.backend.apirest.springbootbackendapirest.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController{

    @Autowired
    private IClienteService iClienteService;



    @GetMapping("/clientes")
    public List<Cliente>index(){
        return iClienteService.findAdll();
    }

    @GetMapping("clientes/{id}")
    public ResponseEntity<Cliente> show(@PathVariable Long id){
        return iClienteService.findbYId(id).map(
                cliente -> new ResponseEntity<>(cliente, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,BindingResult result,@PathVariable Long id ){
        Cliente clienteActual=null;
        Map<String,Object>response= new HashMap<>();

        if (result.hasErrors()){
            List<String>errors=result.getFieldErrors().stream().map(
                    err-> "El campo '"+err.getField()+"' "+err.getDefaultMessage()).collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }

       try {
            clienteActual=  iClienteService.findbYId(id).get();
       }catch (DataAccessException e){
        response.put("mensaje","Error al realizar la consulta en la BD");
        response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
       }

        if(clienteActual!=null){
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());

            response.put("mensaje","Cliente ha sido actualizado con exito");
            response.put("cliente",iClienteService.save(clienteActual));
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }



    }

    @DeleteMapping("clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        iClienteService.delete(id);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result){
        Cliente clienteNuevo=null;
        Map<String,Object>response= new HashMap<>();

        if (result.hasErrors()){
            List<String>errors=result.getFieldErrors().stream().map(
                    err-> "El campo '"+err.getField()+"' "+err.getDefaultMessage()).collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try {
            clienteNuevo=iClienteService.save(cliente);
        }catch (DataAccessException e){
            response.put("mensaje","Error al realizar el insert en la BD");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido creado con exito");
        response.put("cliente",clienteNuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

    }


}
