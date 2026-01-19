#Parte con un immagine base del Java runtime
FROM eclipse-temurin:25-jdk

#LABEL sostituisce MANTEINER per indicare chi mantiene questa immagine
LABEL org.opencontainers.image.authors="Gianfranco.com"

#Dico di prendere il file jar della cartella target e di copiarlo con lo stesso nome all'interno dell'immagine
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#Comando per eseguire il jar all'interno dell'immagine
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]

