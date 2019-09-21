# `Maybe.<V>of(V value)`

Creates a `Maybe` of the `value` where:

* `null` &rarr; `Nothing`
* `a` &rarr; `Just(a)`

## Arguments

* `value (V)`: The value.

## Types

* `V`: The underlying type.

## Returns

* `(Maybe<V>)`: `Nothing` if the `value` is `null`; otherwise, `Just` of the `value`.

## Examples

```java
Maybe.<String>of(null);
// => Nothing()

Maybe.<String>of("a");
// => Just("a")
```
