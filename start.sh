#!/bin/sh
echo "Starting the Weather App"
java -jar -Dspring.profiles.active=prod target/weather-app-0.0.1-SNAPSHOT.jar
