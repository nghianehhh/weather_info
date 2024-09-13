package com.example.weatherinfo.service;

import com.example.weatherinfo.entity.WeatherEntity;
import com.example.weatherinfo.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    private final WeatherDataFetcher weatherDataFetcher;

    private String normalizeProvince(String province) {
        return province.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    public Optional<WeatherEntity> getWeatherByProvince(String province) {
        String normalizedProvince = normalizeProvince(province);
        return weatherRepository.findByProvince(normalizedProvince);
    }

    public List<WeatherEntity> getAllWeathers() {
        return weatherRepository.findAll();
    }

    public Page<WeatherEntity> getProvinces(Pageable pageable) {
        return weatherRepository.findAll(pageable);
    }

    public WeatherEntity addWeather(WeatherEntity newWeather) {
        return weatherRepository.save(newWeather);
    }

    public WeatherEntity updateWeather(String province, WeatherEntity updatedWeather) {
        Optional<WeatherEntity> optionalWeather = weatherRepository.findByProvince(province);
        if (optionalWeather.isPresent()) {
            WeatherEntity existingWeather = optionalWeather.get();
            existingWeather.setProvince(updatedWeather.getProvince());
            existingWeather.setTemperature(updatedWeather.getTemperature());
            existingWeather.setHumidity(updatedWeather.getHumidity());
            existingWeather.setWeatherCondition(updatedWeather.getWeatherCondition());
            existingWeather.setWindSpeed(updatedWeather.getWindSpeed());
            return weatherRepository.save(existingWeather);
        } else {
            throw new RuntimeException("Weather not found for province " + province);
        }
    }

    public void deleteWeather(String province) {
        Optional<WeatherEntity> weatherEntity = weatherRepository.findByProvince(province);
        weatherEntity.ifPresent(weatherRepository::delete);
    }

    @Async
    public void fetchAndSaveWeatherData(String province) {
        WeatherEntity weatherEntity = weatherDataFetcher.fetchWeatherData(province);
        if (weatherEntity != null) {
            weatherRepository.save(weatherEntity);
        }
    }

    @Scheduled(fixedRateString = "${weather.fetch.interval}")
    public void fetchWeatherDataForAllCities() {
        List<String> provinces = List.of(
                "Hanoi", "Ho Chi Minh City", "Da Nang", "Hai Phong", "Can Tho",
                "Ha Giang", "Cao Bang", "Bac Kan", "Tuyen Quang", "Lao Cai",
                "Dien Bien", "Lai Chau", "Son La", "Yen Bai", "Hoa Binh",
                "Thai Nguyen", "Lang Son", "Quang Ninh", "Bac Giang", "Phu Tho",
                "Vinh Phuc", "Bac Ninh", "Hai Duong", "Hung Yen", "Thai Binh",
                "Ha Nam", "Nam Dinh", "Ninh Binh", "Thanh Hoa", "Nghe An",
                "Ha Tinh", "Quang Binh", "Quang Tri", "Thua Thien Hue", "Quang Nam",
                "Quang Ngai", "Binh Dinh", "Phu Yen", "Khanh Hoa", "Ninh Thuan",
                "Binh Thuan", "Kon Tum", "Gia Lai", "Dak Lak", "Dak Nong",
                "Lam Dong", "Binh Phuoc", "Tay Ninh", "Binh Duong", "Dong Nai",
                "Ba Ria - Vung Tau", "Long An", "Tien Giang", "Ben Tre", "Tra Vinh",
                "Vinh Long", "Dong Thap", "An Giang", "Kien Giang", "Hau Giang",
                "Soc Trang", "Bac Lieu", "Ca Mau");
        for (String province : provinces) {
            fetchAndSaveWeatherData(province);
        }
    }
}