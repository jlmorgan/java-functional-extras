# `Tuple<A, B>#swap()`

Creates a new `Tuple` with swapped values.

## Types

* `A`: The `first` type parameter.
* `B`: The `second` type parameter.

## Returns

* `(Tuple<B, A>)`: The swapped `Tuple`.

## Examples

```java
Tuple<String, Integer> tuple = Tuple.of("a", 1);

tuple.swap();
// => Tuple<Integer, String>(1, "a")
```
