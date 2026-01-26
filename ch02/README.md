# 2. 옵저버 패턴

## 옵저버패턴!

> 옵저버 패턴은 한 객체의 상태가 바뀌면 그 객체에 의존하는 다른 객체에게 연락이 가고 자동으로 내용이 갱신되는 방식으로 일대다(one-to-many) 의존성을 정의합니다.

![img](https://refactoring.guru/images/patterns/diagrams/observer/structure-2x.png?id=228af9bded4d6ee6daf43a0e23cca9ff)

- 주제(Subject; Publisher)와 옵저버(Subscriber)로 일대다 관계가 정의된다. 옵저버는 주제에 딸려 있으며 주제의 상태가 바뀌면 옵저버에게 정보가 전달된다.
- 주제 1개당 옵저버 N개가 매핑된다. 주제도 여러개 존재할 수 있어서 Publisher를 인터페이스로하고 Publisher를 구현하는 구체 주제가 N개 존재할 수 있다.

## 느슨한 결합
- 상호작용하는 객체 사이에는 가능하면 느슨한 결합을 사용해야 한다.
- 느슨하게 결합하는 디자인을 사용하면 변경 사항이 생겨도 무난히 처리할 수 있는 유연한 객체지향 시스템을 구축할 수 있다.
- 객체 사이의 상호의존성을 최소화 할 수 있다.

## 장점
- 상태를 변경하는 객체(publisher)와 변경을 감지하는 객체(subscriber)의 관계를 느슨하게 유지할 수 있다.
- Subject의 상태 변경을 주기적으로 조회하지 않고 자동으로 감지할 수 있다.
- 런타임에 옵저버를 추가하거나 제거할 수 있다.

## 단점
- 복잡도가 증가한다.
- 다수의 Observer 객체 등록 이후 해지하지 않으면 memory leak이 발생할 수 있다.

## Weak Reference 활용해서 memory leak 완화하기

- Publisher가 Observer를 강한 참조로 들고 있으면, 사용자가 Observer를 버려도 GC가 수거 못 함
- `removeObserver()` 호출을 깜빡하면 Observer가 영원히 메모리에 남음
- Publisher가 `WeakReference<Observer>`로 저장하면, 사용자가 참조를 끊는 것만으로 GC 대상이 됨
- Weak reference는 안전망 역할이고 GC 타이밍에 의존하기 때문에 `removeObserver()`를 명시적으로 호출하는 게 더 좋음

---

이미지 출처
- https://refactoring.guru/ko/design-patterns/observer