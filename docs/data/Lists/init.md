# `init(list)`

Extracts the elements of a non-null, non-empty list excluding the last element.

## Arguments

* `list (List<A>)`: The list.

## Types

* `A`: The underlying type.

## Returns

* `(List<A>)`: Elements in the list excluding the last.

## Throws

* `IllegalArgumentException` if the `list` is not a non-empty `List`.

## Example

```java
init(Collections.<Object>emptyList());
// => IllegalArgumentException("list must be a not empty List")

init(Collections.<Integer>singletonList(0));
// => [0]

init(Arrays.<Integer>asList(1, 2, 3 ));
// => [1, 2]
```
