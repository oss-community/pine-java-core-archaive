package com.pineframework.core.helper;

import static com.pineframework.core.helper.I18nUtils.i18n;
import static com.pineframework.core.helper.validator.ArrayValidator.requireElement;
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static com.pineframework.core.helper.validator.StringValidator.requireNonEmptyOrNull;
import static java.util.stream.Collectors.joining;

import java.util.stream.Stream;

/**
 * The {@link StringUtils} class provides utility functions for {@link String} values.
 *
 * @author Saman Alishirishahrbabak
 */
public final class StringUtils {

  private StringUtils() {
  }

  /**
   * The {@code concatenate} method concatenates {@code strings} elements and returns an {@code String} and put the {@code separator}
   * between elements.
   *
   * @param separator {@link String}
   * @param strings   {@link String} vararg
   * @return {@link String}
   * @throws IllegalArgumentException if {@code strings} does not have an element
   */
  public static String concatenate(String separator, String... strings) {
    requireNonNull(separator, i18n("error.validation.should.not.be.null", i18n("var.name.separator")));
    requireElement(strings);

    return String.join(separator, strings);
  }

  /**
   * The {@code toTitleCase} returns an {@code String} with a title case style. This method is designed primarily for apply on a word.
   *
   * @param word {@link String}
   * @return {@link String}
   * @throws IllegalArgumentException if {@code word} is {@code null} or <i>empty</i>
   */
  private static String toTitleCase(String word) {
    requireNonEmptyOrNull(word, i18n("error.validation.should.not.be.null", i18n("var.name.word")));

    return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
  }

  /**
   * The {@code toTitleCase} returns an {@code String} with a title case style. This method is designed primarily for apply on sentences.
   * <p>The sentence should be split, then pass to {@code toTitleCase}.</p>
   *
   * @param words {@link String} vararg
   * @return {@link String}
   * @throws IllegalArgumentException if {@code words} does not have an element
   */
  public static String toTitleCase(String... words) {
    requireElement(words);

    return Stream.of(words)
        .map(StringUtils::toTitleCase)
        .collect(joining());
  }

  /**
   * The {@code toCamelCase} returns an {@code String} with a camel case style. This method is designed primarily for apply on sentences.
   * The sentence has to split, then pass to {@code toCamelCase}.
   *
   * @param words {@link String} vararg
   * @return {@link String}
   * @throws IllegalArgumentException if {@code words} does not have an element
   */
  public static String toCamelCase(String... words) {
    requireElement(words);

    return Stream.of(words)
        .map(String::toLowerCase)
        .reduce((first, rest) -> first.concat(toTitleCase(rest)))
        .orElse("");
  }
}
