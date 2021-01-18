package com.github.jlmorgan.data;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A collection of utility functions and morphisms.
 */
public final class Functions {
  /**
   * Curried implementation of {@link Functions#ap(BiFunction, Function)}.
   * @param g The second function of the sequence.
   * @param <A> The input type of the first function and the first argument of the second function.
   * @param <B> The output type of the first function and the input type of the second argument of the second function.
   * @param <C> The output type of the second function.
   * @return A function that takes the first function of the sequence, then input value, and returns the result.
   */
  public static <A, B, C> Function<Function<A, B>, Function<A, C>> ap(final BiFunction<A, B, C> g) {
    return f -> liftA2(g, f, Functions::id);
  }

  /**
   * Composes a sequence of two functions {@code g} after {@code f} where {@code f} maps the input value to the second
   * argument of {@code g}.
   * @param g The second function of the sequence.
   * @param f The first function of the sequence.
   * @param <A> The input type of the first function and the first argument of the second function.
   * @param <B> The output type of the first function and the input type of the second argument of the second function.
   * @param <C> The output type of the second function.
   * @return A function that takes the input value of the sequence and returns the result.
   */
  public static <A, B, C> Function<A, C> ap(final BiFunction<A, B, C> g, final Function<A, B> f) {
    return liftA2(g, f, Functions::id);
  }

  /**
   * Curried implementation of {@link Functions#bind(BiFunction, Function)}.
   * @param g The second function.
   * @param <A> Input type for the first function ({@code f}) and the second argument of the second function
   * ({@code g}).
   * @param <B> Output type of the first function ({@code f}) and the first argument of the second function ({@code g}).
   * @param <C> Output type of the second function ({@code g}).
   * @return A function that takes the first function ({@code f}) and returns the sequence composition.
   */
  public static <A, B, C> Function<Function<A, B>, Function<A, C>> bind(final BiFunction<B, A, C> g) {
    return f -> bind(g, f);
  }

  /**
   * Composes a sequence of two functions {@code g} after {@code f} where {@code f} maps the input value to the first
   * argument of {@code g}.
   * @param g The second function.
   * @param f The first function.
   * @param <A> Input type for the first function ({@code f}) and the second argument of the second function
   * ({@code g}).
   * @param <B> Output type of the first function ({@code f}) and the first argument of the second function ({@code g}).
   * @param <C> Output type of the second function ({@code g}).
   * @return A function that takes the value and applies it to the sequence.
   */
  public static <A, B, C> Function<A, C> bind(final BiFunction<B, A, C> g, final Function<A, B> f) {
    return value -> g.apply(f.apply(value), value);
  }

  /**
   * Provides a curried version of {@link Functions#compose(Function, Function)}.
   * @param g The second function.
   * @param <A> Input type to the first function ({@code f}) in the composition.
   * @param <B> Output type of the first function ({@code f}) and input type to the second ({@code g}).
   * @param <C> Output type of the second function ({@code g}).
   * @return A function that maps a value of type {@code A} to type {@code C}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B, C> Function<Function<A, B>, Function<A, C>> compose(final Function<? super B, ? extends C> g) {
    return f -> compose(g, f);
  }

  /**
   * Composes two functions {@code g} after {@code f}.
   * @param g The second function.
   * @param f The first function.
   * @param <A> Input type to the first function ({@code f}) in the composition.
   * @param <B> Output type of the first function ({@code f}) and input type to the second ({@code g}).
   * @param <C> Output type of the second function ({@code g}).
   * @return A function that maps a value of type {@code A} to type {@code C}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B, C> Function<A, C> compose(
    final Function<? super B, ? extends C> g,
    final Function<? super A, ? extends B> f
  ) {
    return x -> g.apply(f.apply(x));
  }

  /**
   * The {@code K} combinator. Creates a unary function that ignores the input value and returns the original value.
   * @param a The return value of the unary function.
   * @param <A> The constant type parameter.
   * @param <B> The ignored type parameter.
   * @return A unary function that takes a value of type {@code B} and returns the original value of type
   * {@code A}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B> Function<B, A> constant(final A a) {
    return b -> a;
  }

  /**
   * Flips the argument order of the specified curried {@code function}.
   * @param f The curried function to flip.
   * @param <A> Type of the first argument.
   * @param <B> Type of the second argument.
   * @param <C> Return type of the function ({@code f}).
   * @return The flipped {@code function}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B, C> Function<B, Function<A, C>> flip(
    final Function<? super A, ? extends Function<? super B, ? extends C>> f
  ) {
    return b -> a -> f.apply(a).apply(b);
  }

  /**
   * Flips the argument order of the specified {@code bi-function}.
   * @param f The bi-function to flip.
   * @param <A> Type of the first argument.
   * @param <B> Type of the second argument.
   * @param <C> Return type of the function ({@code f}).
   * @return The flipped {@code bi-function}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B, C> BiFunction<B, A, C> flip(
    final BiFunction<A, B, C> f
  ) {
    return (b, a) -> f.apply(a, b);
  }

  /**
   * The {@code I} combinator or identity morphism.
   * @param a The input value.
   * @param <A> Type of the argument.
   * @return The input value.
   */
  @Contract(value = "_ -> param1", pure = true)
  public static <A> A id(final A a) {
    return a;
  }

  /**
   * Curried implementation of {@link Functions#liftA2(BiFunction, Function, Function)}.
   * @param h The combining function of the sequence.
   * @param <A> The input type of the first and second functions.
   * @param <B> The output type of the first function and the first argument of the combining function.
   * @param <C> The output type of the second function and the second argument of the combining function.
   * @param <D> The output type of the combining function.
   * @return A function that takes the second function, then the first function, then the value, and returns the result
   * of the sequence computation.
   */
  public static <A, B, C, D> Function<Function<A, C>, Function<Function<A, B>, Function<A, D>>> liftA2(
    final BiFunction<B, C, D> h
  ) {
    return g -> liftA2(h, g);
  }

  /**
   * Partially curried implementation of {@link Functions#liftA2(BiFunction, Function, Function)}.
   * @param h The combining function of the sequence.
   * @param g The second function of the sequence.
   * @param <A> The input type of the first and second functions.
   * @param <B> The output type of the first function and the first argument of the combining function.
   * @param <C> The output type of the second function and the second argument of the combining function.
   * @param <D> The output type of the combining function.
   * @return A function that takes the first function, then the value, and returns the result of the sequence
   * computation.
   */
  public static <A, B, C, D> Function<Function<A, B>, Function<A, D>> liftA2(
    final BiFunction<B, C, D> h,
    final Function<A, C> g
  ) {
    return f -> liftA2(h, g, f);
  }

  /**
   * Composes a sequence of two functions {@code g} and {@code f} and combines the results ({@code h}) where {@code f}
   * maps the input value to the first argument of {@code h} and {@code g} maps the input value to the second argument
   * of {@code h}.
   * @param h The combining function of the sequence.
   * @param g The second function of the sequence.
   * @param f The first function of the sequence.
   * @param <A> The input type of the first and second functions.
   * @param <B> The output type of the first function and the first argument of the combining function.
   * @param <C> The output type of the second function and the second argument of the combining function.
   * @param <D> The output type of the combining function.
   * @return A function that takes the value and returns the result of the sequence computation.
   */
  public static <A, B, C, D> Function<A, D> liftA2(
    final BiFunction<B, C, D> h,
    final Function<A, C> g,
    final Function<A, B> f
  ) {
    return value -> h.apply(f.apply(value), g.apply(value));
  }

  /**
   * Provides the curried version of {@link Functions#pipe(Function, Function)}.
   * @param f The first function.
   * @param <A> Input type to the first function ({@code f}) in the composition.
   * @param <B> Output type of the first function ({@code f}) and input type to the second ({@code g}).
   * @param <C> Output type of the second function ({@code g}).
   * @return A function that maps a value of type {@code A} to type {@code C}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B, C> Function<Function<B, C>, Function<A, C>> pipe(final Function<? super A, ? extends B> f) {
    return g -> compose(g, f);
  }

  /**
   * Composes two functions {@code f} before {@code g}.
   * @param f The first function.
   * @param g The second function.
   * @param <A> Input type to the first function ({@code f}) in the composition.
   * @param <B> Output type of the first function ({@code f}) and input type to the second ({@code g}).
   * @param <C> Output type of the second function ({@code g}).
   * @return A function that maps a value of type {@code A} to type {@code C}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B, C> Function<A, C> pipe(
    final Function<? super A, ? extends B> f,
    final Function<? super B, ? extends C> g
  ) {
    return compose(g, f);
  }

  /**
   * See {@link Functions#constant(Object)}.
   */
  @NotNull
  @Contract(pure = true)
  public static <A, B> Function<B, A> pure(final A a) {
    return b -> a;
  }
}
