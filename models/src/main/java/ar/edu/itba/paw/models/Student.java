package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@DiscriminatorValue("STUDENT")
@Entity
public class Student extends BaseUser {

    @Column(nullable = false)
    private int legajo;

}
