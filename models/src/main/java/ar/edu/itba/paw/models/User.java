package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users2")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users2_userid_seq")
    @SequenceGenerator(sequenceName = "users2_userid_seq", name = "users2_userid_seq", allocationSize = 1)
    private long userId;

    @Column(nullable = false, unique = true, length = 255)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    // Nombre del atributo en la clase Issue
    @OneToMany(mappedBy = "reporter")
    private List<Issue> reportedIssues;

    // Es peligroso usar FetchType.EAGER porque es recursivo y puede traer toda la base de datos
    @OneToMany(mappedBy = "assignedTo")
    private List<Issue> assignedIssues;

    /* default */ protected User() {
        // Just for Hibernate, we don't create invalid users otherwise
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public List<Issue> getReportedIssues() {
        return reportedIssues;
    }

    public List<Issue> getAssignedIssues() {
        return assignedIssues;
    }

    @Override
    public String toString() {
        return username;
    }
}
