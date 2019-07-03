# `Either.<L, R, C>eitherMap(Function<L, C> leftMorphism, Function<R, C> rightMorphism, Either<L, R> either)`

Provides a catamorphism of the `either` to a singular value. If the value is `Left a`, apply the first function to `a`; otherwise, apply the second function to `b`.

## Alternatives

* `Either.<L, R, C>eitherMap(Function<L, C> leftMorphism, Function<R, C> rightMorphism).apply(Either<L, R> either)`
* `Either.<L, R, C>eitherMap(Function<L, C> leftMorphism).apply(Function<R, C> rightMorphism).apply(Either<L, R> either)`

## Arguments

* `leftMorphism (Function<L, C>)`: Maps the value of a `Left a` to `c`.
* `rightMorphism (Function<R, C>)`: Maps the value of a `Right b` to `c`.
* `either (Either<L, R>)`: The `Either`.

## Types

* `L`: The underlying left type.
* `R`: The underlying right type.
* `C`: The return type.

## Returns

* `(C)`: The result of the catamorphism of the `either`.

## Throws

* `NullPointerException` if the `leftMorphism`, `rightMorphism`, or `either` is `null`.

## Examples

```java
Either.eitherMap(
  Exception::getMessage,
  value -> String.format("The value is %s", value % 2 == 0 ? "even" : "odd"),
  Either.right(1)
);
// => "The value is odd"

Either.eitherMap(
  Exception::getMessage,
  value -> String.format("The value is %s", value % 2 == 0 ? "even" : "odd"),
  Either.left(new RuntimeException("The value is not a number"))
);
// => "The value is not a number"
```
