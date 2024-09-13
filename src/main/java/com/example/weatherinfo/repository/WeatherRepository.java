package com.example.weatherinfo.repository;

import com.example.weatherinfo.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;


public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    //HQL
    @Query("SELECT w FROM WeatherEntity w WHERE LOWER(REPLACE(w.province, ' ', '')) = LOWER(REPLACE(:province, ' ', ''))")
    Optional<WeatherEntity> findByProvince(@Param("province") String province);
}