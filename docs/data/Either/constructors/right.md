# `Either.<L, R>right(R value)`

Encapsulates a right value.

## Arguments

* `value (R)`: A value.

## Types

* `L`: The underlying left type.
* `R`: The underlying right type.

## Returns

* `(Either<L, R>)`: An `Either` of the `value`.

## Example

```java
Either.<Integer, String>right("a");
// => Right("a")
```
