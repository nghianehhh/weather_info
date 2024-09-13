package com.example.weatherinfo.service;

import com.example.weatherinfo.dto.WeatherResponse;
import com.example.weatherinfo.repository.WeatherRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.example.weatherinfo.entity.WeatherEntity;
import org.springframework.web.client.HttpClientErrorException;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class WeatherDataFetcher {

    private final WeatherRepository weatherRepository;
    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherDataFetcher(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public WeatherEntity fetchWeatherData(String province) {
        try {
            String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", province, apiKey);
            // Gửi yêu cầu GET tới OpenWeather API và lấy dữ liệu về dạng JSON
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
            // Chuyển đổi dữ liệu JSON thành đối tượng WeatherEntity
            if (response != null) {
                // Tìm bản ghi thời tiết hiện có trong cơ sở dữ liệu
                Optional<WeatherEntity> existingWeather = weatherRepository.findByProvince(province);

                WeatherEntity weatherEntity = existingWeather.orElse(new WeatherEntity());
                weatherEntity.setProvince(province);
                weatherEntity.setTemperature(response.getMain().getTemp());
                weatherEntity.setHumidity(response.getMain().getHumidity());
                weatherEntity.setWeatherCondition(response.getWeather().get(0).getDescription());
                weatherEntity.setWindSpeed(response.getWind().getSpeed());
                weatherEntity.setRealtime(LocalDateTime.now());

                weatherRepository.save(weatherEntity);
                return weatherEntity;
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error: Province does not exist or request is invalid: " + province);
        } catch (ResourceAccessException e) {
            System.out.println("Error: Unable to connect to OpenWeather API.");
        } catch (HttpServerErrorException e) {
            System.out.println("Error: OpenWeather API server encountered a problem.");
        } catch (Exception e) {
            System.out.println("Unknown error: " + e.getMessage());
        }
        return null;
    }
}