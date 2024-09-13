# Weather Information Service

This project is a Spring Boot application that provides weather information for various provinces and cities in Vietnam. The service retrieves data from the OpenWeather API and stores it in a local MySQL database. It includes RESTful APIs to get, update, add, and delete weather data.

## Features

- Fetches weather data from the OpenWeather API every 5 minutes (configurable) for all cities and provinces in Vietnam.
- Stores weather data in a MySQL database.
- Provides RESTful APIs for:
    - Retrieving weather information by province.
    - Retrieving paginated list of all provinces.
    - Adding, updating, and deleting weather information for a specific province.

## Getting Started

### Prerequisites

- Java: 22
- Maven: 3.9.9
- Spring Boot: 3.3.3
- Mysql: 9.0.1
- An OpenWeather API key

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/nghianehhh/weather_info.git
   cd weather-info-service
   
2. Set up the MySQL database:
   Create a MySQL database named ocb_nghia.
    ```bash   
    CREATE DATABASE ocb_nghia;
3. Configure the application properties: Open src/main/resources/application.yml and configure your database connection details and OpenWeather API key:
   ```bash
    spring:
    datasource:
    url: jdbc:mysql://localhost:3306/ocb_nghia?allowPublicKeyRetrieval=true&useSSL=false
    username: root
    password: Ngh12012002* # replace with your password
    
    weather:
    api:
    key: your_openweather_api_key_here
    fetch:
    interval: 300000
4. Build and run the application:
    ```bash
   mvn spring-boot:run
   
