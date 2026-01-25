# 1. 디자인 패턴 소개와 전략 패턴

## 문제 상황
- 슈퍼 클래스인 Duck에게 fly() 메서드가 있었는데, RubberDuck이 Duck을 상속받으며 날 수 있게 됨.

## 제시된 첫번째 해결책
- 슈퍼 클래스인 Duck에게 fly() 메서드를 없애고, Flyable 따위의 인터페이스를 만들어서 날 수 있는 오리들이 Flyable을 구현하도록 함.
- 하지만 자바 인터페이스에는 코드를 구현할 수 없으니 코드를 재사용할 수 없다는 문제가 있다.

## 디자인 원칙
- **애플리케이션에서 달라지는 부분을 찾아내고 달라지지 않는 부분과 분리한다.**
- 달라지는 부분을 찾아서 나머지 코드에 영향을 주지 않도록 '캡슐화'한다.
- 그러면 코드를 변경하는 과정에서 의도치 않게 발생하는 일을 줄이면서 시스템의 유연성을 향상시킬 수 있다.

## 디자인 원칙을 지키는 두번째 해결책
- FlyBehavior, QuackBehavior 인터페이스를 만들고, 각각 구체 전략을 만든다.
- Duck은 FlyBehavior, QuackBehavior 타입의 필드를 멤버로 가진다. (Composition)
- Duck은 performFly(), performQuack() 메서드를 가진다. 이 메서드를 호출하면 구체 전략의 메서드가 실행된다.
- 즉, 구체적인 내용은 인터페이스 구현체에게 위임하는 것!

## 상속과 컴포지션

- **IS-A (상속)**: Dog extends Animal이면 "개는 동물이다"라는 의미
- **HAS-A (컴포지션)**: Car가 Engine을 필드로 갖고 있으면 "자동차는 엔진을 가진다"는 의미

## 컴포지션을 쓰자

- 상속은 부모 클래스의 구현 세부사항에 의존하게 된다. 부모가 변경되면 자식이 깨질 수 있다.
- 컴포지션은 런타임에 구현체를 바꿀 수 있지만, 상속은 컴파일 타임에 고정된다.
- Java/Kotlin은 단일 상속만 가능한데, 컴포지션은 여러 객체를 조합할 수 있다. (꼭 필요할 때 상속 받지 못하는 경우가 생길 수 있음)

**상속은 정말 IS-A 관계가 확실할 때만 쓰고, 그 외에는 컴포지션을 우선 고려하자**

## 전략패턴!

> 전략 패턴은 알고리즘군을 정의하고 캡슐화해서 각각의 알고리즘군을 수정해서 쓸 수 있게 해준다. 전략 패턴을 사용하면 클라이언트로부터 알고리즘을 분리해서 독립적으로 변경할 수 있다.

![img](https://refactoring.guru/images/patterns/diagrams/strategy/structure-2x.png?id=5bd791857c3bab419bcf4fa86877439d)

**장점**
- 새로운 전략이 추가되어도 기존 코드를 변경하지 않는다. (OCP)
- 클라이언트가 Context 생성 시점 / Setter 호출 시점 / doSomething 호출 시점에 Strategy를 변경할 수 있다.
- 상속 대신 컴포지션을 사용할 수 있다.

**단점**
- 복잡도가 증가한다.
- 클라이언트가 구체 전략을 의존해야 한다.

변화 가능성
- 현구 said, 한 세기동안 변화하지 않았다면 변하지 않을 것!

## 스프링에서 구체 전략 의존 회피하기

- 스프링 사용 시 전략을 빈으로 등록하고 DI를 통해 구체 전략을 의존하지 않아도 되도록 우회할 수 있다.

```kotlin
@Service
class FooService(
    private val fooStrategy: FooStrategy  // 주입 시점에 고정됨
)
```

- 하지만 Spring DI는 기본적으로 런타임 동적 전환을 직접 지원하지 않는다.

```kotlin
@Service
class FooService(
    private val strategies: Map<String, FooStrategy>  // 모든 구현체 주입
) {
    fun pay(type: String, amount: Long) {
        val strategy = strategies[type] ?: throw IllegalArgumentException()
        strategy.execute(amount)
    }
}

// 구현체들
@Component("fo")
class FooOne : FooStrategy { ... }

@Component("fw")  
class FooTwo : FooStrategy { ... }
```

- 몇가지 우회 패턴이 있는데,
- Map 주입, List 주입, ... -> 모든 전략을 미리 주입받아서 런타임에 상황에 맞게 호출하는 방식.
    - 모든 전략을 미리 주입(생성)받으면 자원 낭비가 없는지 꼭 확인해야 한다. (ex. 초기화 오래 걸리거나, 내부 대용량 캐싱이 있다거나)
    - 그리고 특정 전략을 Fade out 할 때 숨겨진 의존성이 될 수 있다. (런타임에 주입되니 실제로 사용되는 곳을 찾기 힘듦)
- 오히려 구체 전략을 직접 의존하는 게 더 나은 선택일 수도 있다.

## 디자인 패턴을 배워야 하는 이유

- 개발자 간 쉽게 소통하기 위해서.
- 불필요한 디테일은 패턴으로 퉁치고 상위 수준에서의 설계에 집중해라!

---

**이미지 출처**
- https://refactoring.guru/ko/design-patterns/strategy