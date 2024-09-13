package com.example.weatherinfo.controller;

import com.example.weatherinfo.entity.WeatherEntity;
import com.example.weatherinfo.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@AllArgsConstructor
@RequestMapping("/api/weathers")

public class WeatherController {

    private WeatherService weatherService;

    @GetMapping("")
    List<WeatherEntity> getAllWeathers() {
        return weatherService.getAllWeathers();
    }

    //api lấy thông tin theo tỉnh thành
    @GetMapping("/by-province")
    Optional<WeatherEntity> findByProvince(@RequestParam(name = "province") String province) {
        return weatherService.getWeatherByProvince(province);
    }

    //API phân trang
    @GetMapping("/provinces")
    public Page<WeatherEntity> getProvinces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return weatherService.getProvinces(PageRequest.of(page, size));
    }

    // API cập nha thông tin theo tỉnh thành
    @PutMapping("/{province}")
    public WeatherEntity updateWeather(
            @PathVariable @NotEmpty String province,
            @Valid @RequestBody WeatherEntity updatedWeather) {
        return weatherService.updateWeather(province, updatedWeather);
    }

    // API thêm thông tin theo tỉnh thành
    @PostMapping("")
    public WeatherEntity addWeather(@Valid @RequestBody WeatherEntity newWeather) {
        return weatherService.addWeather(newWeather);
    }

    // API xóa thông tin theo tỉnh thành
    @DeleteMapping("/{province}")
    public void deleteWeather(@PathVariable String province) {
        weatherService.deleteWeather(province);
    }
}