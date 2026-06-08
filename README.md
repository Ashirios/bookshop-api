
##  Описание проекта

REST API для книжного магазина с асинхронной обработкой заказов через RabbitMQ. 
Проект демонстрирует интеграцию Spring Boot с брокером сообщений для разгрузки 
основного потока обработки заказов.

##  Технологии

- **Java 21**
- **Spring Boot 4.0.2**
- **Spring Data JPA**
- **Spring AMQP (RabbitMQ)**
- **H2 Database**
- **Lombok**
- **Maven**

##  Архитектура

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Client    │────▶│   Spring    │────▶│   RabbitMQ  │────▶│   Consumer  │
│   (curl)    │     │  Producer   │     │   Queue     │     │   Service   │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
       │                  │                    │                    │
       │                  ▼                    │                    ▼
       │           ┌─────────────┐            │             ┌─────────────┐
       └──────────▶│   Database  │◀───────────┘             │   Database  │
                   │    (H2)     │                          │    (H2)     │
                   └─────────────┘                          └─────────────┘
```

##  Модель данных

### Order (Заказ)
```java
- id: Long
- userId: Long
- books: List<Book>
- totalAmount: Double
- status: OrderStatus (PENDING, PROCESSED, FAILED, CANCELLED)
- createdAt: LocalDateTime
- processedAt: LocalDateTime
```

### OrderMessageDto (Сообщение в очередь)
```java
- orderId: Long
- userId: Long
- bookCount: Integer
- totalPrice: Double
- timestamp: String
```

##  Настройка RabbitMQ

### Docker Compose
```yaml
services:
  rabbitmq:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"   # AMQP порт
      - "15672:15672" # Management UI
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
```

### Конфигурация
```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

order.queue.name=order.queue
order.exchange.name=order.exchange
order.routing.key=order.routing.key
```

##  Запуск проекта

### 1. Запуск RabbitMQ
```bash
docker-compose up -d
```

### 2. Запуск приложения
```bash
./mvnw spring-boot:run
```

### 3. Создание заказа
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "totalPrice": 29.99}'
```

### 4. Проверка статуса
```bash
curl http://localhost:8080/api/orders/{id}
```

##  Мониторинг

### RabbitMQ Management UI
- **URL**: http://localhost:15672
- **Login**: guest / guest
- **Queues**: order.queue
- **Exchanges**: order.exchange

### H2 Database Console
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: jdbc:h2:mem:bookshopdb
- **Login**: sa / (пусто)

##  Поток обработки заказа

1. **Client** отправляет POST запрос с данными заказа
2. **OrderController** сохраняет заказ в БД со статусом PENDING
3. **OrderMessageProducer** отправляет сообщение в RabbitMQ
4. **RabbitMQ** помещает сообщение в очередь `order.queue`
5. **OrderMessageConsumer** получает сообщение из очереди
6. **Consumer** обрабатывает заказ (имитация бизнес-логики)
7. **Consumer** обновляет статус заказа на PROCESSED в БД

##  Тестирование

### Тестовые сценарии
```bash
# Создать несколько заказов
curl -X POST http://localhost:8080/api/orders -d '{"userId":1,"totalPrice":29.99}'
curl -X POST http://localhost:8080/api/orders -d '{"userId":1,"totalPrice":49.99}'
curl -X POST http://localhost:8080/api/orders -d '{"userId":2,"totalPrice":99.99}'

# Получить все заказы
for i in 1 2 3; do
  curl http://localhost:8080/api/orders/$i
done
```

## 📁 Структура проекта

```
bookshop-api/
├── src/main/java/.../bookshop_api/
│   ├── configurations/
│   │   └── RabbitMQConfiguration.java      # Конфигурация RabbitMQ
│   ├── controller/
│   │   └── OrderController.java            # REST контроллер
│   ├── dto/
│   │   ├── CreateOrderRequest.java         # DTO для создания заказа
│   │   └── OrderMessageDto.java            # DTO для сообщений
│   ├── entity/
│   │   ├── Order.java                      # Сущность заказа
│   │   └── OrderStatus.java                # Enum статусов
│   ├── repository/
│   │   └── OrderRepository.java            # JPA репозиторий
│   └── service/
│       ├── OrderMessageProducer.java       # Отправка сообщений
│       └── OrderMessageConsumer.java       # Получение и обработка
└── src/main/resources/
    └── application.properties              # Конфигурация приложения
```

## 🔍 Ключевые моменты реализации

### Producer (Отправка сообщений)
```java
@Service
public class OrderMessageProducer {
    @Value("${order.exchange.name}")
    private String exchange;
    
    @Value("${order.routing.key}")
    private String routingKey;
    
    public void sendOrderMessage(OrderMessageDto message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
```

### Consumer (Получение сообщений)
```java
@Service
public class OrderMessageConsumer {
    @RabbitListener(queues = "${order.queue.name}")
    public void consumeOrderMessage(OrderMessageDto message) {
        // Бизнес-логика обработки
        order.setStatus(OrderStatus.PROCESSED);
        orderRepository.save(order);
    }
}
```

##  Преимущества асинхронной обработки

- **Разгрузка основного потока** - API быстро отвечает клиенту
- **Масштабируемость** - можно добавить несколько Consumer'ов
- **Надежность** - сообщения сохраняются в очереди до обработки
- **Отказоустойчивость** - при падении Consumer'а сообщения не теряются

##  Возможные улучшения

- [ ] Добавить Dead Letter Queue для обработки ошибок
- [ ] Реализовать retry механизм с экспоненциальной задержкой
- [ ] Добавить email уведомления после обработки заказа
- [ ] Внедрить WebSocket для real-time обновлений статуса
- [ ] Добавить мониторинг очередей (Prometheus + Grafana)
- [ ] Написать интеграционные тесты

##  Используемые материалы

- [Spring Boot AMQP Documentation](https://spring.io/projects/spring-amqp)
- [RabbitMQ Documentation](https://www.rabbitmq.com/documentation.html)
- [Docker Compose for RabbitMQ](https://hub.docker.com/_/rabbitmq)

##  Автор

Ashirios

---
