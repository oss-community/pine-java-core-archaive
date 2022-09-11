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
import static java.lang.Boolean.FALSE;

import io.vavr.control.Try;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link ResultSetIterator} class converts a {@link ResultSet} to an {@link Iterator}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public record ResultSetIterator(ResultSet rs) implements Iterator<ResultSet> {

  @Override
  public boolean hasNext() {
    return Try.of(rs::next).getOrElse(FALSE);
  }

  @Override
  public ResultSet next() throws NoSuchElementException {
    if (rs == null) {
      throw new NoSuchElementException(i18n("error.validation.can.not.find", i18n("var.name.element")));
    }

    return rs;
  }
}
