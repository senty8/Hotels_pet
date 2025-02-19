package com.senty.hotels_pet.repository;

import com.senty.hotels_pet.entity.Amenity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    Set<Amenity> findByNameIn(Collection<String> names);

    @Query(nativeQuery = true,
    value = """
            SELECT am.name, COUNT(*)
            FROM amenity am
            JOIN hotel_amenity h_am ON h_am.amenity_id = am.id
            GROUP BY am.name
            """)
    List<Tuple> countByName();
}
