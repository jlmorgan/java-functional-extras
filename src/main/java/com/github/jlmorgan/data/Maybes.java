package com.github.jlmorgan.data;

import static com.github.jlmorgan.Objects.requireNonNull;

import java.util.function.Function;
import java.util.function.Predicate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Provides a collection of curried methods for {@link Maybe} instances.
 */
public final class Maybes {
  /**
   * Curried implementation of {@link Maybes#filter(Predicate, Maybe)}.
   */
  @NotNull
  @Contract(pure = true)
  public static <T>Function<Maybe<T>, Maybe<T>> filter(final Predicate<T> predicate) {
    return maybe -> filter(predicate, maybe);
  }

  /**
   * Tests the underlying value against the {@code predicate}, returning the {@code Just} of the value for {@code true};
   * otherwise, {@code Nothing}.
   * @param predicate The predicate with which to test the value.
   * @param maybe The maybe.
   * @return The {@code Just} of the value for {@code true}; otherwise, {@code Nothing}.
   * @throws IllegalArgumentException if the {@code maybe} is {@code null}.
   */
  public static <T> Maybe<T> filter(final Predicate<T> predicate, final Maybe<T> maybe) {
    return requireNonNull(maybe, "maybe must not be null").filter(predicate);
  }

  /**
   * Curried implementation of {@link Maybes#fmap(Function, Maybe)}.
   */
  @NotNull
  @Contract(pure = true)
  public static <T, U> Function<Maybe<T>, Maybe<U>> fmap(final Function<T, U> morphism) {
    return maybe -> fmap(morphism, maybe);
  }

  /**
   * Maps the underlying value of a {@link Maybe} in a {@code null}-safe way.
   * @param morphism The morphism.
   * @param <T> The underlying type of the {@link Maybe}.
   * @param <U> The return type of the {@code morphism}.
   * @return The mapped {@link Maybe}.
   * @throws IllegalArgumentException if the {@code maybe} is {@code null}.
   */
  public static <T, U> Maybe<U> fmap(final Function<T, U> morphism, final Maybe<T> maybe) {
    return requireNonNull(maybe, "maybe must not be null").fmap(morphism);
  }
}
