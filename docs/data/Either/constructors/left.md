# `Either.<L, R>left(L value)`

Encapsulates a left value.

## Arguments

* `value (L)`: The value.

## Types

* `L`: The underlying left type.
* `R`: The underlying right type.

## Returns

* `(Either<L, R>)`: An `Either` of the `value`.

## Example

```java
Either.<Integer, String>left(0);
// => Left(0)
```
