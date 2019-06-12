# `Tuple<A, B>#toString()`

Converts the `instance` to a `String` representation.

## Types

* `A`: The `first` type parameter.
* `B`: The `second` type parameter.

## Returns

* `(String)`: The `instance` as a `String`.

## Examples

```java
Tuple<String, Integer> tuple = Tuple.of("a", 1);

tuple.toString();
// => "Tuple(a, 1)"
```
