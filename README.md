# Проект Диплом 2 API
## Что реализовано:
### Создание пользователя:
* создать уникального пользователя;
* создать пользователя, который уже зарегистрирован;
* создать пользователя и не заполнить одно из обязательных полей.
### Логин пользователя:
* логин под существующим пользователем,
* логин с неверным логином и паролем.
* Изменение данных пользователя:
* с авторизацией,
* без авторизации,
### Создание заказа:
* с авторизацией,
* без авторизации,
* с ингредиентами,
без ингредиентов,
* с неверным хешем ингредиентов.
### Получение заказов конкретного пользователя:
* авторизованный пользователь,
* неавторизованный пользователь.

## Зависимости:
* junit 5
* gson
* allure
* lombok
* allure-rest-assured

## Для запуска, используй команду:
_mvn clean test allure:serve_
