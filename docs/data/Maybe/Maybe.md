# `Maybe<A>`

## Types

* `A`: The underlying type.

## Constructors

* [`Maybe<V> just(V value)`][just]
* [`Maybe<V> nothing()`][nothing]

## Static Methods

* [`List<V> catMaybes(List<Maybe<V>> list)`][catMaybes]
* [`V fromJust(Maybe<V> maybe)`][fromJust]
* [`Function<Maybe<V>, V> fromMaybe(V defaultValue)`][fromMaybe]
* [`V fromMaybe(V defaultValue, Maybe<V> maybe)`][fromMaybe]
* [`Maybe<V> listToMaybe(List<V> list)`][listToMaybe]
* [`Function<List<V>, List<R>> mapMaybe(Function<? super V, ? extends Maybe<R>> morphism)`][mapMaybe]
* [`List<R> mapMaybe(Function<? super V, ? extends Maybe<R>> morphism, List<V> list)`][mapMaybe]
* [`Function<Function<V, R>, Function<Maybe<V>, R>> maybeMap(R defaultValue)`][maybeMap]
* [`Function<Maybe<V>, R> maybeMap(R defaultValue, Function<? super V, R> morphism)`][maybeMap]
* [`R maybeMap(R defaultValue, Function<? super V, R> morphism, Maybe<V> maybe)`][maybeMap]
* [`List<V> maybeToList(Maybe<V> maybe)`][maybeToList]

## Instance Methods

Instance methods that accept an argument have curried implementations provided by the `Maybes` class where the instance is always the last argument.

* `Maybe<A> filter(Predicate<A> predicate)`: Tests the underlying value against the `predicate`, returning the `Just` of the value for `true`; otherwise, `Nothing`.
* `boolean isJust()`: Determines whether or not the `Maybe` is a `Just`.
* `boolean isNothing()`: Determines whether or not the `Maybe` is a `Nothing`.

[catMaybes]: ./static/catMaybes.md
[fromJust]: ./static/fromJust.md
[fromMaybe]: ./static/fromMaybe.md
[just]: ./constructors/just.md
[listToMaybe]: ./static/listToMaybe.md
[mapMaybe]: ./static/mapMaybe.md
[maybeMap]: ./static/maybeMap.md
[maybeToList]: ./static/maybeToList.md
[nothing]: ./constructors/nothing.md
