package com.github.jlmorgan.data;

import static java.util.Objects.requireNonNull;

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
   * @throws NullPointerException if the {@code maybe} is {@code null}.
   */
  public static <T> Maybe<T> filter(final Predicate<T> predicate, final Maybe<T> maybe) {
    return requireNonNull(maybe, "maybe must not be null").filter(predicate);
  }
}
