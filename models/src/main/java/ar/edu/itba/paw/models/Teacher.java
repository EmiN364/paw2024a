package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

//@DiscriminatorValue("TEACHER")
@Entity
public class Teacher extends BaseUser {

    @Column(nullable = false)
    private BigDecimal salary;

}
