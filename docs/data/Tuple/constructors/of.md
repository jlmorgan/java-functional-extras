# `Tuple.<A, B>of(A first, B second)`

Creates a `Tuple` with two values.

## Alternatives

* `Tuple.<A, B>of(A first).apply(B second)`

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
Tuple.<String, int>of("a").apply(1);
// => Tuple(a, 1)

Tuple.<String, int>of("b", 2);
// => Tuple(b, 2)
```
