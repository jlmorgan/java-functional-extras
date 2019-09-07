package com.github.jlmorgan.data;

import static java.util.Objects.requireNonNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link Maybe} type is a disjunction that wraps an arbitrary value. The {@link Maybe} {@code a} either contains a
 * value of type {@code a} (read: {@code Just a}) or empty (read: {@code Nothing}). {@link Maybe} provides a way to deal
 * with error or exceptional behavior.
 * @param <A> The underlying type.
 */
public interface Maybe<A> {
  /**
   * Takes a list of {@link Maybe} and returns a list of the {@code Just} values.
   * @param list List of {@link Maybe}.
   * @param <V> The underlying type.
   * @return A list of the {@code Just} values.
   */
  @Contract("null -> new")
  static <V> List<V> catMaybes(final List<Maybe<V>> list) {
    return Optional.ofNullable(list)
      .orElseGet(Collections::emptyList)
      .stream()
      .filter(Maybe::isJust)
      .map(Maybe::fromJust)
      .collect(Collectors.toList());
  }

  /**
   * Extracts the value out of a {@code Just} and throws an error if its argument is a {@code Nothing}.
   * @param maybe The {@link Maybe}.
   * @param <V> The underlying type.
   * @return The underlying value.
   * @throws IllegalArgumentException if the {@code maybeMap} is {@code null} or {@code Nothing}.
   */
  static <V> V fromJust(final Maybe<V> maybe) {
    return Optional.ofNullable(maybe)
      .filter(Maybe::isJust)
      .map(just -> ((Just<V>) just)._value)
      .orElseThrow(() -> new IllegalArgumentException("maybe must not be null or Nothing"));
  }

  /**
   * Curried implementation of {@link Maybe#fromMaybe(Object, Maybe)}.
   */
  @NotNull
  @Contract(pure = true)
  static <V> Function<Maybe<V>, V> fromMaybe(final V defaultValue) {
    return maybe -> fromMaybe(defaultValue, maybe);
  }

  /**
   * Takes a {@code defaultValue} and a {@link Maybe} value. If the {@link Maybe} is {@code Nothing}, it returns the
   * {@code defaultValue}; otherwise, it returns the underlying value of the {@code Just}.
   * @param defaultValue The value to use if the {@code maybeMap} is {@code null} or {@code Nothing}.
   * @param maybe The {@link Maybe}.
   * @param <V> The underlying type.
   * @return The underlying value for a {@code Just}; otherwise, the {@code defaultValue}.
   */
  @Contract("!null, _ -> !null")
  static <V> V fromMaybe(final V defaultValue, final Maybe<V> maybe) {
    return Optional.ofNullable(maybe)
      .filter(Maybe::isJust)
      .map(Maybe::fromJust)
      .orElse(defaultValue);
  }

  /**
   * Creates a {@code Just} of the value.
   * @param value A non-null value.
   * @param <V> The value type.
   * @return The {@link Maybe} of the {@code value}.
   */
  @NotNull
  @Contract("_ -> new")
  static <V> Maybe<V> just(final V value) {
    requireNonNull(value, "value must not be null");

    return new Just<>(value);
  }

  /**
   * Returns {@code Nothing} for an empty {@code list} or {@code Just} of the first element in the {@code list}.
   * @param list The {@code list} of values.
   * @param <V> The underlying type.
   * @return A {@code Just} of the first non-null value; otherwise, {@code Nothing}.
   */
  static <V> Maybe<V> listToMaybe(final List<V> list) {
    return Optional.ofNullable(list)
      .orElseGet(Collections::emptyList)
      .stream()
      .filter(Objects::nonNull)
      .findFirst()
      .map(Maybe::just)
      .orElseGet(Maybe::nothing);
  }

  /**
   * Curried implementation of {@link Maybe#mapMaybe(Function, List)}.
   */
  @NotNull
  @Contract(pure = true)
  static <V, R> Function<List<V>, List<R>> mapMaybe(final Function<? super V, ? extends Maybe<R>> morphism) {
    return list -> mapMaybe(morphism, list);
  }

  /**
   * A version of {@code map} which can throw out elements. If the result of the function is {@code Nothing}, no element
   * is added to the result list; otherwise, the value is added.
   * @param morphism The function that maps the value in the {@code list} to a {@link Maybe} of the result.
   * @param list The {@code list} of values.
   * @param <V> The underlying type of the {@code list}.
   * @param <R> The underlying type of the result {@code list}.
   * @return A list of mapped {@code Just} values.
   * @throws NullPointerException if the {@code morphism} is {@code null}.
   */
  static <V, R> List<R> mapMaybe(final Function<? super V, ? extends Maybe<R>> morphism, final List<V> list) {
    requireNonNull(morphism, "morphism must not be null");

    return Optional.ofNullable(list)
      .orElseGet(Collections::emptyList)
      .stream()
      .map(morphism)
      .filter(Maybe::isJust)
      .map(Maybe::fromJust)
      .collect(Collectors.toList());
  }

  /**
   * Curried implementation of {@link Maybe#maybeMap(Object, Function, Maybe)}.
   */
  @NotNull
  @Contract(pure = true)
  static <V, R> Function<Function<V, R>, Function<Maybe<V>, R>> maybeMap(final R defaultValue) {
    return morphism -> maybeMap(defaultValue, morphism);
  }

  /**
   * Partially curried implementation of {@link Maybe#maybeMap(Object, Function, Maybe)}.
   */
  @NotNull
  @Contract(pure = true)
  static <V, R> Function<Maybe<V>, R> maybeMap(final R defaultValue, final Function<? super V, R> morphism) {
    return instance -> maybeMap(defaultValue, morphism, instance);
  }

  /**
   * If the {@link Maybe} value is a {@code Nothing}, it returns the {@code defaultValue}; otherwise, applies the
   * {@code morphism} to the underlying value in the {@code Just} and returns the result.
   * @param defaultValue The value to use if the {@code maybeMap} is {@code null} or {@code Nothing}.
   * @param morphism The function to map the underlying value of the {@code maybeMap} to the same type as the return
   * type.
   * @param maybe The {@link Maybe}.
   * @param <V> The underlying type of the {@code maybeMap}.
   * @param <R> The default value and return type.
   * @return The mapped underlying value for a {@code Just}; otherwise, the {@code defaultValue}.
   * @throws NullPointerException if the {@code morphism} is {@code null}.
   */
  @Contract("!null, _, _ -> !null")
  static <V, R> R maybeMap(final R defaultValue, final Function<? super V, R> morphism, final Maybe<V> maybe) {
    requireNonNull(morphism, "morphism must not be null");

    return Optional.ofNullable(maybe)
      .filter(Maybe::isJust)
      .map(Maybe::fromJust)
      .map(morphism)
      .orElse(defaultValue);
  }

  /**
   * Returns an empty list for {@code Nothing}; otherwise, a singleton list of the underlying value of the {@code Just}.
   * @param maybe The {@link Maybe}.
   * @param <V> The underlying type.
   * @return A singleton list of the underlying value within the {@code maybeMap} for a {@code Just}; otherwise, an
   * empty list for {@code Nothing}.
   */
  static <V> List<V> maybeToList(final Maybe<V> maybe) {
    return Optional.ofNullable(maybe)
      .filter(Maybe::isJust)
      .map(Maybe::fromJust)
      .map(Collections::singletonList)
      .orElseGet(Collections::emptyList);
  }

  /**
   * Creates a nothing to represent {@code null} or a missing value.
   * @param <V> The underlying type.
   * @return A {@code Nothing}.
   */
  @Contract(pure = true)
  @SuppressWarnings("unchecked")
  static <V> Maybe<V> nothing() {
    return (Maybe<V>) Nothing.INSTANCE;
  }

  /**
   * Tests the underlying value against the {@code predicate}, returning the {@code Just} of the value for {@code true};
   * otherwise, {@code Nothing}.
   * @param predicate The predicate with which to test the value.
   * @return The {@code Just} of the value for {@code true}; otherwise, {@code Nothing}.
   * @throws NullPointerException if the {@code predicate} is {@code null}.
   */
  Maybe<A> filter(Predicate<A> predicate);

  /**
   * Determines whether or not the {@link Maybe} is a {@code Just}.
   * @return {@code true} for a {@code Just}; otherwise, {@code false}.
   */
  boolean isJust();

  /**
   * Determines whether or not the {@link Maybe} is a {@code Nothing}.
   * @return {@code true} for a {@code Nothing}; otherwise, {@code false}.
   */
  default boolean isNothing() {
    return !isJust();
  }

  /**
   * Encapsulates a non-null value.
   * @param <A> The underlying type.
   */
  class Just<A> implements Maybe<A> {
    private final A _value;

    Just(final A value) {
      _value = value;
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
        final Just<?> just = (Just<?>) other;

        result = _value.equals(just._value);
      }

      return result;
    }

    /**
     * Tests the underlying value against the {@code predicate}, returning the {@code Just} of the value for
     * {@code true}; otherwise, {@code Nothing}.
     * @param predicate The predicate with which to test the value.
     * @return The {@code Just} of the value for {@code true}; otherwise, {@code Nothing}.
     * @throws NullPointerException if the {@code predicate} is {@code null}.
     */
    public Maybe<A> filter(final Predicate<A> predicate) {
      return requireNonNull(predicate, "predicate must not be null").test(_value)
        ? this
        : nothing();
    }

    /**
     * Generates a hash code representing the underlying values of the {@link Maybe}.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(_value);
    }

    /**
     * Determines whether or not the {@link Maybe} is a {@code Just}.
     * @return {@code true} for a {@code Just}; otherwise, {@code false}.
     */
    @Override
    public boolean isJust() {
      return true;
    }

    /**
     * Converts the {@code instance} to a {@link String} representation.
     * @return The {@code instance} as a {@link String}.
     */
    @Override
    public String toString() {
      return String.format("Just(%s)", _value);
    }
  }

  /**
   * Encapsulates the absence of a value.
   * @param <A>
   */
  class Nothing<A> implements Maybe<A> {
    private static final Nothing<?> INSTANCE = new Nothing<>();

    Nothing() {}

    /**
     * Determines whether or not the {@code other} is a {@code Nothing}.
     * @param other The other object.
     * @return {@code true} for equality; otherwise, {@code false}.
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(final Object other) {
      return this == other;
    }

    /**
     * Tests the underlying value against the {@code predicate}, returning the {@code Just} of the value for
     * {@code true}; otherwise, {@code Nothing}.
     * @param predicate The predicate with which to test the value.
     * @return The {@code Just} of the value for {@code true}; otherwise, {@code Nothing}.
     */
    public Maybe<A> filter(final Predicate<A> predicate) {
      return this;
    }

    /**
     * Generates a hash code representing the underlying values of the {@link Maybe}.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
      return 0;
    }

    /**
     * Determines whether or not the {@link Maybe} is a {@code Just}.
     * @return {@code true} for a {@code Just}; otherwise, {@code false}.
     */
    @Override
    public boolean isJust() {
      return false;
    }

    /**
     * Converts the {@code instance} to a {@link String} representation.
     * @return The {@code instance} as a {@link String}.
     */
    @Override
    public String toString() {
      return "Nothing()";
    }
  }
}
