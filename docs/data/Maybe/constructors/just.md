# `Maybe.<V>just(V value)`

Creates a `Just` of the value.

## Arguments

* `value (V)`: A non-null value.

## Types

* `V`: The value type.

## Returns

* `(Maybe<V>)`: A `Maybe` of the `value`.

## Example

```java
Maybe.<String>just("a");
// => Just(a)
```
