package com.github.jlmorgan.data;

import static com.github.jlmorgan.data.Functions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Functions")
class FunctionsTests {
  private static final boolean _testInputA = true;
  private static final Function<Boolean, Integer> _testAToB = value -> value ? 1 : 0;
  private static final Function<Integer, String> _testBToC = value -> value == 1 ? "one" : "zero";

  @Nested
  class DescribeCurriedCompose {
    @Test
    void shouldConvertTypeAToTypeC() {
      final String expectedResult = _testBToC.apply(_testAToB.apply(_testInputA));
      final String actualResult = Functions.<Boolean, Integer, String>compose(_testBToC)
        .apply(_testAToB)
        .apply(_testInputA);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeCompose {
    @Test
    void shouldConvertTypeAToTypeC() {
      final String expectedResult = _testBToC.apply(_testAToB.apply(_testInputA));
      final String actualResult = compose(_testBToC, _testAToB)
        .apply(_testInputA);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeConstant {
    @Test
    void shouldIgnoreInputValue() {
      final UUID testValue = UUID.randomUUID();
      final String testSecondValue = "test";
      final Function<String, UUID> testFunction = constant(testValue);
      // noinspection UnnecessaryLocalVariable
      final UUID expectedResult = testValue;
      final UUID actualResult = testFunction.apply(testSecondValue);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeFlip {
    @Test
    void shouldFlipArgumentsForBiFunction() {
      final int testA = 2;
      final int testB = 4;
      final BiFunction<Integer, Integer, Integer> testFunction = (a, b) -> a - b;
      final int expectedResult = testFunction.apply(testA, testB);
      final int actualResult = flip(testFunction).apply(testB, testA);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldFlipArgumentsForFunction() {
      final int testA = 2;
      final int testB = 4;
      final Function<Integer, Function<Integer, Integer>> testFunction = a -> b -> a - b;
      final int expectedResult = testFunction.apply(testA).apply(testB);
      final int actualResult = flip(testFunction).apply(testB).apply(testA);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeId {
    @Test
    void shouldReturnValue() {
      final UUID testValue = UUID.randomUUID();
      // noinspection UnnecessaryLocalVariable
      final UUID expectedResult = testValue;
      final UUID actualResult = id(testValue);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeCurriedPipe {
    @Test
    void shouldConvertTypeAToTypeC() {
      final String expectedResult = _testBToC.apply(_testAToB.apply(_testInputA));
      final String actualResult = Functions.<Boolean, Integer, String>pipe(_testAToB)
        .apply(_testBToC)
        .apply(_testInputA);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribePipe {
    @Test
    void shouldConvertTypeAToTypeC() {
      final String expectedResult = _testBToC.apply(_testAToB.apply(_testInputA));
      final String actualResult = pipe(_testAToB, _testBToC)
        .apply(_testInputA);

      assertEquals(expectedResult, actualResult);
    }
  }
}
