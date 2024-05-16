package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, length = 2048)
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    // Como estoy desde issue, ManyToOne porque un user puede tener muchos issues pero un issue solo puede tener un user
    // Oprhan removal es para que si borro un user, se borren todos los issues que tiene asociados (Delete Cascade)
    @ManyToOne(optional = false)
    private User reporter;

    @ManyToOne(optional = true)
    private User assignedTo;

    /* default */ protected Issue() {
        // Just for Hibernate
    }

    public Issue(User reporter, String description) {
        this.description = description;
        this.priority = Priority.MEDIUM;
        this.reporter = reporter;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }
}
