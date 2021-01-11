package com.github.jlmorgan.data;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Function;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A finite ordered sequence of elements.
 * @param <A> The {@code first} type parameter.
 * @param <B> The {@code second} type parameter.
 */
public interface Tuple<A, B> {
  /**
   * Curried implementation of {@link Tuple#of(Object, Object)}.
   */
  @NotNull
  @Contract(pure = true)
  static <A, B> Function<B, Tuple<A, B>> of(final A first) {
    return second -> of(first, second);
  }

  /**
   * Creates a {@link Tuple} with two values.
   * @param first The first element.
   * @param second The second element.
   * @param <A> The underlying first type.
   * @param <B> The underlying second type.
   * @return A {@link Tuple} of the two values.
   */
  @NotNull
  @Contract(value = "_, _ -> new", pure = true)
  static <A, B> Tuple<A, B> of(final A first, final B second) {
    return new Couple<>(first, second);
  }

  /**
   * Curried implementation of {@link Tuple#tupleMap(Function, Function, Object)}.
   */
  @NotNull
  @Contract(pure = true)
  static <A, B, C> Function<Function<A, C>, Function<A, Tuple<B, C>>> tupleMap(
    final Function<A, B> firstMorphism
  ) {
    return secondMorphism -> tupleMap(firstMorphism, secondMorphism);
  }

  /**
   * Partially curried implementation of {@link Tuple#tupleMap(Function, Function, Object)}.
   */
  @NotNull
  @Contract(pure = true)
  static <A, B, C> Function<A, Tuple<B, C>> tupleMap(
    final Function<A, B> firstMorphism,
    final Function<A, C> secondMorphism
  ) {
    return value -> tupleMap(firstMorphism, secondMorphism, value);
  }

  /**
   * Maps the {@code value} into the elements of the {@link Tuple} after applying a morphism for each position.
   * @param firstMorphism The function to map the {@code value} into the first element.
   * @param secondMorphism The function to map the {@code value} into the second element.
   * @param value The value.
   * @param <A> The value type.
   * @param <B> The underlying first type of the returned {@link Tuple}.
   * @param <C> The underlying second type of the returned {@link Tuple}.
   * @return The mapped tuple.
   */
  @NotNull
  static <A, B, C> Tuple<B, C> tupleMap(
    final Function<A, B> firstMorphism,
    final Function<A, C> secondMorphism,
    final A value
  ) {
    return of(
      requireNonNull(firstMorphism, "firstMorphism must not be null").apply(value),
      requireNonNull(secondMorphism, "secondMorphism must not be null").apply(value)
    );
  }

  /**
   * The first element.
   * @return The first element.
   */
  A first();

  /**
   * The second element.
   * @return The second element.
   */
  B second();

  /**
   * Creates a new {@code Tuple} with swapped values.
   * @return The swapped {@code Tuple}.
   */
  default Tuple<B, A> swap() {
    return of(second(), first());
  }

  class Couple<A, B> implements Tuple<A, B> {
    private final A _first;
    private final B _second;

    private Couple(final A first, final B second) {
      _first = first;
      _second = second;
    }

    /**
     * Determines whether or not the {@code other} has the same value as the current {@code instance}.
     * @param other The other object.
     * @return {@code true} for equality; otherwise, {@code false}.
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(final Object other) {
      boolean result = false;

      if (this == other) {
        result = true;
      } else if (other != null && getClass() == other.getClass()) {
        final Couple<?, ?> tuple = (Couple<?, ?>) other;

        result = Objects.equals(first(), tuple.first())
          && Objects.equals(second(), tuple.second());
      }

      return result;
    }

    /**
     * The first element.
     * @return The first element.
     */
    public A first() {
      return _first;
    }

    /**
     * Generates a hash code representing the underlying values of the {@code Tuple}.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(first(), second());
    }

    /**
     * The second element.
     * @return The second element.
     */
    public B second() {
      return _second;
    }

    /**
     * Converts the {@code instance} to a {@link String} representation.
     * @return The {@code instance} as a {@link String}.
     */
    @Override
    public String toString() {
      return String.format("Tuple(%s, %s)", first(), second());
    }
  }
}
