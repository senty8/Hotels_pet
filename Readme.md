# Hotels pet

Пет проект для работы с CRUD операциями для базы данных отелей.

# Использованные технологии

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [Hibernate](https://hibernate.org)
* [H2](https://ru.wikipedia.org/wiki/H2)
* [Liquibase](https://www.liquibase.org/)
* [Maven](https://maven.apache.org)
* [Docker](https://hub.docker.com)


# Как запустить локально?

1. Клонируем репозиторий

```shell
git clone https://github.com/senty8/Hotels_pet
```

2. Собираем проект (для сборки рекомендуется использовать amazon corretto 17)

```shell
mvn clean install
```

3. Запуск проекта

```shell
mvn spring-boot:run
```

Примечание: 
по умолчанию проект запускается на h2, также есть возможность запустить его на postgres.

1. Клонируем репозиторий
2. Запускаем docker-compose

```shell
docker-compose up -d
```

# Код

* Обычная трёхслойная
  архитектура – Controller, Service, Repository
* Слой Repository реализован на JPA
* При запуске проекта h2 доступен [тут](http://localhost:8092/hotel_db)
* Swagger доступен [тут](http://localhost:8092/property-view/swagger-ui.html)
* Отчет jacoco доступен после сборки проекта в target/site/jacoco/index.html

# Тесты

* Для тестирования используются
* Mockito
* JUnit5

* Код покрыт Unit тестами на 71%
* Также написаны интеграционные тесты на все endpoints