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

import static com.pineframework.core.helper.I18nUtils.I18N_FILES;
import static com.pineframework.core.helper.I18nUtils.I18N_LANG;
import static com.pineframework.core.helper.TestEnvironmentConfig.TEST_I18N_LANG;
import static com.pineframework.core.helper.TestEnvironmentConfig.TEST_I18N_FILES;

import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;

/**
 * {@link AbstractUtilsTest} is abstract of unit tests.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public abstract class AbstractUtilsTest {

  protected final Logger logger = logger();

  public AbstractUtilsTest() {

  }

  @BeforeAll
  static void beforeAll() {
    System.setProperty(I18N_LANG, TEST_I18N_LANG);
    System.setProperty(I18N_FILES, TEST_I18N_FILES);

    System.out.printf("lang: %s, added i18n: %s\n", TEST_I18N_LANG, TEST_I18N_FILES);
  }

  private Logger logger() {
    return LogUtils.getLogger(this.getClass().getSimpleName());
  }

  protected void logErrorAsInfo(String str) {
    logger.info("error as info: {}", str);
  }

  protected void logInfo(String str) {
    logger.info("info: {}", str);
  }

  protected void logArray(byte... bytes) {
    var string = IntStream.range(0, bytes.length)
        .mapToObj(i -> bytes[i])
        .map(o -> "[" + o + "]")
        .collect(Collectors.joining(" "));

    logger.info("info: {}", string);
  }

  protected void logError(String str) {
    logger.error("error: {}", str);
  }

  protected boolean shouldRunSuccessfully(CheckedRunnable runnable) {
    return Try.run(runnable).isSuccess();
  }
}
