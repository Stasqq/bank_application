### Bank application

Simple application simulating creating account and allowing transfers between them.

## Used technology

Java 17

Spring Boot - Spring Security, Spring MVC, Spring Data JPA

Oracle SQL

## Usage

Application contains three endpoints:
- Registration - POST method on "/api/user/register" with request body in JSON format, example request:
```
{
    "userName": "JanKowalski",
    "password": "password",
    "phoneNumber": "123456789",
    "email": "jan@kowalski.com",
    "notificationChannel": "SMS"
}
```
- Account creation - POST method on "/api/account/create" with request body in JSON format, example request:
```
{
    "username": "JanKowalski",
    "promoCode": "KOD_1"
}
```
- Transfer order - POST method on "/api/transfer/order" with request body in JSON format, example request:
```
{
    "fromAccountNumber": "11111111111111111111",
    "toAccountNumber": "22222222222222222222",
    "amount": 10
}
```

Registration endpoint is not secured with authentication, account creation and transfer order are secured with Basic Auth - user needs to provide username and password used in registration.
