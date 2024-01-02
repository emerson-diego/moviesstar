# Estágio 1: Construção
FROM maven:3.6.3-openjdk-17 AS builder
WORKDIR /app

# Copiando apenas o pom.xml e baixando as dependências para aproveitar o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiando o resto do projeto e construindo
COPY src /app/src
RUN mvn package -DskipTests


# Estágio 2: Execução
FROM openjdk:17
WORKDIR /app

# Copiar o JAR do estágio de construção para o estágio de execução
COPY --from=builder /app/target/*.jar moviestarback.jar

# Expor a porta utilizada pela aplicação
EXPOSE 8083

# Definir o comando para iniciar a aplicação
ENTRYPOINT ["java","-jar","moviestarback.jar"]
#ENTRYPOINT ["java","-jar","target/moviesstar-0.0.1-SNAPSHOT.jar"]