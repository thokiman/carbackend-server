package com.packt.cardatabase;

import com.packt.cardatabase.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CardatabaseApplication {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(CardatabaseApplication.class, args);

    }

    // allow execute additional code
    @Bean
    public CommandLineRunner runner() {
        return (args -> {
            // place your code here
            // Save demo data to database
            Owner owner1 = new Owner("John", "Johnson");
            Owner owner2 = new Owner("Marry", "Robinson");
            ownerRepository.save(owner1);
            ownerRepository.save(owner2);

            carRepository.save(new Car("Ford", "Mustang", "Red",
                    "ADF-1121", 2017, 59000,owner1));
            carRepository.save(new Car("Nissan", "Leaf", "White",
                    "SSJ-3002", 2014, 29000,owner2));
            carRepository.save(new Car("Toyota","Prius", "Silver",
                    "KKO-0212", 2018, 39000,owner2));


            // username: user password: user
            userRepository.save(new User("user",
                    "$2a$04$1.YhMIgNX/8TkCKGFUONWO1waedKhQ5KrnB30fl0Q01QKqmzLf.Zi",
                    "USER"));
            // username: admin password: admin
            userRepository.save(new User("admin",
                    "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG",
                    "ADMIN"));

        });
    }

}
