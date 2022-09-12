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

import static com.pineframework.core.helper.SystemUtils.getEnv;
import static com.pineframework.core.helper.YesNoQuestion.ask;
import static com.pineframework.core.helper.validator.StringValidator.requireNonEmptyOrNull;
import static java.lang.String.format;
import static java.util.ResourceBundle.getBundle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/**
 * The {@link I18nUtils} class provides utility functions for internationalization.
 * <ul>
 *   <li>{@link #i18n(String, Object...)}</li>
 *   <li>{@link #i18nByBundle(String, String, Object...)}</li>
 *   <li>{@link #i18ns(String, String, Object[][])}</li>
 *   <li>{@link #i18nsByBundle(String, String, String, Object[][])}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class I18nUtils {

  /**
   * The language of locale in two letters format.
   * <example>en</example>
   * <example>fa</example>
   */
  public static final String I18N_LANG = "I18N_LANG";

  /**
   * The files in order to handle multiple internationalization files.
   * <p>
   * The files should be write as a comma separate string.
   * </p>
   * <example>test_i18n1,test_i18n2,...</example>
   */
  public static final String I18N_FILES = "I18N_FILES";

  private static final Map<String, ResourceBundle> bundles = Collections.synchronizedMap(new HashMap<>());

  static {
    var lang = getEnv(I18N_LANG).orElse("en");
    bundles.put("i18n", getBundle("i18n", new Locale(lang)));

    getEnv(I18N_FILES).ifPresent(value -> {
      var files = value.split(",");
      for (String file : files) {
        bundles.put(file, getBundle(file, new Locale(lang)));
      }
    });
  }

  private I18nUtils() {

  }

  /**
   * The {@code i18n} method gets message from bundles.
   *
   * @param key    message key
   * @param params values for placeholder
   * @return message
   */
  public static String i18n(String key, Object... params) {
    requireNonEmptyOrNull(key, "key is empty or null");

    var message = i18nByBundle("i18n", key, params);

    if (message.isBlank()) {
      Optional<String> keyOptional = bundles.entrySet().stream()
          .filter(entry -> entry.getValue().containsKey(key))
          .map(Map.Entry::getKey)
          .findFirst();

      if (keyOptional.isPresent()) {
        message = i18nByBundle(keyOptional.get(), key, params);
      }
    }

    return message;
  }

  /**
   * The {@code i18n} method gets message from bundles.
   *
   * @param bundlePrefix file prefix
   * @param key          message key
   * @param params       values for placeholder
   * @return message
   */
  public static String i18nByBundle(String bundlePrefix, String key, Object... params) {
    requireNonEmptyOrNull(bundlePrefix, "bundle prefix is empty or null");
    requireNonEmptyOrNull(key, "key is empty or null");

    var bundle = bundles.get(bundlePrefix);
    return bundle.containsKey(key) ? format(bundle.getString(key), params) : key;
  }

  /**
   * The {@code i18ns} method gets message from bundles as an array.
   *
   * @param key      message key
   * @param splitter to distinguish the elements of array
   * @return {@link String}[], messages
   */
  public static String[] i18ns(String key, String splitter, Object[][] params) {
    requireNonEmptyOrNull(key, "key is empty or null");
    requireNonEmptyOrNull(splitter, "splitter is empty or null");

    String[] messages = i18nsByBundle("i18n", key, splitter, params);

    if (messages.length == 0) {
      Optional<String> keyOptional = bundles.entrySet().stream()
          .filter(entry -> entry.getValue().containsKey(key))
          .map(Map.Entry::getKey)
          .findFirst();

      if (keyOptional.isPresent()) {
        messages = i18nsByBundle(keyOptional.get(), key, splitter, params);
      }
    }

    return messages;
  }

  /**
   * The {@code i18ns} method gets message from bundles as an array.
   *
   * @param bundlePrefix file prefix
   * @param key          message key
   * @param splitter     to distinguish the elements of array
   * @return {@link String}[], messages
   */
  public static String[] i18nsByBundle(String bundlePrefix, String key, String splitter, Object[][] params) {
    requireNonEmptyOrNull(bundlePrefix, "bundle prefix is empty or null");
    requireNonEmptyOrNull(key, "key is empty or null");
    requireNonEmptyOrNull(splitter, "splitter is empty or null");

    var bundle = bundles.get(bundlePrefix);

    if (bundle.containsKey(key)) {
      var messages = bundle.getString(key).split(splitter);

      ask(params.length > 0 && params.length != messages.length)
          .yes(() -> {
                throw new IllegalArgumentException("parameters rows is not equals to messages number");
              }
          );

      return IntStream.range(0, messages.length)
          .boxed()
          .map(index -> format(messages[index], params.length > 0 ? params[index] : null))
          .toArray(String[]::new);

    } else {
      return new String[0];
    }
  }
}
