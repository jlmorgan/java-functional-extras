# `isNotEmpty(list)`

Determines whether or not the `list` is not `null` or empty.

## Arguments

* `list (List<A>)`: The list.

## Types

* `A`: The underlying type.

## Returns

* `(boolean)`: `true` if the `list` is empty; otherwise, `false`.

## Example

```java
isNotEmpty(Collections.<Object>emptyList());
// => false

isNotEmpty(Collections.<Object>singletonList(null));
// => true

isNotEmpty(Collections.<Integer>singletonList(0));
// => true
```
