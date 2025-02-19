package com.senty.hotels_pet.repository;

import com.senty.hotels_pet.entity.Hotel;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @Query(value = """
            SELECT h.brand, COUNT(h)
            FROM Hotel h
            GROUP BY h.brand
            """)
    List<Tuple> countByBrands();

    @Query(value = """
            SELECT a.city, COUNT(*)
            FROM Hotel h
            JOIN h.address a
            GROUP BY a.city
            """)
    List<Tuple> countByCities();

    @Query(value = """
            SELECT a.country, COUNT(h)
            FROM Hotel h
            JOIN h.address a
            GROUP BY a.country
            """)
    List<Tuple> countByCountries();
}
