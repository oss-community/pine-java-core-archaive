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
import static com.pineframework.core.helper.ReflectionUtils.toJavaBasicType;
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Arrays;

/**
 * The {@link PrimitiveArrayDeserializer} class convert JSON array to primitive array.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public class PrimitiveArrayDeserializer extends StdDeserializer<Object[]> {

  public PrimitiveArrayDeserializer() {
    super(Object[].class);
  }

  @Override
  public Object[] deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    requireNonNull(parser, i18n("error.validation.should.not.be.null", i18n("var.name.parser")));

    return Arrays.stream(parser.readValueAs(Object[].class))
        .map(o -> toJavaBasicType(o, o.getClass()))
        .toArray(Object[]::new);
  }
}
