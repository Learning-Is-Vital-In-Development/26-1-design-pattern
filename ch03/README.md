# 데코레이터 패턴 (Decorator Pattern)

## 스타버즈 커피 설계

### 초기 설계의 문제점

스타버즈 커피는 다양한 음료와 첨가물(우유, 두유, 모카, 휘핑크림)을 조합하여 판매. 초기 설계에서는 모든 조합을 클래스로 생성


```
                    ┌─────────────────┐
                    │    Beverage     │
                    ├─────────────────┤
                    │ description     │
                    ├─────────────────┤
                    │ getDescription()│
                    │ cost()          │
                    └─────────────────┘
                             △
                             │
        ┌────────────────────┼────────────────────┬─────────────────────┐
        │                    │                    │                     │
┌───────────────┐  ┌─────────────────┐  ┌──────────────┐  ┌────────────────────────────────┐
│  HouseBlend   │  │    DarkRoast    │  │   Espresso   │  │ HouseBlendWithSteamedMilk      │
├───────────────┤  ├─────────────────┤  ├──────────────┤  │ HouseBlendWithMocha            │
│ cost()        │  │ cost()          │  │ cost()       │  │ HouseBlendWithSteamedMilkMocha │
└───────────────┘  └─────────────────┘  └──────────────┘  │ DarkRoastWithSteamedMilk       │
                                                          │ DarkRoastWithMocha             │
                                                          │ DarkRoastWithWhip              │
                                                          │ EspressoWithSteamedMilkMocha   │
                                                          │ ...                            │
                                                          └────────────────────────────────┘
                                                                    클래스 폭발!
```

```java
class HouseBlendWithMilk extends Beverage {
    public double cost() { return 0.89 + 0.10; }
}

class HouseBlendWithMocha extends Beverage {
    public double cost() { return 0.89 + 0.20; }
}

class HouseBlendWithMilkAndMocha extends Beverage {
    public double cost() { return 0.89 + 0.10 + 0.20; }
}
```

**문제점**
- 클래스 폭발(Class Explosion) - 조합마다 클래스 생성
- 첨가물 가격 변경 시 모든 클래스 수정 필요
- 새로운 첨가물 추가 시 기하급수적으로 클래스 증가
- 실행 중 첨가물 변경 불가능

### 개선: 변수 추가

```
┌─────────────────────┐
│      Beverage       │
├─────────────────────┤
│ description         │
│ milk: boolean       │
│ soy: boolean        │
│ mocha: boolean      │
│ whip: boolean       │
├─────────────────────┤
│ getDescription()    │
│ cost()              │
│ hasMilk()           │
│ setMilk()           │
│ ...                 │
└─────────────────────┘
          △
          │
    ┌─────┼─────┬─────────┐
    │     │     │         │
┌───────┐ ┌───────┐ ┌───────┐ ┌───────┐
│House  │ │Dark   │ │Decaf  │ │Espre- │
│Blend  │ │Roast  │ │       │ │sso    │
└───────┘ └───────┘ └───────┘ └───────┘
```

```java
public abstract class Beverage {
    String description = "Beverage";
    boolean milk, soy, mocha, whip;

    // getter/setter 생략 (hasMilk(), setMilk(), ...)

    public String getDescription() { return description; }

    // 첨가물 가격 계산
    public double cost() {
        double condimentCost = 0;
        if (milk) condimentCost += 0.10;
        if (soy) condimentCost += 0.15;
        if (mocha) condimentCost += 0.20;
        if (whip) condimentCost += 0.10;
        return condimentCost;
    }
}

public class HouseBlend extends Beverage {
    public HouseBlend() { description = "House Blend Coffee"; }

    @Override
    public double cost() {
        return 0.89 + super.cost();  // 기본가격 + 첨가물 가격
    }
}
```

**문제점**
- 첨가물 가격 변경 시 기존 코드 수정 필요
- 새로운 첨가물 추가 시 슈퍼클래스 수정 필요
- 특정 음료에 맞지 않는 첨가물도 상속받게 됨
- 휘핑크림 두번 추가 같은 중복 추가 처리 어려움

### SOLID 원칙 위반

위 두 설계 모두 SOLID 원칙을 위반함.

**OCP (Open-Closed Principle) 위반**
> 클래스는 확장에는 열려 있어야 하지만, 변경에는 닫혀 있어야 한다.

|  | 초기 설계 | 개선 |
|----|-----------|-------|
| 새 첨가물 추가 | 모든 조합 클래스 새로 생성 | Beverage 슈퍼클래스 수정 |
| 첨가물 가격 변경 | 관련 클래스 모두 수정 | Beverage의 cost() 수정 |

**SRP (Single Responsibility Principle) 위반**
> 클래스는 하나의 책임만 가져야 한다.

Beverage 클래스가 너무 많은 책임을 가짐.
- 음료 기본 정보 관리
- 모든 첨가물 상태 관리 (milk, soy, mocha, whip)
- 첨가물 가격 계산

**잘못된 상속 구조**

특정 음료에 맞지 않는 첨가물도 상속받게 됨.
```java
class Tea extends Beverage {
    // 차(Tea)인데 setWhip(), hasMocha() 등을 갖게 됨
    // 의미적으로 맞지 않는 메서드들이 포함됨
}
```

**기존 코드를 수정하지 않고 확장할 수 있는 새로운 설계가 필요!**

---

## 데코레이터 패턴

> **데코레이터 패턴**은 객체에 추가 요소를 동적으로 더할 수 있습니다.
> 데코레이터를 사용하면 서브클래스를 만들 때보다 훨씬 유연하게 기능을 확장할 수 있습니다.

### 상속 vs 구성

**상속의 한계**
- 상속은 **"종류"** 표현에 적합 (Espresso는 Beverage의 한 종류)
- 하지만 **"조합"** 문제에는 부적합 (모카 + 휘핑 + 우유 조합)
- 상속으로 행동을 받으면 컴파일 시에 정적으로 결정됨

**구성(Composition)의 장점**
- **런타임에 동적으로 조합** 가능
- 기존 코드를 수정하지 않고 새로운 기능 추가 가능
- 데코레이터가 Beverage를 상속받는 이유는 행동을 물려받기 위함이 아니라 **형식(타입)을 맞춰 다형성을 활용**하기 위함
- 실제 행동은 감싸고 있는 객체에 **위임**하고, 추가 작업 수행

```java
// 상속: 컴파일 타임에 고정
class MochaEspresso extends Espresso { }  // 이미 정해짐

// 구성: 런타임에 조합
Beverage b = new Espresso();
b = new Mocha(b);  // 실행 중에 모카 추가!
b = new Whip(b);   // 실행 중에 휘핑 추가!
```

### 패턴 구조

```
        ┌─────────────────────┐
        │     Component       │ ◀─── 추상 구성 요소
        │─────────────────────│
        │ methodA()           │
        │ methodB()           │
        └─────────────────────┘
                   △
                   │
       ┌───────────┴───────────┐
       │                       │
┌──────────────────┐    ┌─────────────────────┐
│ ConcreteComponent│    │     Decorator       │ ◀─── 추상 데코레이터
│──────────────────│    │─────────────────────│
│ methodA()        │    │ component: Component│ ────▶ Component를 감쌈
│ methodB()        │    │─────────────────────│
└──────────────────┘    │ methodA()           │
     구상 구성 요소        │ methodB()           │
                        └─────────────────────┘
                                  △
                                  │
                    ┌─────────────┴─────────────┐
                    │                           │
           ┌──────────────────┐         ┌──────────────────┐
           │ConcreteDecoratorA│         │ConcreteDecoratorB│
           │──────────────────│         │──────────────────│
           │ methodA()        │         │ methodA()        │
           │ methodB()        │         │ methodB()        │
           │ newBehavior()    │         └──────────────────┘
           └──────────────────┘
               구상 데코레이터
```

---

## 데코레이터 패턴 적용

**추상 구성 요소 (Beverage)**
```java
public abstract class Beverage {
    String description = "";
    public String getDescription() { return description; }
    public abstract double cost();
}
```

**구상 구성 요소 (Espresso)**
```java
public class Espresso extends Beverage {
    public Espresso() {
        description = "에스프레소";
    }
    public double cost() {
        return 1.99;
    }
}
```

**추상 데코레이터 (CondimentDecorator)**
```java
public abstract class CondimentDecorator extends Beverage {
    Beverage beverage;  // 감쌀 음료 객체
    public abstract String getDescription();
}
```

**구상 데코레이터 예시 (Mocha)**
```java
public class Mocha extends CondimentDecorator {
    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }
    public String getDescription() {
        return beverage.getDescription() + ", 모카";
    }
    public double cost() {
        return beverage.cost() + .20;
    }
}
```

### 사용 예시

```java
// 다크 로스트 + 더블 모카 + 휘핑크림
Beverage beverage = new DarkRoast();
beverage = new Mocha(beverage);
beverage = new Whip(beverage);  // 더블!
beverage = new Whip(beverage);
System.out.println(beverage.getDescription() + " $" + beverage.cost());
// 출력: 다크 로스트, 모카, 휘핑크림, 휘핑크림 $1.49
```

**"모카 + 휘핑크림 2번 얹은 다크 로스트"의 가격 계산**

```
cost() 호출 시:
   → Whip.cost()
     → Whip.cost()
       → Mocha.cost()
         → DarkRoast.cost() → 0.99 반환
       → 0.99 + 0.20 = 1.19 반환
     → 1.19 + 0.10 = 1.29 반환
   → 1.29 + 0.10 = 1.39 반환
```

---

## Java I/O

Java의 I/O 라이브러리는 데코레이터 패턴을 활용함.

```java
// FileInputStream을 BufferedInputStream으로 감싸서 버퍼링 기능 추가
// 다시 DataInputStream으로 감싸서 기본 타입 읽기 기능 추가
InputStream in =
    new DataInputStream(
        new BufferedInputStream(
            new FileInputStream("test.txt")));
```

```
InputStream (추상 구성 요소)
    ├── FileInputStream (구상 구성 요소)
    ├── ByteArrayInputStream (구상 구성 요소)
    └── FilterInputStream (추상 데코레이터)
            ├── BufferedInputStream (버퍼링 기능)
            ├── DataInputStream (기본 타입 읽기)
            └── PushbackInputStream (읽은 데이터 되돌리기)
```

## Java Collections

`Collections` 유틸리티 클래스도 데코레이터 패턴을 활용함

```java
// 타입 체크 기능 추가
List<String> checked = Collections.checkedList(list, String.class);

// 불변성 기능 추가
List<String> readOnly = Collections.unmodifiableList(list);
```

원본 컬렉션을 감싸서 **기존 코드 수정 없이** 부가 기능 추가!

---

## 장단점

**장점**
- 기존 코드 수정 없이 새로운 기능을 동적으로 추가 가능 (OCP 준수)
- 상속보다 유연한 기능 확장
- 실행 중에 데코레이터 조합 변경 가능

**단점**
- 데코레이터를 여러 겹 감싸면 코드가 복잡해짐
  ```java
  new Whip(new Mocha(new Mocha(new DarkRoast())))
  ```
- 어느 데코레이터에서 문제인지 추적이 번거로울 수 있음
- 이런 경우 팩토리/빌더 패턴으로 생성 로직을 캡슐화하면 해결 가능

**빌더 패턴으로 해결**
```java
// Before: 복잡한 생성 코드
new Whip(new Mocha(new Mocha(new DarkRoast())))

// After: 빌더 패턴 적용
BeverageBuilder.darkRoast()
    .addMocha()
    .addMocha()
    .addWhip()
    .build();
```

**팩토리 패턴으로 해결**
```java
class BeverageFactory {
    static Beverage createMochaLatte() {
        return new Mocha(new Mocha(new Milk(new Espresso())));
    }
}

// 사용
Beverage latte = BeverageFactory.createMochaLatte();
```
