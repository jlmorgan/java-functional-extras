# `Either#<A, B>toString()`

Converts the `instance` to a `String` representation.

## Types

* `A`: The underlying left type.
* `B`: The underlying right type.

## Returns

* `(String)`: The `instance` as a `String`.

## Examples

```java
Either.Left("a").toString();
// => "Left(a)"

Either.Right("a").toString();
// => "Right(a)"
```
