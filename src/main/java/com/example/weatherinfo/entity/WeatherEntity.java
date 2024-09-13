package com.example.weatherinfo.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "weather_model")

public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String province;

    private String weatherCondition;
    private double humidity;
    private double temperature;
    private double windSpeed;
    private LocalDateTime realtime;

}