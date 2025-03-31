package messy_filtering.solution.java_dsl.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private boolean isActive;
    private String ldap;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getLdap() {
        return ldap;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setLdap(String ldap) {
        this.ldap = ldap;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}


