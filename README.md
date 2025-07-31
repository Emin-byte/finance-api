FINANCE API - QUICK SMOKE CHECK (порт 8080, Postgres на localhost:5433)

1. БД
   - Проверить доступность:
     pg_isready -h localhost -p 5433 -U postgres -d finance
   - Убедиться, что расширение есть:
     psql -h localhost -p 5433 -U postgres -d finance -c "CREATE EXTENSION IF NOT EXISTS pgcrypto;"

2. Запуск
   - Собрать и запустить:
     ./gradlew clean bootJar
     java -jar build/libs/finance-api-0.1.0.jar
   - В логах: Spring поднялся, созданы таблицы users и transactions.

3. API проверки
   a) Создать пользователя:
      curl -s -X POST http://localhost:8080/api/users \
        -H "Content-Type: application/json" \
        -d '{"name":"Smoke","initialBalance":1000.0}' | jq
      → Ожидается JSON с id, name="Smoke", balance=1000.0

   b) Создать транзакцию:
      curl -s -X POST http://localhost:8080/api/transactions \
        -H "Content-Type: application/json" \
        -d '{"userId":"<UUID>","amount":200.0}' | jq
      → amount=200, вложенный user.balance увеличился на 200

   c) Получить транзакции за диапазон:
      curl -s "http://localhost:8080/api/transactions?from=2025-07-01T00:00:00Z&to=2025-08-31T23:59:59Z" | jq
      → Массив с TransactionDto, каждый содержит user

   d) Фильтрация по userId:
      curl -s "http://localhost:8080/api/transactions?from=2025-07-01T00:00:00Z&to=2025-08-31T23:59:59Z&userId=<UUID>" | jq
      → Только транзакции указанного пользователя

4. Ошибки
   - Некорректный userId:
     curl -s -X POST http://localhost:8080/api/transactions \
       -H "Content-Type: application/json" \
       -d '{"userId":"bad","amount":10.0}' | jq
     → Структурированный JSON с ошибкой (не 500 без описания)

   - Пустой диапазон:
     curl -s "http://localhost:8080/api/transactions?from=2020-01-01T00:00:00Z&to=2020-01-02T00:00:00Z" | jq
     → Пустой массив

5. Дополнительно (по желанию)
   - Открыть Swagger UI: http://localhost:8080/swagger-ui/index.html
