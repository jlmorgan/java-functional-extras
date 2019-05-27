# `of<A, B>(A first, B second) | of<A, B>(A first).apply(B second)`

Creates a `Tuple` with two values.

## Arguments

* `first (A)`: The first value.
* `second (B)`: The second value.

## Types

* `A`: The `first` type parameter.
* `B`: The `second` type parameter.

## Returns

* `(Tuple<A, B>)`: A `Tuple` of the two values.

## Example

```java
Tuple.of<String, int>("a").apply(1);
// => Tuple<String, int>("a", 1)

Tuple.of<String, int>("b", 2);
// => Tuple<String, int>("b", 2)
```
