package com.packt.cardatabase.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface CarRepository extends CrudRepository<Car,Long> {

    // get cars by color
    List<Car> findByColor(
            @Param("color")
            String color);
    // get cars by year
    List<Car> findByYear(
            @Param("year")
            int year);

    // get cars by brand and model
    List<Car> findByBrandAndModel (String brand, String model);
    // get cars by brand or color
    List<Car> findByBrandOrColor(String brand, String color);

    // get cars by brand and sort by year
    List<Car> findByBrandOrderByYearAsc (String brand);

    // get cars by brand using SQL
    @Query("select c from Car c where c.brand=?1")
    List<Car> findByBrand (String brand);

    // get cars by brand using sql
    @Query("select c from Car c where c.brand like %?1")
    List<Car> findByBrandEndsWith(String brand);

}
