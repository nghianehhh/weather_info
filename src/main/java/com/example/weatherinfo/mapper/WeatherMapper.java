//package com.example.weatherinfo.mapper;
//
//import com.example.weatherinfo.dto.WeatherResponse;
//import com.example.weatherinfo.entity.WeatherEntity;
//import org.springframework.stereotype.Component;
//import java.util.List;
//
//@Component
//public class WeatherMapper {
//
//    public WeatherResponse toResponse(WeatherEntity entity) {
//        WeatherResponse response = new WeatherResponse();
//
//        response.setProvince(entity.getProvince());
//        response.setWeatherCondition(entity.getWeatherCondition());
//        response.setHumidity(entity.getHumidity());
//        response.setTemperature(entity.getTemperature());
//        response.setWindSpeed(entity.getWindSpeed());
//        response.setRealtime(entity.getRealtime());
//
//        return response;
//    }
//}