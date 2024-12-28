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

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>, JpaSpecificationExecutor<Restaurant> {
    @Query("select r from Restaurant r where r.name like %:searchString% or r.address like %:searchString%")
    Page<Restaurant> searchRestaurantByNameOrAddressLike(@Param("searchString") String searchString, Pageable pageable);

    Page<Restaurant> searchRestaurantByAvailableSpotsGreaterThan(int availableSpotsIsGreaterThan, Pageable pageable);

    Page<Restaurant> searchRestaurantByAvailableSpotsGreaterThanAndNameLike(int availableSpotsIsGreaterThan, String name, Pageable pageable);
}
