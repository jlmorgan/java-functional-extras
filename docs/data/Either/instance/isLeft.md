# `Either#<A, B>isLeft()`

Determines whether or not the `Either` is a `Left`.

## Types

* `L`: The underlying left type.
* `R`: The underlying right type.

## Returns

* `(boolean)`: `true` for a `Left`; otherwise, `false`.

## Examples

```java
Either.<String, String>Left("a").isLeft();
// => true

Either.<String, String>Right("a").isLeft();
// => false
```
