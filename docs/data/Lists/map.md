# `map(morphism, list)`

Maps the values of the `list` into a new `list`.

## Alternatives

* `map(morphism).apply(list)`

## Arguments

* `morphism (Function<A, B>)`: The morphism.
* `list (List<Integer>)`: The list.

## Types

* `A`: The underlying type.
* `B`: The mapped type.

## Returns

* `(List<Integer>)`: The mapped list.

## Example

```java
Function<Integer, Integer> increment = value => value + 1;

map(increment, Arrays.asList(1, 2, 3));
// => [2, 3, 4]
```
