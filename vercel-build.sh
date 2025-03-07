#!/bin/bash
# Instalar Java 17
sudo apt install openjdk-17-jdk

# Dar permisos y construir
chmod +x gradlew
./gradlew bootRun