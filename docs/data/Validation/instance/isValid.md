# `Validation#<A, B>isValid()`

Determines whether or not the `Validation` is a `Valid`.

## Types

* `A`: The underlying invalid type.
* `B`: The underlying valid type.

## Returns

* `(Boolean)`: `true` for a `Valid`; otherwise, `false`.

## Examples

```java
Validation.<String, String>invalid("a").isValid();
// => false

Validation.<String, String>valid("a").isValid();
// => true
```
