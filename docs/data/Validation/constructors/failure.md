# `Validation.<F, S>failure(F value)`

Encapsulates a failure value.

## Alternatives

* `Validation.<F, S>failure(Collection<F> values)`

## Arguments

* `value (F)`: The value.

### Argument Overloads

* `values (Collection<F>)`: The values.

## Types

* `F`: The underlying failure type.
* `S`: The underlying success type.

## Returns

* `(Validation<F, S>)`: An `Validation` of the `value`.

## Example

```java
Validation.<Integer, String>failure(0);
// => Failure([0])

Validation.<Integer, String>failure(Arrays.asList(0, 1, 2));
// => Failure([0, 1, 2])
```
