#!/bin/bash
# Asegúrate de tener los permisos correctos para ejecutar Gradle
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 17.0.7-tem
chmod +x ./gradlew
# Ejecuta la compilación de Gradle
./gradlew clean build
# Copia el JAR generado a donde Vercel puede encontrarlo
mkdir -p public
cp build/libs/*.jar public/