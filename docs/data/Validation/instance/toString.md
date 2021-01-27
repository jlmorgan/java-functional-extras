# `Validation#<A, B>toString()`

Converts the `instance` to a `String` representation.

## Types

* `A`: The underlying invalid type.
* `B`: The underlying valid type.

## Returns

* `(String)`: The `instance` as a `String`.

## Examples

```java
Validation.<String, String>invalid("a").toString();
// => "Invalid(a)"

Validation.<String, String>valid("a").toString();
// => "Valid(a)"
```
