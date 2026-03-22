# 팩토리 패턴 (Factory Pattern)

## 1. 팩토리 패턴이란?

**정의**: 객체 생성 로직을 별도 클래스로 분리하여 캡슐화하는 패턴

**핵심 아이디어**: 객체를 직접 생성(new)하지 않고, 팩토리에 요청하여 생성

### 기본 개념

```java
// Before: 직접 생성
const pizza = new CheesePizza();
pizza.prepare();
pizza.bake();

// After: 팩토리 사용
const store = new PizzaStore();
const pizza = store.orderPizza('cheese');
```

---

## 2. 왜 필요한가?

### 문제 상황

```java
// 조건에 따라 다른 객체를 생성해야 하는 경우
let monster: Monster;

if (level === 1) {
  monster = new Slime();
  monster.setHP(10);
} else if (level === 2) {
  monster = new Goblin();
  monster.setHP(30);
} else if (level === 3) {
  monster = new Dragon();
  monster.setHP(100);
}
```

**문제점**:

- 새로운 타입 추가 시 모든 곳을 수정해야 함
- 생성 로직이 여러 곳에 중복됨
- 변경에 취약한 코드

### 해결책: 팩토리

```java
class MonsterFactory {
  createMonster(level: number): Monster {
    switch (level) {
      case 1:
        return new Slime();
      case 2:
        return new Goblin();
      case 3:
        return new Dragon();
      default:
        throw new Error('Invalid level');
    }
  }
}

// 사용
const factory = new MonsterFactory();
const monster = factory.createMonster(level);
```

**장점**:

- 생성 로직을 한 곳에서 관리
- 새로운 타입 추가가 용이
- 코드 중복 제거

---

## 3. 세 가지 팩토리 패턴

### 3.1 간단한 팩토리 (Simple Factory)

객체 생성을 전담하는 클래스

```java
class DrinkFactory {
  makeDrink(type: string): Drink {
    switch (type) {
      case 'coffee':
        return new Coffee();
      case 'tea':
        return new Tea();
      case 'juice':
        return new Juice();
      default:
        throw new Error('Unknown drink type');
    }
  }
}
```

**사용 시점**: 단순한 객체 생성 로직을 캡슐화할 때

---

### 3.2 팩토리 메소드 패턴 (Factory Method)

객체 생성을 서브클래스에 위임

```java
// 추상 카페 (본사 매뉴얼)
abstract class Cafe {
    // 음료 주문 과정 (모든 지점 공통)
    public Drink orderDrink(String type) {
        Drink drink = makeDrink(type);  // 각 지점이 알아서 만듦

        drink.prepare();  // 준비
        drink.serve();    // 서빙

        return drink;
    }

    // 각 지점이 구현
    protected abstract Drink makeDrink(String type);
}

// 강남점
class GangnamCafe extends Cafe {
    protected Drink makeDrink(String type) {
        if (type.equals("coffee")) {
            return new FancyCoffee();
        }
    }
}

// 홍대점
class HongdaeCafe extends Cafe {
    protected Drink makeDrink(String type) {
        if (type.equals("coffee")) {
            return new ArtisticCoffee();
        }
    }
}

// 사용
Cafe gangnamStore = new GangnamCafe();
Drink drink1 = gangnamStore.orderDrink("coffee"); // FancyCoffee

Cafe hongdaeStore = new HongdaeCafe();
Drink drink2 = hongdaeStore.orderDrink("coffee");  // ArtisticCoffee
```

**사용 시점**: 생성 프로세스는 동일하지만 생성되는 객체가 달라질 때

---

### 3.3 추상 팩토리 패턴 (Abstract Factory)

관련된 객체들을 일관성 있게 생성

```java
// 팩토리 인터페이스
interface MealFactory {
  createBurger(): Burger;
  createDrink(): Drink;
  createDessert(): Dessert;
}

// 아침 세트 팩토리
class BreakfastFactory implements MealFactory {
  createBurger(): Burger {
    return new EggBurger();
  }

  createDrink(): Drink {
    return new OrangeJuice();
  }

  createDessert(): Dessert {
    return new Yogurt();
  }
}

// 저녁 세트 팩토리
class DinnerFactory implements MealFactory {
  createBurger(): Burger {
    return new BeefBurger();
  }

  createDrink(): Drink {
    return new Cola();
  }

  createDessert(): Dessert {
    return new IceCream();
  }
}

// 사용
MealFactory factory;

if (time == "morning") {
    factory = new BreakfastFactory();
} else {
    factory = new DinnerFactory();
}

// 세트로 주문!
Burger burger = factory.makeBurger();
Drink drink = factory.makeDrink();
Dessert dessert = factory.makeDessert();
```

**사용 시점**: 서로 관련된 여러 객체를 일관되게 생성해야 할 때

---

## 4. 실전 활용 예시

### 예시 1: 알림 시스템

```tsx
interface Notification {
  send(message: string): void;
}

class EmailNotification implements Notification {
  send(message: string): void {
    console.log(`Email: ${message}`);
  }
}

class SmsNotification implements Notification {
  send(message: string): void {
    console.log(`SMS: ${message}`);
  }
}

class PushNotification implements Notification {
  send(message: string): void {
    console.log(`Push: ${message}`);
  }
}

class NotificationFactory {
  create(type: string): Notification {
    switch (type) {
      case 'email':
        return new EmailNotification();
      case 'sms':
        return new SmsNotification();
      case 'push':
        return new PushNotification();
      default:
        throw new Error('Unknown notification type');
    }
  }
}

// 사용
const factory = new NotificationFactory();
const notification = factory.create('email');
notification.send('Hello!');
```

---

### 예시 2: 결제 시스템

```tsx
interface PaymentProcessor {
  process(amount: number): Promise<boolean>;
}

class CardPayment implements PaymentProcessor {
  async process(amount: number): Promise<boolean> {
    console.log(`카드 결제: ${amount}원`);
    return true;
  }
}

class KakaoPayment implements PaymentProcessor {
  async process(amount: number): Promise<boolean> {
    console.log(`카카오페이 결제: ${amount}원`);
    return true;
  }
}

class TossPayment implements PaymentProcessor {
  async process(amount: number): Promise<boolean> {
    console.log(`토스 결제: ${amount}원`);
    return true;
  }
}

class PaymentFactory {
  create(method: string): PaymentProcessor {
    switch (method) {
      case 'card':
        return new CardPayment();
      case 'kakao':
        return new KakaoPayment();
      case 'toss':
        return new TossPayment();
      default:
        throw new Error('지원하지 않는 결제 방법');
    }
  }
}

// 사용
const factory = new PaymentFactory();
const payment = factory.create('kakao');
await payment.process(10000);
```

---

### 예시 3: 데이터베이스 연결

```tsx
interface Database {
  connect(): Promise<void>;
  query(sql: string): Promise<any>;
  disconnect(): Promise<void>;
}

class MySQL implements Database {
  async connect(): Promise<void> {
    console.log('MySQL 연결');
  }

  async query(sql: string): Promise<any> {
    console.log(`MySQL 쿼리 실행: ${sql}`);
    return [];
  }

  async disconnect(): Promise<void> {
    console.log('MySQL 연결 해제');
  }
}

class PostgreSQL implements Database {
  async connect(): Promise<void> {
    console.log('PostgreSQL 연결');
  }

  async query(sql: string): Promise<any> {
    console.log(`PostgreSQL 쿼리 실행: ${sql}`);
    return [];
  }

  async disconnect(): Promise<void> {
    console.log('PostgreSQL 연결 해제');
  }
}

class MongoDB implements Database {
  async connect(): Promise<void> {
    console.log('MongoDB 연결');
  }

  async query(sql: string): Promise<any> {
    console.log(`MongoDB 쿼리 실행: ${sql}`);
    return [];
  }

  async disconnect(): Promise<void> {
    console.log('MongoDB 연결 해제');
  }
}

class DatabaseFactory {
  create(type: string): Database {
    switch (type) {
      case 'mysql':
        return new MySQL();
      case 'postgresql':
        return new PostgreSQL();
      case 'mongodb':
        return new MongoDB();
      default:
        throw new Error('지원하지 않는 데이터베이스');
    }
  }
}

// 사용
const factory = new DatabaseFactory();
const db = factory.create('postgresql');
await db.connect();
await db.query('SELECT * FROM users');
```

---

### 예시 4: 로거 시스템

```tsx
interface Logger {
  info(message: string): void;
  error(message: string): void;
  warn(message: string): void;
}

class ConsoleLogger implements Logger {
  info(message: string): void {
    console.log(`[INFO] ${message}`);
  }

  error(message: string): void {
    console.error(`[ERROR] ${message}`);
  }

  warn(message: string): void {
    console.warn(`[WARN] ${message}`);
  }
}

class FileLogger implements Logger {
  info(message: string): void {
    console.log(`파일 기록: [INFO] ${message}`);
  }

  error(message: string): void {
    console.log(`파일 기록: [ERROR] ${message}`);
  }

  warn(message: string): void {
    console.log(`파일 기록: [WARN] ${message}`);
  }
}

class CloudLogger implements Logger {
  info(message: string): void {
    console.log(`클라우드 전송: [INFO] ${message}`);
  }

  error(message: string): void {
    console.log(`클라우드 전송: [ERROR] ${message}`);
  }

  warn(message: string): void {
    console.log(`클라우드 전송: [WARN] ${message}`);
  }
}

class LoggerFactory {
  create(env: string): Logger {
    switch (env) {
      case 'development':
        return new ConsoleLogger();
      case 'production':
        return new CloudLogger();
      case 'test':
        return new FileLogger();
      default:
        return new ConsoleLogger();
    }
  }
}

// 사용
const factory = new LoggerFactory();
const logger = factory.create(process.env.NODE_ENV || 'development');
logger.info('Application started');
```

---

### 예시 5: HTTP 클라이언트

외부 API 요청 시

```tsx
interface ApiClient {
  get(url: string): Promise<any>;
  post(url: string, data: any): Promise<any>;
}

class RestApiClient implements ApiClient {
  async get(url: string): Promise<any> {
    console.log(`REST GET: ${url}`);
    return { data: 'rest response' };
  }

  async post(url: string, data: any): Promise<any> {
    console.log(`REST POST: ${url}`, data);
    return { success: true };
  }
}

class GraphQLClient implements ApiClient {
  async get(url: string): Promise<any> {
    console.log(`GraphQL Query: ${url}`);
    return { data: 'graphql response' };
  }

  async post(url: string, data: any): Promise<any> {
    console.log(`GraphQL Mutation: ${url}`, data);
    return { success: true };
  }
}

class ApiClientFactory {
  create(type: string): ApiClient {
    switch (type) {
      case 'rest':
        return new RestApiClient();
      case 'graphql':
        return new GraphQLClient();
      default:
        throw new Error('Unknown API type');
    }
  }
}

// 사용
const factory = new ApiClientFactory();
const client = factory.create('rest');
const response = await client.get('/api/users');
```

---

## 5. 언제 사용하나?

### 사용해야 할 때

- 객체 생성 로직이 복잡할 때
- 런타임에 생성할 객체 타입이 결정될 때
- 새로운 타입이 자주 추가될 것으로 예상될 때
- 관련된 객체들을 일관성 있게 생성해야 할 때

### 사용하지 말아야 할 때

- 객체 생성이 매우 단순할 때
- 변경 가능성이 거의 없을 때
- 오버엔지니어링이 될 수 있을 때

---

## 6. 핵심 정리

### 패턴별 특징

| 패턴 | 특징 | 사용 시점 |
| --- | --- | --- |
| 간단한 팩토리 | 객체 생성을 한 곳에서 처리 | 단순한 생성 로직 |
| 팩토리 메소드 | 서브클래스가 생성 담당 | 생성 로직이 다양할 때 |
| 추상 팩토리 | 관련 객체들을 세트로 생성 | 제품군을 생성할 때 |
