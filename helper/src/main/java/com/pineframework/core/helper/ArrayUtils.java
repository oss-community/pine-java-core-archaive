/* **********************************************************************
 * Copyright (c) 2022 Saman Alishirishahrbabak.
 * All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ***********************************************************************/

package com.pineframework.core.helper;

import static com.pineframework.core.helper.I18nUtils.i18n;
import static com.pineframework.core.helper.validator.ArrayValidator.requireElement;
import static com.pineframework.core.helper.validator.NumberValidator.shouldBeGreaterThanZero;
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * The {@link ArrayUtils} class provides utility functions for the arrays.
 * <ul>
 *  <li>{@link #createArray(Class, int)}</li>
 *  <li>{@link #ofNullable(Object[])}</li>
 *  <li>{@link #random(Object[])}</li>
 *  <li>{@link #find(Object[], Predicate)}</li>
 *  <li>{@link #subtract(Object[], Object[], BiPredicate, Class)}</li>
 *  <li>{@link #intersection(Object[], Object[], BiPredicate, Class)}</li>
 *  <li>{@link #union(Object[], Object[])}</li>
 *  <li>{@link #concat(Object[], Object[])}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
@SuppressWarnings("unchecked")
public final class ArrayUtils {

  private static final Random RANDOM = new SecureRandom();

  private ArrayUtils() {
  }

  /**
   * The {@code createArray} method creates an empty array for any type ({@link E}).
   *
   * @param type type of array
   * @param size size of array
   * @param <E>  type of array
   * @return {@link E}[]
   * @throws IllegalArgumentException if {@code type} is {@code null} or {@code size} is less than zero
   */
  public static <E> E[] createArray(Class<?> type, int size) {
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));
    shouldBeGreaterThanZero(size, i18n("error.validation.should.be.greaterThan", i18n("var.name.size"), 0));

    //noinspection unchecked
    return (E[]) Array.newInstance(type, size);
  }

  /**
   * The {@code ofNullable} method checks if an array is {@code null} or not.
   * <p>
   * If the array is {@code null} then it returns an empty array otherwise,
   * returns the same array.
   * </p>
   * <p>
   * This method is designed primarily for applying null functionality pattern.
   * </p>
   *
   * @param array array
   * @param <T>   type of array
   * @return {@link T}[]
   */
  public static <T> T[] ofNullable(T[] array) {
    return isNull(array) ? createArray(Object.class, 0) : array;
  }

  /**
   * The {@code random} method gets an element of an array randomly.
   *
   * @param vararg vararg
   * @param <T>    type of vararg
   * @return {@link T}
   * @throws IllegalArgumentException if {@code vararg} is {@code null} or empty
   */
  @SafeVarargs
  public static <T> T random(T... vararg) {
    requireElement(vararg);

    return vararg[RANDOM.nextInt(vararg.length)];
  }

  /**
   * The {@code find} method finds an element in an array that is match with
   * the {@code comparator}.
   * <p>
   * It returns an {@link Optional} including the element and, it returns
   * an empty {@link Optional} if there is no element.
   * </p>
   *
   * @param array      array
   * @param comparator {@link Predicate}
   * @param <T>        type of array
   * @return {@link Optional}
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  public static <T> Optional<T> find(T[] array, Predicate<? super T> comparator) {
    requireNonNull(array, i18n("error.validation.should.not.be.null", i18n("var.name.array")));
    requireNonNull(comparator, i18n("error.validation.should.not.be.null", i18n("var.name.comparator")));

    return Arrays.stream(array)
        .filter(comparator)
        .findAny();
  }

  /**
   * The {@code subtract} method performs subtract operation between two arrays
   * based on a comparison function.
   * <p>
   * All objects are included in {@code array1} but are not included in {@code array2}.
   * </p>
   *
   * @param array1     1st array
   * @param array2     2nd array
   * @param comparator {@link BiPredicate}
   * @param <T>        type of 1st array
   * @param <E>        type of 2nd array
   * @param type       class object of {@link  T}
   * @return {@link T}[]
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  public static <T, E> T[] subtract(T[] array1, E[] array2, BiPredicate<T, E> comparator, Class<?> type) {
    requireNonNull(array1, i18n("error.validation.should.not.be.null", i18n("var.name.array")));
    requireNonNull(array2, i18n("error.validation.should.not.be.null", i18n("var.name.array")));
    requireNonNull(comparator, i18n("error.validation.should.not.be.null", i18n("var.name.comparator")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    return Arrays.stream(array1)
        .filter(a1 -> Arrays.stream(array2).noneMatch(a2 -> comparator.test(a1, a2)))
        .toArray(value -> createArray(type, value));
  }

  /**
   * The {@code intersection} method performs intersection operation
   * between two arrays based on comparison function then it returns
   * common elements.
   *
   * @param array1     1st array
   * @param array2     2nd array
   * @param comparator {@link BiPredicate}
   * @param <T>        type of 1st array
   * @param <E>        type of 2nd array
   * @return {@link T}[]
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  public static <T, E> T[] intersection(T[] array1, E[] array2, BiPredicate<T, E> comparator, Class<?> type) {
    requireNonNull(array1, i18n("error.validation.should.not.be.null", i18n("var.name.array")));
    requireNonNull(array2, i18n("error.validation.should.not.be.null", i18n("var.name.array")));
    requireNonNull(comparator, i18n("error.validation.should.not.be.null", i18n("var.name.comparator")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    return Arrays.stream(array1)
        .filter(e1 -> Arrays.stream(array2).anyMatch(e2 -> comparator.test(e1, e2)))
        .toArray(value -> createArray(type, value));
  }

  /**
   * The {@code union} method performs union operation on two arrays and,
   * it returns a new array including the elements of two arrays and
   * removes the repetitive elements.
   *
   * @param array  array
   * @param vararg vararg
   * @param <T>    type of array
   * @return {@link T}[]
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  @SafeVarargs
  public static <T> T[] union(T[] array, T... vararg) {
    requireNonNull(array, i18n("error.validation.should.not.be.null", i18n("var.name.array")));
    requireNonNull(vararg, i18n("error.validation.should.not.be.null", i18n("var.name.vararg")));

    HashSet<T> set = new HashSet<>();
    set.addAll(asList(array));
    set.addAll(asList(vararg));
    return set.toArray(createArray(array.getClass().getComponentType(), set.size()));
  }

  /**
   * The {@code concat} method joins an array and a vararg together.
   *
   * @param array  array
   * @param vararg vararg
   * @param <T>    type of the array and vararg
   * @return {@link T}[]
   */
  @SafeVarargs
  public static <T> T[] concat(T[] array, T... vararg) {
    if (isNull(array) && isNull(vararg)) {
      return null;
    }

    Class<?> type = (isNull(array) ? vararg : array).getClass().getComponentType();

    array = ofNullable(array);
    vararg = ofNullable(vararg);

    T[] newArray = createArray(type, array.length + vararg.length);
    System.arraycopy(array, 0, newArray, 0, array.length);
    System.arraycopy(vararg, 0, newArray, array.length, vararg.length);

    return newArray;
  }
}
