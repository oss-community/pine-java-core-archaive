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
import static com.pineframework.core.helper.StringUtils.concatenate;
import static com.pineframework.core.helper.StringUtils.toCamelCase;
import static com.pineframework.core.helper.StringUtils.toTitleCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link StringUtilsTest} class provides unit tests for {@link StringUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see StringUtils
 * @since 2022-01-01
 */
@DisplayName("String Utils Tests")
class StringUtilsTest extends AbstractUtilsTest {

  @Test
  @DisplayName("concatenating the words when the separator is null")
  @SuppressWarnings("ConstantConditions")
  void concatenates_IfSeparatorIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenString = new String[] {};
    String givenSeparator = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> concatenate(givenSeparator, givenString));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.separator")), message);
  }

  @Test
  @DisplayName("concatenating null words")
  @SuppressWarnings("ConstantConditions")
  void concatenates_IfWordsIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    String[] givenStrings = null;
    var givenSeparator = "dummy";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> concatenate(givenSeparator, givenStrings));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("concatenating blank words")
  void concatenates_IfWordsIsBlank_ShouldThrowIllegalArgumentException() {
    //Given
    var givenStrings = new String[] {};
    var givenSeparator = "dummy";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> concatenate(givenSeparator, givenStrings));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("concatenating the words when the separator is empty")
  void concatenates_IfSeparatorIsEmpty_ShouldReturnConcatenatedWordsAsChunk() {
    //Given
    var givenStrings = new String[] {"word1", "word2"};
    var givenSeparator = "";

    //Expectation
    var expectedString = "word1word2";

    //When
    var result = concatenate(givenSeparator, givenStrings);

    //Then
    assertEquals(expectedString, result);
  }

  @Test
  @DisplayName("changing the style of a word to the title case")
  void toTitleCase_IfWordIsValid_ShouldReturnWordWithTitleCaseStyle() {
    //Given
    var givenWords = "word";

    //Expectation
    var expectedWord = "Word";

    //When
    var result = toTitleCase(givenWords);

    //Then
    assertEquals(expectedWord, result);
  }

  @Test
  @DisplayName("changing the style of words to the title case")
  void toTitleCase_IfWordsIsValid_ShouldReturnWordWithTitleCaseStyle() {
    //Given
    var givenWords = new String[] {"title", "case"};

    //Expectation
    var expectedString = "TitleCase";

    //When
    var result = toTitleCase(givenWords);

    //Then
    assertEquals(expectedString, result);
  }

  @Test
  @DisplayName("changing the style of a word to the camel case")
  void toCamelCase_IfWordIsValid_ShouldReturnWordWithCamelcaseStyle() {
    //Given
    var givenWords = "word";

    //Expectation
    var expectedWord = "word";

    //When
    var result = toCamelCase(givenWords);

    //Then
    assertEquals(expectedWord, result);
  }

  @Test
  @DisplayName("changing the style of words to the camel case")
  void toCamelCase_IfWordsIsValid_ShouldReturnWordWithCamelcaseStyle() {
    //Given
    var givenWords = new String[] {"camel", "case"};

    //Expectation
    var expectedString = "camelCase";

    //When
    var result = toCamelCase(givenWords);

    //Then
    assertEquals(expectedString, result);
  }
}
