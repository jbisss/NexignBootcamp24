# Базовый image
FROM openjdk:19

# Переменная, в которой указывается путь к jar- архиву
ARG JAR_FILE=build/libs/*.jar

#Задаётся рабочая директория, в которой будут выполняться дальнейшие команды (перемещаемся в папку app)
WORKDIR /app

EXPOSE 8765

#Jar-файл с локального хоста (путь до него задан в переменной JAR_FILE) копируется в папку app, копии задаётся имя app.jar
COPY ${JAR_FILE} app.jar

# Команда запуска приложения
ENTRYPOINT ["java","-jar","app.jar"]