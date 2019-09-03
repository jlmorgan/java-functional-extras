# `last(list)`

Extracts the last element of a non-null, non-empty list.

## Arguments

* `list (List<A>)`: The list.

## Returns

* `(A)`: Last item in the list.

## Types

* `A`: The underlying type.

## Throws

* `IllegalArgumentException` if the `list` is not a non-empty `List<T>`.

## Example

```java
last(Collections.<Integer>emptyList());
// => IllegalArgumentException("list must be a non-empty List")

last(Collections.<Integer>singletonList(0));
// => 0

last(Arrays.<Integer>asList(1, 2, 3));
// => 3
```
