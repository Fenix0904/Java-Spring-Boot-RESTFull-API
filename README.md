# Spring-Boot-RESTFull-API JAVA

### Problem definition
Створити невеликий RESTful веб сервіс, який буде відповідати наступним бізнес вимогам:

- Сервіс повинен повертати REST API відповіді і мати наступну функціональність:
  - створювати новий контракт (`name`, `surname`, `personal id`, `term` та інші поля (за бажанням))
  - повертати список всіх контрактів
  - повертати список контрактів по id користувача
- Сервіс повинен перелбачати валідацію запитів відповідно наступним правилам і відхиляти запит, якщо:
  - Запит приходить від користувача, який знаходиться в чорному списку
  - Кількість запитів / секунду з одної країни перевищує певне значення
- Сервіс повинен визначати країну користувача і зберігати. За замовчуванням код країни має бути виставлений в - "lv".

### Technical requirements
- Spring Boot, Spring MVC
- Hibernate
- MySQL або Spring Data (на вибір)
- JUnit
