# `isEmpty(list)`

Determines whether or not the `list` is `null` or empty.

## Arguments

* `list (List<A>)`: The list.

## Types

* `A`: The underlying type.

## Returns

* `(boolean)`: `true` if the `list` is empty; otherwise, `false`.

## Example

```java
isEmpty(Collections.<Object>emptyList());
// => true

isEmpty(Collections.<Object>singletonList(null));
// => false

isEmpty(Collections.<Integer>singletonList(0));
// => false
```
