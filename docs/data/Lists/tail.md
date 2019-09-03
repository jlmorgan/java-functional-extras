# `tail<A>(list)`

Extracts the elements of a non-null, non-empty list excluding the first element.

## Arguments

* `list (List<A>)`: The list.

## Returns

* `(List<A>)`: Elements in the list excluding the first.

## Throws

* `IllegalArgumentException` if the `list` is `null` or empty.

## Example

```java
tail(null);
// => IllegalArgumentException("list must be a non-empty List")

tail(Collections.singletonList(0));
// => []

tail(Arrays.asList(1, 2, 3));
// => [2, 3]
```
