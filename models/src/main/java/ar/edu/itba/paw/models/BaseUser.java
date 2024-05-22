package ar.edu.itba.paw.models;

import javax.persistence.*;

// @MappedSuperClass Esto nunca va a tener una tabla en la base de datos
// Analogo a InheritanceType.TABLE_PER_CLASS
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// @DiscriminatorColumn(name = "userType") // Con singleTable
@Entity
public abstract class BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long userId;

    @Column(nullable = false, unique = true, length = 255)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

}
