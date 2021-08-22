package com.packt.cardatabase;


import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.web.CarController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
//The @RunWith(SpringRunner.class) annotation provides
//Spring's ApplicationContext and gets beans injected into your test instance:
@SpringBootTest
//The @SpringBootTest annotation specifies that the class
//is a regular test class that runs Spring Boot-based tests
class CardatabaseApplicationTests {

    @Autowired
    private CarController carController;

    @Test
    //The @Test annotation
        //before the method specifies to JUnit that the method can be run as a test case.
    public void contextLoads() {
        assertThat(carController).isNotNull();
    }

}
