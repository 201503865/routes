#!/bin/bash
# Instalar Java 17
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 17.0.7-tem

# Dar permisos y construir
chmod +x gradlew
./gradlew bootRun