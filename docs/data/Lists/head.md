# `head(list)`

Extracts the first element of a non-null, non-empty list.

## Arguments

* `list (List<A>)`: The list.

## Types

* `A`: The underlying type.

## Returns

* `(A)`: First item in the list.

## Throws

* `IllegalArgumentException` if the `list` is not a non-empty `Array`.

## Example

```java
head([]);
// => IllegalArgumentException("list must be a not empty List")

head([0]);
// => 0

head([1, 2, 3]);
// => 1
```
