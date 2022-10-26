package com.estudio.springboot.backend.apirest.springbootbackendapirest.models.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "cliente")
public class Cliente  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 12)
    @Column(nullable = false,length = 25)
    private String nombre;

    @NotEmpty
    @Column(length = 25)
    private String apellido;

    @NotEmpty
    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @PrePersist
    public void prePersit(){
        createAt=new Date();    }

    @Column(name = "fecha_creacion")
    private Date createAt;
}
