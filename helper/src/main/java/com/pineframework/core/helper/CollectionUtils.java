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
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static java.util.Collections.emptyList;
import static java.util.Collections.frequency;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;

/**
 * The {@link CollectionUtils} class provides utility functions for collection data structures.
 * <ul>
 *   <li>{@link #ofNullable(Collection)}</li>
 *   <li>{@link #findFrequency(Collection)}</li>
 *   <li>{@link #findRepetitiveElements(Collection)}</li>
 *   <li>{@link #subtract(Collection, Collection)}</li>
 *   <li>{@link #subtract(Collection, Collection, BiPredicate)}</li>
 *   <li>{@link #intersection(Collection, Collection)}</li>
 *   <li>{@link #intersection(Collection, Collection, BiPredicate)}</li>
 *   <li>{@link #union(Collection, Collection)}</li>
 *   <li>{@link #concat(Collection, Collection)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class CollectionUtils {

  private CollectionUtils() {
  }

  /**
   * The {@code checkNull} method checks the {@code collection} is null or not.
   *
   * @param collection {@link Locale}
   * @throws IllegalArgumentException if {@code collection} is  {@code null}
   */
  private static <T> void checkNull(Collection<T> collection) {
    requireNonNull(collection, i18n("error.validation.should.not.be.null", i18n("var.name.collection")));
  }

  /**
   * The {@code checkNull} method checks the {@code comparator} is null or not.
   *
   * @param comparator {@link Locale}
   * @throws IllegalArgumentException if {@code comparator} is  {@code null}
   */
  private static <T, E> void checkNull(BiPredicate<T, E> comparator) {
    requireNonNull(comparator, i18n("error.validation.should.not.be.null", i18n("var.name.comparator")));
  }

  /**
   * The {@code ofNullable} method checks if a collection is {@code null}
   * or not, if so then it returns an empty {@link List} otherwise it
   * returns the same collection.
   * <p>This method is designed primarily for applying null functionality pattern.</p>
   *
   * @param collection {@link Collection}{@literal <}{@link T}{@literal >}
   * @param <T>        type of collection
   * @return an empty {@link List} if {@code collection} is null,
   * otherwise return the same {@link Collection} object
   */
  public static <T> Collection<T> ofNullable(Collection<T> collection) {
    return isNull(collection) ? emptyList() : collection;
  }

  /**
   * The {@code findFrequency} method finds frequency of the elements
   * in a collection. It returns a {@link Map} include the elements
   * as a key and counting of them as a value.
   *
   * @param collection {@link Collection}{@literal <}{@link T}{@literal >}
   * @param <T>        type of collection
   * @return {@link Map} include the elements as a key and counting of them as a value
   * @throws IllegalArgumentException if {@code collection} is {@code null}
   */
  public static <T> Map<T, Long> findFrequency(Collection<T> collection) {
    checkNull(collection);

    return collection.stream()
        .collect(groupingBy(e -> e, counting()));
  }

  /**
   * The {@code findRepetitiveElements} method finds the elements
   * that be repeated in a collection then it returns {@link Set}
   * include the elements.
   *
   * @param collection {@link Collection}{@literal <}{@link T}{@literal >}
   * @param <T>        type of collection
   * @return {@link Set}
   * @throws IllegalArgumentException if {@code collection} is {@code null}
   */
  public static <T> Collection<T> findRepetitiveElements(Collection<T> collection) {
    checkNull(collection);

    return collection.stream()
        .filter(e -> frequency(collection, e) > 1)
        .collect(toSet());
  }

  /**
   * The {@code subtract} method performs subtracting operation between two
   * collections based on a comparison function.
   * <p>
   * All objects included in {@code c1} but not included in {@code c2}. It
   * means the result is subset of fist collection {@code c1}
   * </p>
   *
   * @param c1         {@link Collection}{@literal <}{@link T}{@literal >}
   * @param c2         {@link Collection}{@literal <}{@link E}{@literal >}
   * @param comparator {@link BiPredicate}
   * @param <T>        type of first collection
   * @param <E>        type of second collection
   * @return {@link Collection}{@literal <}{@link T}{@literal >}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T, E> Collection<T> subtract(Collection<T> c1, Collection<E> c2, BiPredicate<T, E> comparator) {
    checkNull(c1);
    checkNull(c2);
    checkNull(comparator);

    return c1.stream()
        .filter(e1 -> c2.stream().noneMatch(e2 -> comparator.test(e1, e2)))
        .collect(toList());
  }

  /**
   * The {@code subtract} method performs subtract operation between two
   * collections based on {@code equals} function of elements.
   * <p>
   * All objects included in {@code c1} but not included in {@code c2}. It
   * means the result is subset of fist collection {@code c1}
   * </p>
   *
   * @param c1  {@link Collection}{@literal <}{@link T}{@literal >}
   * @param c2  {@link Collection}{@literal <}{@link E}{@literal >}
   * @param <T> type of first collection
   * @param <E> type of second collection
   * @return {@link Collection}{@literal <}{@link T}{@literal >}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T, E> Collection<T> subtract(Collection<T> c1, Collection<E> c2) {
    return subtract(c1, c2, Objects::equals);
  }

  /**
   * The {@code intersection} method performs intersection operation between two
   * collections based on comparison function then it returns common elements.
   *
   * @param c1         {@link Collection}{@literal <}{@link T}{@literal >}
   * @param c2         {@link Collection}{@literal <}{@link E}{@literal >}
   * @param comparator {@link BiPredicate}
   * @param <T>        type of first collection
   * @param <E>        type of second collection
   * @return {@link Collection}{@literal <}{@link T}{@literal >}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T, E> Collection<T> intersection(Collection<T> c1, Collection<E> c2, BiPredicate<T, E> comparator) {
    checkNull(c1);
    checkNull(c2);
    checkNull(comparator);

    return c1.stream()
        .filter(e1 -> c2.stream().anyMatch(e2 -> comparator.test(e1, e2)))
        .collect(toCollection(ArrayList::new));
  }

  /**
   * The {@code intersection} method performs intersection operation between two
   * collections based on {@code equals} function of elements then it returns
   * common elements.
   *
   * @param c1  {@link Collection}{@literal <}{@link T}{@literal >}
   * @param c2  {@link Collection}{@literal <}{@link E}{@literal >}
   * @param <E> type of first collection
   * @param <T> type of second collection
   * @return {@link Collection}{@literal <}{@link T}{@literal >}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T, E> Collection<T> intersection(Collection<T> c1, Collection<E> c2) {
    return intersection(c1, c2, Objects::equals);
  }

  /**
   * The {@code union} method performs union operation on two collections
   * and, it returns a {@link Collection} including the elements of two the
   * collections.
   *
   * @param c1  {@link Collection}
   * @param c2  {@link Collection}
   * @param <T> type of elements
   * @return {@link Collection}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T> Collection<T> union(Collection<T> c1, Collection<T> c2) {
    checkNull(c1);
    checkNull(c2);

    Set<T> set = new HashSet<>();
    set.addAll(c1);
    set.addAll(c2);
    return set.stream().toList();
  }

  /**
   * The {@code concat} method joins two collection.
   *
   * @param c1  {@link Collection}
   * @param c2  {@link Collection}
   * @param <T> type of collection elements
   * @return {@link Collection}
   */
  public static <T> Collection<T> concat(Collection<T> c1, Collection<T> c2) {
    if (isNull(c1) && isNull(c2)) {
      return null;
    }

    c1 = ofNullable(c1);
    c2 = ofNullable(c2);

    List<T> list = new ArrayList<>();
    list.addAll(c1);
    list.addAll(c2);
    return list;
  }
}
