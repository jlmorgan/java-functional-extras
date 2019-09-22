# `Maybe#<A, B>fmap(Function<A, B> morphism)`

Maps the underlying value of a `Maybe` in a `null`-safe way.

## Alternatives

* `Maybes.<A, B>fmap(Function<A, B> morphism).apply(Maybe<A> maybe)`
* `Maybes.<A, B>fmap(Function<A, B> morphism, Maybe<A> maybe)`

## Types

* `A`: The underlying type of the `Maybe`.
* `B`: The return type of the morphism.

## Arguments

* `morphism (Function<A, B>)`: The morphism.

## Returns

* `(Maybe<B>)`: The mapped `Maybe`.

## Throws

* `NullPointerException` if the `morphism` or `maybe` is `null`.

## Examples

```java
Function<Integer, Integer> square = value -> value * value;
Maybe.just(4)
  .fmap(square);
// => Just(16)

Maybe.nothing()
  .fmap(square);
// => Nothing()

Maybe.just(Collections.singletonList(null))
  .fmap(list -> list.get(0));
// => Nothing()
```
