package com.example.it22123.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
/*
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
*/

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String firstName;

    private String lastName;
    private String vat;

    @NotBlank
    @Size(max = 20)
    private String username;



    @NotBlank
    @Size(max = 50)
    @Email
    private String email;




    @NotBlank
    @Size(max = 120)
    private String password;







    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<role> roles = new HashSet<>();



    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<role> getRoles() {
        return roles;
    }

    public void setRoles(Set<role> roles) {
        this.roles = roles;
    }


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "cooperative_id")
    @JsonBackReference
    private Cooperative cooperative;



    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cooperative> applications;



    public List<Cooperative> getApplications() {
        return applications;
    }

    public void setApplications(List<Cooperative> applications) {
        this.applications = applications;
    }



    public void addApplication(Cooperative cooperative) {
        applications.add(cooperative);
    }





    public void approveApplication(Cooperative cooperative) {
        cooperative.setEstatus(Status.APPROVED);
        cooperative.setStatus("Approved");
        applications.add(cooperative);
    }

    public void rejectApplication(Cooperative cooperative) {
        cooperative.setEstatus(Status.REJECTED);
        cooperative.setStatus("Rejected");
        applications.add(cooperative);
    }

    public Cooperative getCooperative() {
        return cooperative;
    }

    public void setCooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
    }

    @PreRemove
    private void preRemove() {
        for (Cooperative cooperative : applications) {
            cooperative.setUser(null);
        }
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }





    public void addUser(User user) {
    }
}
