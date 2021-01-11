package com.github.jlmorgan.data;

import static com.github.jlmorgan.data.Lists.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Lists")
class ListsTests {
  private static final Supplier<Integer> _randomInt = () -> new Random().nextInt(10);

  @Nested
  class DescribeAppend {
    @Test
    void shouldReturnSecondListForNullFirst() {
      final List<UUID> testSecond = Collections.singletonList(UUID.randomUUID());
      final List<UUID> testFirst = null;
      // noinspection UnnecessaryLocalVariable
      final List<UUID> expectedResult = testSecond;
      // noinspection ConstantConditions
      final List<UUID> actualResult = append(testSecond).apply(testFirst);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnFirstListForNullSecond() {
      final List<UUID> testSecond = null;
      final List<UUID> testFirst = Collections.singletonList(UUID.randomUUID());
      // noinspection UnnecessaryLocalVariable
      final List<UUID> expectedResult = testFirst;
      // noinspection ConstantConditions
      final List<UUID> actualResult = append(testSecond).apply(testFirst);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnEmptyListForEmptyLists() {
      final List<UUID> testSecond = Collections.emptyList();
      final List<UUID> testFirst = Collections.emptyList();
      final List<UUID> expectedResult = Collections.emptyList();
      final List<UUID> actualResult = append(testSecond).apply(testFirst);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnAppendedLists() {
      final UUID testValue2 = UUID.randomUUID();
      final UUID testValue1 = UUID.randomUUID();
      final List<UUID> testSecond = Collections.singletonList(testValue2);
      final List<UUID> testFirst = Collections.singletonList(testValue1);
      final List<UUID> expectedResult = Arrays.asList(testValue1, testValue2);
      final List<UUID> actualResult = append(testSecond).apply(testFirst);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeEmpty {
    @Test
    void shouldReturnEmptyList() {
      final List<Object> expectedResult = Collections.emptyList();
      final List<Object> actualResult = Lists.empty();

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeFoldLeft {
    private final BiFunction<Integer, Integer, Integer> _testFold = Integer::sum;
    private final int _testInitialValue = 0;

    @Test
    void shouldThrowExceptionForNullFold() {
      final BiFunction<Integer, Integer, Integer> testFold = null;
      final List<Integer> testList = Arrays.asList(
        _randomInt.get(),
        _randomInt.get(),
        _randomInt.get()
      );

      // noinspection ConstantConditions
      assertThrows(
        IllegalArgumentException.class,
        () -> foldLeft(testFold, _testInitialValue, testList)
      );
    }

    @Test
    void shouldReturnInitialValueForNullList() {
      final List<Integer> testList = null;
      // noinspection UnnecessaryLocalVariable
      final int expectedResult = _testInitialValue;
      // noinspection ConstantConditions
      final int actualResult = foldLeft(_testFold).apply(_testInitialValue).apply(testList);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnInitialValueForEmptyList() {
      final List<Integer> testList = Collections.emptyList();
      // noinspection UnnecessaryLocalVariable
      final int expectedResult = _testInitialValue;
      final int actualResult = foldLeft(_testFold).apply(_testInitialValue).apply(testList);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnAccumulatedValueForList() {
      final int testValue1 = _randomInt.get();
      final int testValue2 = _randomInt.get();
      final int testValue3 = _randomInt.get();
      final List<Integer> testList = Arrays.asList(testValue1, testValue2, testValue3);
      final int expectedResult = _testInitialValue + testValue1 + testValue2 + testValue3;
      final int actualResult = foldLeft(_testFold).apply(_testInitialValue).apply(testList);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeFoldRight {
    private final BiFunction<Integer, Integer, Integer> _testFold = Integer::sum;
    private final int _testInitialValue = 0;

    @Test
    void shouldThrowExceptionForNullFold() {
      final BiFunction<Integer, Integer, Integer> testFold = null;
      final List<Integer> testList = Arrays.asList(
        _randomInt.get(),
        _randomInt.get(),
        _randomInt.get()
      );

      // noinspection ConstantConditions
      assertThrows(
        IllegalArgumentException.class,
        () -> foldRight(testFold, _testInitialValue, testList)
      );
    }

    @Test
    void shouldReturnInitialValueForNullList() {
      final List<Integer> testList = null;
      // noinspection UnnecessaryLocalVariable
      final int expectedResult = _testInitialValue;
      // noinspection ConstantConditions
      final int actualResult = foldRight(_testFold).apply(_testInitialValue).apply(testList);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnInitialValueForEmptyList() {
      final List<Integer> testList = Collections.emptyList();
      // noinspection UnnecessaryLocalVariable
      final int expectedResult = _testInitialValue;
      final int actualResult = foldRight(_testFold).apply(_testInitialValue).apply(testList);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnAccumulatedValueForList() {
      final int testValue1 = _randomInt.get();
      final int testValue2 = _randomInt.get();
      final int testValue3 = _randomInt.get();
      final List<Integer> testList = Arrays.asList(testValue1, testValue2, testValue3);
      final int expectedResult = _testInitialValue + testValue1 + testValue2 + testValue3;
      final int actualResult = foldRight(_testFold).apply(_testInitialValue).apply(testList);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeHead {
    @Test
    void shouldThrowExceptionForNullList() {
      final List<Object> testList = null;

      // noinspection ConstantConditions
      assertThrows(IllegalArgumentException.class, () -> head(testList));
    }

    @Test
    void shouldThrowExceptionForEmptyList() {
      final List<Object> testList = Collections.emptyList();

      assertThrows(IllegalArgumentException.class, () -> head(testList));
    }

    @Test
    void shouldReturnFirstElementForNonEmptyList() {
      final UUID testValue1 = UUID.randomUUID();
      final UUID testValue2 = UUID.randomUUID();
      final UUID testValue3 = UUID.randomUUID();
      final List<UUID> testList = Arrays.asList(
        testValue1,
        testValue2,
        testValue3
      );
      // noinspection UnnecessaryLocalVariable
      final UUID expectedResult = testValue1;
      final UUID actualResult = head(testList);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeInit {
    @Test
    void shouldThrowExceptionForNullList() {
      final List<Object> testList = null;

      // noinspection ConstantConditions
      assertThrows(IllegalArgumentException.class, () -> init(testList));
    }

    @Test
    void shouldThrowExceptionForEmptyList() {
      final List<Object> testList = Collections.emptyList();

      assertThrows(IllegalArgumentException.class, () -> init(testList));
    }

    @Test
    void shouldReturnAllElementsExcludingLastForNonEmptyList() {
      final UUID testValue1 = UUID.randomUUID();
      final UUID testValue2 = UUID.randomUUID();
      final UUID testValue3 = UUID.randomUUID();
      final List<UUID> testList = Arrays.asList(
        testValue1,
        testValue2,
        testValue3
      );
      final List<UUID> expectedResult = Arrays.asList(testValue1, testValue2);
      final List<UUID> actualResult = init(testList);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeIsEmpty {
    @Test
    void shouldReturnTrueForNullList() {
      final List<Object> testList = null;
      // noinspection ConstantConditions
      final boolean actualResult = isEmpty(testList);

      assertTrue(actualResult);
    }

    @Test
    void shouldReturnTrueForEmptyList() {
      final List<Object> testList = Collections.emptyList();
      final boolean actualResult = isEmpty(testList);

      assertTrue(actualResult);
    }

    @Test
    void shouldReturnFalseForBlankList() {
      final List<Object> testList = Arrays.asList(null, null);
      final boolean actualResult = isEmpty(testList);

      assertFalse(actualResult);
    }

    @Test
    void shouldReturnFalseForNonEmptyList() {
      final List<UUID> testList = Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
      final boolean actualResult = isEmpty(testList);

      assertFalse(actualResult);
    }
  }

  @Nested
  class DescribeIsNotEmpty {
    @Test
    void shouldReturnFalseForNullList() {
      final List<Object> testList = null;
      // noinspection ConstantConditions
      final boolean actualResult = isNotEmpty(testList);

      assertFalse(actualResult);
    }

    @Test
    void shouldReturnFalseForEmptyList() {
      final List<Object> testList = Collections.emptyList();
      final boolean actualResult = isNotEmpty(testList);

      assertFalse(actualResult);
    }

    @Test
    void shouldReturnTrueForBlankList() {
      final List<Object> testList = Arrays.asList(null, null);
      final boolean actualResult = isNotEmpty(testList);

      assertTrue(actualResult);
    }

    @Test
    void shouldReturnTrueForNonEmptyList() {
      final List<UUID> testList = Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
      final boolean actualResult = isNotEmpty(testList);

      assertTrue(actualResult);
    }
  }

  @Nested
  class DescribeLast {
    @Test
    void shouldThrowExceptionForNullList() {
      final List<Object> testList = null;

      // noinspection ConstantConditions
      assertThrows(IllegalArgumentException.class, () -> last(testList));
    }

    @Test
    void shouldThrowExceptionForEmptyList() {
      final List<Object> testList = Collections.emptyList();

      assertThrows(IllegalArgumentException.class, () -> last(testList));
    }

    @Test
    void shouldReturnLastElementForNonEmptyList() {
      final UUID testValue1 = UUID.randomUUID();
      final UUID testValue2 = UUID.randomUUID();
      final UUID testValue3 = UUID.randomUUID();
      final List<UUID> testList = Arrays.asList(
        testValue1,
        testValue2,
        testValue3
      );
      // noinspection UnnecessaryLocalVariable
      final UUID expectedResult = testValue3;
      final UUID actualResult = last(testList);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeLength {
    @Test
    void shouldReturn0ForNullList() {
      final List<Object> testList = null;
      final int expectedResult = 0;
      // noinspection ConstantConditions
      final int actualResult = length(testList);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturn0ForEmptyList() {
      final List<Object> testList = Collections.emptyList();
      final int expectedResult = 0;
      final int actualResult = length(testList);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnLengthOfList() {
      final int testLength = _randomInt.get();
      final List<Object> testList = new ArrayList<>(testLength);
      final int expectedResult = testList.size();
      final int actualResult = length(testList);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeMap {
    @Test
    void shouldThrowExceptionForNullMorphism() {
      final List<UUID> testList = Collections.emptyList();
      final Function<UUID, String> testMorphism = null;

      // noinspection ConstantConditions
      assertThrows(IllegalArgumentException.class, () -> map(testMorphism, testList));
    }

    @Test
    void shouldReturnEmptyListForNullList() {
      final List<UUID> testList = null;
      final Function<UUID, String> testMorphism = UUID::toString;
      final List<String> expectedResult = Collections.emptyList();
      // noinspection ConstantConditions
      final List<String> actualResult = map(testMorphism).apply(testList);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnMappedList() {
      final int testValue1 = _randomInt.get();
      final int testValue2 = _randomInt.get();
      final int testValue3 = _randomInt.get();
      final Function<Integer, Integer> testMorphism = value -> value + 1;
      final List<Integer> testList = Arrays.asList(testValue1, testValue2, testValue3);
      final List<Integer> expectedResult = Arrays.asList(testValue1 + 1, testValue2 + 1, testValue3 + 1);
      final List<Integer> actualResult = map(testMorphism).apply(testList);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeTail {
    @Test
    void shouldThrowExceptionForNullList() {
      final List<Object> testList = null;

      // noinspection ConstantConditions
      assertThrows(IllegalArgumentException.class, () -> tail(testList));
    }

    @Test
    void shouldThrowExceptionForEmptyList() {
      final List<Object> testList = Collections.emptyList();

      assertThrows(IllegalArgumentException.class, () -> tail(testList));
    }

    @Test
    void shouldReturnAllElementsExcludingFirstForNonEmptyList() {
      final UUID testValue1 = UUID.randomUUID();
      final UUID testValue2 = UUID.randomUUID();
      final UUID testValue3 = UUID.randomUUID();
      final List<UUID> testList = Arrays.asList(
        testValue1,
        testValue2,
        testValue3
      );
      final List<UUID> expectedResult = Arrays.asList(testValue2, testValue3);
      final List<UUID> actualResult = tail(testList);

      assertEquals(expectedResult, actualResult);
    }
  }

  @Nested
  class DescribeUncons {
    @Test
    void shouldNothingForNullList() {
      final List<UUID> testList = null;
      final Maybe<Tuple<UUID, List<UUID>>> expectedResult = Maybe.nothing();
      // noinspection ConstantConditions
      final Maybe<Tuple<UUID, List<UUID>>> actualResult = uncons(testList);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldNothingForEmptyList() {
      final List<UUID> testList = Collections.emptyList();
      final Maybe<Tuple<UUID, List<UUID>>> expectedResult = Maybe.nothing();
      final Maybe<Tuple<UUID, List<UUID>>> actualResult = uncons(testList);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnJustOfTupleOfHeadAndTailOfList() {
      final UUID testValue1 = UUID.randomUUID();
      final UUID testValue2 = UUID.randomUUID();
      final UUID testValue3 = UUID.randomUUID();
      final List<UUID> testList = Arrays.asList(testValue1, testValue2, testValue3);
      final Maybe<Tuple<UUID, List<UUID>>> expectedResult = Maybe.just(Tuple.of(
        testValue1,
        Arrays.asList(testValue2, testValue3)
      ));
      final Maybe<Tuple<UUID, List<UUID>>> actualResult = uncons(testList);

      assertEquals(expectedResult, actualResult);
    }
  }
}
