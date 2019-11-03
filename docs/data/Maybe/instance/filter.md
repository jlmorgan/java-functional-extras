# `Maybe#<A>filter(Predicate<A> predicate)`

Tests the underlying value against the `predicate`, returning the `Just` of the value for `true`; otherwise, `Nothing`.

## Alternatives

* `Maybes.<A>filter(Predicate<A> predicate).apply(Maybe<A> maybe)`
* `Maybes.<A>filter(Predicate<A> predicate, Maybe<A> maybe)`

## Types

* `A`: The underlying type.

## Arguments

* `predicate (Predicate<A>)`: The predicate with which to test the value.

## Returns

* `(Maybe<A>)`: The `Just` of the value for `true`; otherwise, `Nothing`.

## Throws

* `NullPointerException` if the `predicate` or `maybe` is `null`.

## Examples

```java
Predicate<Integer> isEven = value -> value % 2 == 0;
Maybe.just(0)
  .filter(isEven);
// => Just(1)

Maybe.just(1)
  .filter(isEven);
// => Nothing()

Maybe.nothing()
  .filter(isEven);
// => Nothing()

Maybes.filter(isEven).apply(Maybe.just(0));
// => Just(0)

Maybes.filter(isEven, Maybe.just(1));
// => Nothing()

Maybes.filter(isEven, Maybe.nothing());
// => Nothing()
```
