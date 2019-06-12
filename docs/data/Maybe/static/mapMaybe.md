# `Maybe.<V, R>mapMaybe(Function<V, Maybe<R>> morphism, List<V> list)`

A version of `map` which can throw out elements. If the result of the function is `Nothing`, no element is added to the result list; otherwise, the value is added.

## Alternatives

* `Maybe.<V, R>mapMaybe(Function<V, Maybe<R>> morphism).apply(List<V> list)`

## Arguments

* `morphism (Function<V, Maybe<R>>)`: The function that maps the value in the `list` to a `Maybe` of the result.
* `list (List<V>)`: The list of values.

## Types

* `R`: The underlying type of the result `list`.
* `V`: The underlying type of the `list`.

## Returns

* `(List<R>)`: A list of mapped `Just` values.

## Throws

* `NullPointerException` if the `morphism` is `null`.

## Examples

```java
Maybe.mapMaybe(
  value -> value % 2 == 0 ? Maybe.just(value) : Maybe.nothing(),
  Arrays.asList(0, 1, 2, 3)
);
// => [0, 2]

Maybe.mapMaybe(
  value -> value % 2 == 0 ? Maybe.just(value) : Maybe.nothing(),
  null
);
// => []

Maybe.mapMaybe(
  value -> value % 2 == 0 ? Maybe.just(value) : Maybe.nothing(),
  Collections.emptyList()
);
// => []
```
