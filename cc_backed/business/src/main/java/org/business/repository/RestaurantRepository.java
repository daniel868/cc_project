package org.business.repository;

import org.business.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>, JpaSpecificationExecutor<Restaurant> {
    @Query("select r from Restaurant r where r.name like %:searchString% or r.address like %:searchString%")
        //not used anymore; replace with jpaSpecification
    Page<Restaurant> searchRestaurantByNameOrAddressLike(@Param("searchString") String searchString, Pageable pageable);

    Page<Restaurant> searchRestaurantByAvailableSpotsGreaterThan(int availableSpotsIsGreaterThan, Pageable pageable);

    Page<Restaurant> searchRestaurantByAvailableSpotsGreaterThanAndNameLike(int availableSpotsIsGreaterThan, String name, Pageable pageable);

    @Query("select r from Restaurant r left join fetch r.reservations where r.id=:id")
    Optional<Restaurant> loadRestaurantByIdWithReservation(@Param("id") Integer id);

    Restaurant findRestaurantByName(String name);
}
