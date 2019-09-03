# `Tuple.<A, B, C>tupleMap(Function<A, B> firstMorphism, Function<A, C> secondMorphism, A value)`

Maps the `value` into the elements of the [`Tuple`][Tuple] after applying a morphism for each position.

## Alternatives

* `Tuple.<A, B, C>tupleMap(Function<A, B> firstMorphism).apply(Function<A, C> secondMorphism).apply(A value)`
* `Tuple.<A, B, C>tupleMap(Function<A, B> firstMorphism, Function<A, C> secondMorphism).apply(A value)`

## Arguments

* `firstMorphism (Function<A, B>)`: The function to map the `value` into the first element.
* `secondMorphism (Function<A, C>)`: The function to map the `value` into the second element.
* `value (A)`: The value.

## Types

* `A`: The value type.
* `B`: The underlying first type for the returned `Tuple`.
* `C`: The underlying second type for the returned `Tuple`.

## Returns

* `Tuple<B, C>`: The mapped tuple.

## Example

```java
Tuple.<Integer, Integer, Integer>tupleMap(increment, decrement, 10);
// => Tuple<Integer, Integer>(11, 9)
```

[Tuple]: ..
