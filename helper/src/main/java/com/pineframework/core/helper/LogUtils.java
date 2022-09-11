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
import static com.pineframework.core.helper.validator.StringValidator.requireNonEmptyOrNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LogUtils} class provides utility functions for logging.
 * <ul>
 *   <li>{@link #getLogger(String)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class LogUtils {

  private LogUtils() {
  }

  /**
   * The {@code getLogger} method returns a slf4j logger.
   *
   * @param name name of a class wants to use logger
   * @return {@link Logger}
   * @throws NullPointerException if {@code name} is {@code null}
   */
  public static Logger getLogger(String name) {
    requireNonEmptyOrNull(name, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.name")));

    return LoggerFactory.getLogger(name);
  }
}
