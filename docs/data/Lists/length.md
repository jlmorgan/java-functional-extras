# `length(list)`

Gets the length of the `list`; otherwise, defaults to `0`.

## Arguments

* `list (List<A>)`: The list.

## Types

* `A`: The underlying type.

## Returns

* `(int)`: The length of the list.

## Example

```java
length(Collections.<Integer>emptyList());
// => 0

length(Collections.<Integer>singletonList(0));
// => 1

length(Arrays.<Integer>asList(1, 2, 3));
// => 3
```
