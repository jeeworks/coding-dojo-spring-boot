# Weather App

The weather app requests its data from [OpenWeather](https://openweathermap.org/) and stores the result in a database.


## Development

### System requirements
* [Java JDK - Version 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)
* [Docker](https://www.docker.com/) For running a production PostgreSQL database locally

### Prepare, Build, Run and Test

#### Prepare
Weather App use a PostgreSQL database in production and a H2 Database for testing locally. We have prepared a docker compose file with a PostgreSQL database.

Go to the 'docker' folder. In the folder run the following command:

    docker-compose up -d 

* Database username: admin
* Database password: 12345
* Database name weather_db

Create the weather table with the schema.sql file. That can be found in src/main/resources/data/

Generate a OpenWeather API key on [OpenWeather Sign up](https://openweathermap.org/appid) page.

Store the api key as environment variable: OPEN_WEATHER_APP_ID

#### Build
To build the application simply run:

    ./mvnw

#### Run
To run the application simply run:

    ./start.sh

### Test
Open a browser and go to the following address:

    http://localhost:8080/api/weather?city={City name}

Replace the '{city name}' to the city of your choice, you want to know the temperature of.

## Assignment Solution
### Added dependencies
* Spring Webflux
* Lombok
* Mapstruct
* Mockito
* H2
* Assertj

### Architecture changes
* Upgraded to Latest Spring boot version 2.3.1
* Splited the code into layers top down: 
    * Resource (Rest endpoint)
    * Service
    * Web Client
    * Repository
    * Mapper
    * Domain Entity
    * Configuration
    * Properties
* Instead of the RestTemplate for fetching rest data we use the Webflux Web Client
* Added unit tests
* Added docker-compose file to spin up a production database
* Added start script to start the production application
* Store secrets in environment variables, not hard coded
* Handle errors from the Open Weather api and present them to the user
