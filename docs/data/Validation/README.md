# `Validation`

The `Validation` type is a right-biased disjunction that represents two possibilities: either a `Invalid` of `a` or a `Valid` of `b`. By convention, the `Validation` is used to represent a value or invalid result of some function or process as a `Invalid` of the invalid message or a `Valid` of the value.

* [Constructors](./constructors)
    * [`.invalid`](./constructors/invalid.md)
    * [`.valid`](./constructors/valid.md)
* [Static Methods](./static)
    * [`.concat`](./static/concat.md)
    * [`.fromInvalid`](./static/fromInvalid.md)
    * [`.fromValid`](./static/fromValid.md)
    * [`.invalids`](./static/invalids.md)
    * [`.partitionValidations`](./static/partitionValidations.md)
    * [`.validate`](./static/validate.md)
    * [`.validationMap`](./static/validationMap.md)
    * [`.valids`](./static/valids.md)
* [Instance Methods](./instance)
    * [`#equals`](./instance/equals.md)
    * [`#isInvalid`](./instance/isInvalid.md)
    * [`#isValid`](./instance/isValid.md)
    * [`#toString`](./instance/toString.md)
