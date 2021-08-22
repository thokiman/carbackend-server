package com.packt.cardatabase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"}) // avoid infinite loop at one to many
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id") // default : ownerId, java & default: owner_id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    //The mappedBy="owner" attribute setting tells us that the Car
    //class has the owner field, which is the foreign key for this relationship.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner") // -> Car.getOwner and Car,setOwner
    @JsonIgnore // avoid infinite loop at one to many
    private List<Car> cars;

    //The annotation @JoinColumn indicates that this entity is the owner of the relationship
        // (that is: the corresponding table has a column with a foreign key to the referenced table),

    // whereas the attribute mappedBy indicates
        // that the entity in this side is the inverse of the relationship, and the owner resides in the "other" entity.
        // This also means that you can access the other table from the class which you've annotated with "mappedBy" (fully bidirectional relationship).

    public Owner() {
    }

    public Owner(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cars=" + cars +
                '}';
    }
}
