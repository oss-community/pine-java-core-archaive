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

import static com.pineframework.core.helper.DateUtils.allZoneAndOffsetRelatedTo;
import static com.pineframework.core.helper.DateUtils.calculateOffset;
import static com.pineframework.core.helper.DateUtils.changeZone;
import static com.pineframework.core.helper.DateUtils.changeZoneRightNow;
import static com.pineframework.core.helper.DateUtils.convertCalendar;
import static com.pineframework.core.helper.DateUtils.createFormat;
import static com.pineframework.core.helper.DateUtils.getCurrentDateTime;
import static com.pineframework.core.helper.DateUtils.isLeap;
import static com.pineframework.core.helper.DateUtils.plus;
import static com.pineframework.core.helper.DateUtils.toDate;
import static com.pineframework.core.helper.DateUtils.toSecond;
import static com.pineframework.core.helper.I18nUtils.i18n;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * {@link DateUtilsTest} class provides unit tests for {@link DateUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see DateUtils
 * @since 2022-01-01
 */
@SuppressWarnings("ConstantConditions")
@DisplayName("Date Utils Tests")
class DateUtilsTest extends AbstractUtilsTest {

  @Test
  @DisplayName("creating date format if id is null")
  void createFormat_IfIdIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    String givenId = null;
    var givenLocale = new Locale("dummy");
    var givenPattern = "dummy";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> createFormat(givenId, givenLocale, givenPattern));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id"), 0), message);
  }

  @Test
  @DisplayName("creating date format if id is empty")
  void createFormat_IfIdIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenId = "";
    var givenLocale = new Locale("dummy");
    var givenPattern = "dummy";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> createFormat(givenId, givenLocale, givenPattern));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id"), 0), message);
  }

  @Test
  @DisplayName("creating date format if pattern is null")
  void createFormat_IfPatternIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    String givenPattern = null;
    var givenLocale = new Locale("dummy");
    var givenId = "dummy";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> createFormat(givenId, givenLocale, givenPattern));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.pattern"), 0), message);
  }

  @Test
  @DisplayName("creating date format if pattern is empty")
  void createFormat_IfPatternIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenPattern = "";
    var givenId = "dummy";
    var givenLocale = new Locale("dummy");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> createFormat(givenId, givenLocale, givenPattern));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.pattern"), 0), message);
  }

  @Test
  @DisplayName("creating date format if locale is null")
  void createFormat_IfLocaleIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Locale givenLocale = null;
    var givenId = "dummy";
    var givenPattern = "dummy";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> createFormat(givenId, givenLocale, givenPattern));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.locale")), message);
  }

  @Test
  @DisplayName("creating date format if parameters are valid")
  void createFormat_IfParametersAreValid_ShouldReturnDateTime() {
    //Given
    var givenId = "gregorian";
    var givenLocale = new Locale("en", "US");
    var givenPattern = "yyyy-MM-dd'T'HH:mm:ss";

    //Expectation
    var expectedDateTimeFormat = "2022-01-01T12:00:00";

    //When
    var format = createFormat(givenId, givenLocale, givenPattern);
    assertNotNull(format);

    var result = format.format(Date.from(LocalDateTime.of(2022, 1, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant()));

    //Then
    assertNotNull(result);
    assertEquals(expectedDateTimeFormat, result);
  }

  @Test
  @DisplayName("getting all zones and related offsets if zone is null")
  void allZoneAndOffsetRelatedTo_IfZoneIdIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    ZoneId givenZone = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> allZoneAndOffsetRelatedTo(givenZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.zone")), message);
  }

  @Test
  @DisplayName("getting all zones and related offsets if zone is valid")
  void allZoneAndOffsetRelatedTo_IfZoneIdIsValid_ShouldReturnMapIncludeZoneAndOffset() {
    //Given
    var givenZone = ZoneId.of("UTC");

    //When
    var result = allZoneAndOffsetRelatedTo(givenZone);

    //Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  @DisplayName("calculating offset between two zones if 1st zone is null")
  void calculateOffset_If1stZoneIdIsNull_ShouldReturnIllegalArgumentException() {
    //Given
    ZoneId givenZone1 = null;
    var givenZone2 = ZoneId.of("UTC");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> calculateOffset(givenZone1, givenZone2));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.zone")), message);
  }

  @Test
  @DisplayName("calculating offset between two zones if 2nd zone is null")
  void calculateOffset_If2ndZoneIdIsNull_ShouldReturnIllegalArgumentException() {
    //Given
    var givenZone1 = ZoneId.of("UTC");
    ZoneId givenZone2 = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> calculateOffset(givenZone1, givenZone2));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.zone")), message);
  }

  @ParameterizedTest(name = "[{index}]: between {0} and {1}")
  @ArgumentsSource(ZoneIdList.class)
  @DisplayName("calculate offset between two zones")
  void calculateOffset_IfZoneIdsAreValid_ShouldReturnOffset(ZoneId givenZone1, ZoneId givenZone2, String expectedOffset) {

    var lowOffsetDiff = toSecond(expectedOffset) - 3600;
    var lowTime = ofPattern("HH:mm").format(LocalTime.ofSecondOfDay(Math.abs(lowOffsetDiff)));
    var lowExpectedOffset = String.format("%s%s", lowOffsetDiff >= 0 ? "+" : "-", lowTime);

    var highOffsetDiff = toSecond(expectedOffset) + 3600;
    var highTime = ofPattern("HH:mm").format(LocalTime.ofSecondOfDay(Math.abs(highOffsetDiff)));
    var highExpectedOffset = String.format("%s%s", highOffsetDiff >= 0 ? "+" : "-", highTime);

    //When
    var result = calculateOffset(givenZone1, givenZone2);

    //Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    Assertions.assertThat(result).isIn(expectedOffset, lowExpectedOffset, highExpectedOffset);
  }

  @Test
  @DisplayName("converting a time (HH:mm:ss) to second if time is null")
  void toSecond_IfTimeIsNull_ShouldReturnIllegalArgumentException() {
    //Given
    String givenTime = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toSecond(givenTime));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.time")), message);
  }

  @Test
  @DisplayName("converting a time (HH:mm:ss) to second if time is empty")
  void toSecond_IfTimeIsEmpty_ShouldReturnIllegalArgumentException() {
    //Given
    var givenTime = "";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toSecond(givenTime));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.time")), message);
  }

  @Test
  @DisplayName("converting a time (HH:mm:ss) to second if time has invalid format")
  void toSecond_IfTimeHasInvalidFormat_ShouldReturnIllegalArgumentException() {
    //Given
    var givenTime = "01:01:";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toSecond(givenTime));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.is.not.valid", i18n("var.name.format"), i18n("var.name.time")), message);
  }

  @Test
  @DisplayName("converting a time (HH:mm:ss) to second if time has valid format")
  void toSecond_IfTimeHasValidFormat_ShouldReturnSecond() {
    //Given
    var givenTime = "01:01:01";

    //Expectation
    var expectedSecond = 3661;

    //When
    var result = toSecond(givenTime);

    //Then
    assertEquals(expectedSecond, result);
  }

  @Test
  @DisplayName("converting a time (HH:mm:[ss]) to second if time is a positive valid offset")
  void toSecond_IfTimeIsPositiveValidOffset_ShouldReturnSecond() {
    //Given
    var givenTime = "+01:01";

    //Expectation
    var expectedSecond = 3660;

    //When
    var result = toSecond(givenTime);

    //Then
    assertEquals(expectedSecond, result);
  }

  @Test
  @DisplayName("converting a time (HH:mm:[ss]) to second if time is a negative valid offset")
  void toSecond_IfTimeIsNegativeValidOffset_ShouldReturnSecond() {
    //Given
    var givenTime = "-01:01";

    //Expectation
    var expectedSecond = -3660;

    //When
    var result = toSecond(givenTime);

    //Then
    assertEquals(expectedSecond, result);
  }

  @Test
  @DisplayName("converting a LocalDateTime to the Date if datetime is null")
  void toDate_datetime_IfDatetimeIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    LocalDateTime givenDateTime = null;
    var givenId = "dummy";
    var givenLocale = new Locale("dummy");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toDate(givenDateTime, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.dataAndTime")), message);
  }

  @Test
  @DisplayName("converting a LocalDateTime to the Date if locale is null")
  void toDate_datetime_IfLocaleIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenDateTime = LocalDateTime.now();
    var givenId = "dummy";
    Locale givenLocale = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toDate(givenDateTime, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.locale")), message);
  }

  @Test
  @DisplayName("converting a LocalDateTime to the Date if ID is null")
  void toDate_datetime_IfIdIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    LocalDateTime givenDateTime = LocalDateTime.now();
    String givenId = null;
    var givenLocale = new Locale("dummy");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toDate(givenDateTime, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")), message);
  }

  @Test
  @DisplayName("converting a LocalDateTime to the Date if ID is empty")
  void toDate_datetime_IfIdIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    LocalDateTime givenDateTime = LocalDateTime.now();
    var givenId = "";
    var givenLocale = new Locale("dummy");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toDate(givenDateTime, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")), message);
  }

  @Test
  @DisplayName("converting a LocalDateTime to the Date if parameters are valid")
  void toDate_datetime_IfParametersAreValid_ShouldReturnDate() {
    //Given, Expectation
    var givenDateTime = LocalDateTime.now();
    var givenId = "gregorian";
    var givenLocale = new Locale("en", "US");

    //When
    var result = toDate(givenDateTime, givenId, givenLocale);

    //Then
    assertNotNull(result);

    var calendar = Calendar.getInstance();
    calendar.setTime(result);

    assertEquals(givenDateTime.getYear(), calendar.get(Calendar.YEAR));
    assertEquals(givenDateTime.getMonthValue(), calendar.get(Calendar.MONTH) + 1);
    assertEquals(givenDateTime.getDayOfMonth(), calendar.get(Calendar.DAY_OF_MONTH));
    assertEquals(givenDateTime.getHour(), calendar.get(Calendar.HOUR_OF_DAY));
    assertEquals(givenDateTime.getMinute(), calendar.get(Calendar.MINUTE));
    assertEquals(givenDateTime.getSecond(), calendar.get(Calendar.SECOND));
  }

  @Test
  @DisplayName("converting a LocalDate to the Date if date is null")
  void toDate_date_IfDateIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    LocalDate givenDate = null;
    var givenId = "dummy";
    var givenLocale = new Locale("dummy");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toDate(givenDate, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.date")), message);
  }

  @Test
  @DisplayName("converting a LocalDate to the Date if locale is null")
  void toDate_date_IfLocaleIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Locale givenLocale = null;
    var givenDate = LocalDate.now();
    var givenId = "dummy";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toDate(givenDate, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.locale")), message);
  }

  @Test
  @DisplayName("converting a LocalDate to the Date if ID is null")
  void toDate_date_IfIdIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    String givenId = null;
    LocalDate givenDate = LocalDate.now();
    var givenLocale = new Locale("dummy");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toDate(givenDate, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")), message);
  }

  @Test
  @DisplayName("converting a LocalDate to the Date if ID is empty")
  void toDate_date_IfIdIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenId = "";
    LocalDate givenDate = LocalDate.now();
    var givenLocale = new Locale("dummy");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> toDate(givenDate, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")), message);
  }

  @Test
  @DisplayName("converting a LocalDate to the Date if parameters are valid")
  void toDate_date_IfParametersAreValid_ShouldReturnDate() {
    //Given, Expectation
    var givenDate = LocalDate.now();
    var givenId = "gregorian";
    var givenLocale = new Locale("en", "US");

    //When
    var result = toDate(givenDate, givenId, givenLocale);

    //Then
    assertNotNull(result);

    var calendar = Calendar.getInstance();
    calendar.setTime(result);

    assertEquals(givenDate.getYear(), calendar.get(Calendar.YEAR));
    assertEquals(givenDate.getMonthValue(), calendar.get(Calendar.MONTH) + 1);
    assertEquals(givenDate.getDayOfMonth(), calendar.get(Calendar.DAY_OF_MONTH));
  }

  @Test
  @DisplayName("changing the zone of a LocalDateTime if datetime is null")
  void changeZone_datetime_IfDateTimeIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    LocalDateTime givenDate = null;
    var givenCurrentZone = ZoneId.of("UTC");
    var givenNextZone = ZoneId.of("UTC");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> changeZone(givenDate, givenCurrentZone, givenNextZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.dataAndTime")), message);
  }

  @Test
  @DisplayName("changing the zone of a LocalDateTime if current zone is null")
  void changeZone_datetime_IfCurrentZoneIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    ZoneId givenCurrentZone = null;
    var givenDate = LocalDateTime.now();
    var givenNextZone = ZoneId.of("UTC");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> changeZone(givenDate, givenCurrentZone, givenNextZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.zone")), message);
  }

  @Test
  @DisplayName("changing the zone of a LocalDateTime if next zone is null")
  void changeZone_datetime_IfNextZoneIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenDate = LocalDateTime.now();
    var givenCurrentZone = ZoneId.of("UTC");
    ZoneId givenNextZone = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> changeZone(givenDate, givenCurrentZone, givenNextZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.zone")), message);
  }

  @ParameterizedTest(name = "[{index}]: from {0} to {1}")
  @ArgumentsSource(GregorianCurrentZonedDateTimeList.class)
  @DisplayName("changing time zone of datetime if parameters are valid")
  void changeZone_datetime_IfParametersAreValid_ShouldReturnDate(ZonedDateTime givenDateTime, ZonedDateTime expectedDateTime) {
    //When
    var result = changeZone(givenDateTime.toLocalDateTime(), givenDateTime.getZone(), expectedDateTime.getZone());

    //Then
    assertNotNull(result);

    assertEquals(expectedDateTime.getYear(), result.getYear());
    assertEquals(expectedDateTime.getMonthValue(), result.getMonthValue());
    assertEquals(expectedDateTime.getDayOfMonth(), result.getDayOfMonth());
    assertEquals(expectedDateTime.getHour(), result.getHour());
    assertEquals(expectedDateTime.getMinute(), result.getMinute());
    assertEquals(expectedDateTime.getSecond(), result.getSecond());
  }

  @Test
  @DisplayName("changing the zone of a LocalDate if date is null")
  void changeZone_date_IfDateIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    LocalDate givenDate = null;
    var givenCurrentZone = ZoneId.of("UTC");
    var givenNextZone = ZoneId.of("UTC");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> changeZone(givenDate, givenCurrentZone, givenNextZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.date")), message);
  }

  @Test
  @DisplayName("changing the zone of a LocalDate if current zone is null")
  void changeZone_date_IfCurrentZoneIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    ZoneId givenCurrentZone = null;
    var givenDate = LocalDate.now();
    var givenNextZone = ZoneId.of("UTC");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> changeZone(givenDate, givenCurrentZone, givenNextZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.zone")), message);
  }

  @Test
  @DisplayName("changing the zone of a LocalDate if next zone is null")
  void changeZone_date_IfNextZoneIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenDate = LocalDate.now();
    var givenCurrentZone = ZoneId.of("UTC");
    ZoneId givenNextZone = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> changeZone(givenDate, givenCurrentZone, givenNextZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.zone")), message);
  }

  @ParameterizedTest(name = "[{index}]: from [({0})::{1}] to [({2})::{3}]")
  @ArgumentsSource(GregorianLocalDateList.class)
  @DisplayName("changing zone of LocalDate if parameters are valid")
  void changeZone_date_IfParametersAreValid_ShouldReturnDate(ZoneId currentZone, LocalDate givenDate, ZoneId nextZone,
                                                             LocalDate expectedDate) {
    //When
    var result = changeZone(givenDate, currentZone, nextZone);

    //Then
    assertNotNull(result);

    assertEquals(expectedDate.getYear(), result.getYear());
    assertEquals(expectedDate.getMonthValue(), result.getMonthValue());
    assertEquals(expectedDate.getDayOfMonth(), result.getDayOfMonth());
  }

  @Test
  @DisplayName("changing the zone of a LocalDate at right now if date is null")
  void changeZoneRightNow_IfDateIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    LocalDate givenDate = null;
    var givenCurrentZone = ZoneId.of("UTC");
    var givenNextZone = ZoneId.of("UTC");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> changeZoneRightNow(givenDate, givenCurrentZone, givenNextZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.date")), message);
  }

  @Test
  @DisplayName("changing the zone of a LocalDate at right now if current zone is null")
  void changeZoneRightNow_IfCurrentZoneIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    ZoneId givenCurrentZone = null;
    var givenDate = LocalDate.now();
    var givenNextZone = ZoneId.of("UTC");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> changeZoneRightNow(givenDate, givenCurrentZone, givenNextZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.zone")), message);
  }

  @Test
  @DisplayName("changing the zone of a LocalDate at right now if next zone is null")
  void changeZoneRightNow_IfNextZoneIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenDate = LocalDate.now();
    var givenCurrentZone = ZoneId.of("UTC");
    ZoneId givenNextZone = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> changeZoneRightNow(givenDate, givenCurrentZone, givenNextZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.zone")), message);
  }

  @ParameterizedTest(name = "[{index}]: from {0} to {1}")
  @ArgumentsSource(GregorianCurrentZonedDateTimeList.class)
  @DisplayName("changing zone of datetime if parameters are valid")
  void changeZoneRightNow_date_IfParametersAreValid_ShouldReturnDate(ZonedDateTime givenDateTime, ZonedDateTime expectedDateTime) {
    //When
    var result = changeZoneRightNow(givenDateTime.toLocalDate(), givenDateTime.getZone(), expectedDateTime.getZone());

    //Then
    assertNotNull(result);

    assertEquals(expectedDateTime.getYear(), result.getYear());
    assertEquals(expectedDateTime.getMonthValue(), result.getMonthValue());
    assertEquals(expectedDateTime.getDayOfMonth(), result.getDayOfMonth());
  }

  @Test
  @DisplayName("adding year(s), month(s) and Day(s) to a date if date is null")
  void plus_IfDateIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    LocalDate givenDate = null;
    var givenId = "dummy";
    var givenLocale = new Locale("dummy");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> plus(givenDate, 0, 0, 0, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.date")), message);
  }

  @Test
  @DisplayName("adding year(s), month(s) and Day(s) to a date if ID is null")
  void plus_IfIdIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenDate = LocalDate.now();
    String givenId = null;
    var givenLocale = new Locale("dummy");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> plus(givenDate, 0, 0, 0, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")), message);
  }

  @Test
  @DisplayName("adding year(s), month(s) and Day(s) to a date if ID is empty")
  void plus_IfIdIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    LocalDate givenDate = LocalDate.now();
    String givenId = "";
    var givenLocale = new Locale("dummy");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> plus(givenDate, 0, 0, 0, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")), message);
  }

  @Test
  @DisplayName("adding year(s), month(s) and Day(s) to a date if locale is null")
  void plus_IfLocaleIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    LocalDate givenDate = LocalDate.now();
    var givenId = "dummy";
    Locale givenLocale = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> plus(givenDate, 0, 0, 0, givenId, givenLocale));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.locale")), message);
  }

  @Test
  @DisplayName("adding year(s), month(s) and Day(s) to a date")
  void plus_IfParametersAreValid_ShouldReturnLocalDate() {
    //Given
    var givenDate = LocalDate.of(2021, 9, 4);
    var givenYear = 2;
    var givenMonth = 3;
    var givenDay = 5;
    var givenId = "gregorian";
    var givenLocale = new Locale("en", "US");

    //Expectation
    var expectedDate = LocalDate.of(2023, 12, 9);

    //When
    var result = plus(givenDate, givenYear, givenMonth, givenDay, givenId, givenLocale);

    //Then
    assertNotNull(result);
    assertEquals(expectedDate.getYear(), result.getYear());
    assertEquals(expectedDate.getMonthValue(), result.getMonthValue());
    assertEquals(expectedDate.getDayOfMonth(), result.getDayOfMonth());
  }


  @Test
  @DisplayName("getting current datetime if ID is null")
  void getCurrentDateTime_IfIdIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    String givenId = null;
    var givenLocale = new Locale("en", "US");
    var givenZone = ZoneId.of("UTC");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> getCurrentDateTime(givenId, givenLocale, givenZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")), message);
  }

  @Test
  @DisplayName("getting current datetime if ID is empty")
  void getCurrentDateTime_IfIdIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenId = "";
    var givenLocale = new Locale("en", "US");
    var givenZone = ZoneId.of("UTC");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> getCurrentDateTime(givenId, givenLocale, givenZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")), message);
  }

  @Test
  @DisplayName("getting current datetime if locale is null")
  void getCurrentDateTime_IfLocaleIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenId = "dummy";
    Locale givenLocale = null;
    var givenZone = ZoneId.of("UTC");

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> getCurrentDateTime(givenId, givenLocale, givenZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.locale")), message);
  }

  @Test
  @DisplayName("getting current datetime if zone is null")
  void getCurrentDateTime_IfZoneIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenId = "dummy";
    var givenLocale = new Locale("dummy");
    ZoneId givenZone = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> getCurrentDateTime(givenId, givenLocale, givenZone));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.zone")), message);
  }

  @Test
  @DisplayName("getting datetime if parameters are valid")
  void getCurrentDateTime_IfParametersAreValid_ShouldReturnDate() {
    //Given
    var givenId = "gregorian";
    var givenLocale = new Locale("en", "US");
    var givenZone = ZoneId.of("UTC");

    //Expectation
    var expectedDateTime = ZonedDateTime.now(givenZone);

    //When
    var result = getCurrentDateTime(givenId, givenLocale, givenZone);

    assertEquals(expectedDateTime.getYear(), result._1.getYear());
    assertEquals(expectedDateTime.getMonthValue(), result._1.getMonthValue());
    assertEquals(expectedDateTime.getDayOfMonth(), result._1.getDayOfMonth());
    assertEquals(expectedDateTime.getHour(), result._1.getHour());
    assertEquals(expectedDateTime.getMinute(), result._1.getMinute());
    assertEquals(expectedDateTime.getSecond(), result._1.getSecond());

    logger.info("date: {}", result._2);
  }

  @ParameterizedTest(name = "[{index}]: From {0} To {1}")
  @ArgumentsSource(GregorianAndKhorshidiLocalDateList.class)
  @DisplayName("converting a gregorian date in any zone to the Khorshidi date in the Asia/Tehran zone")
  void convertCalendar_date_IfParametersAreValid_ShouldReturnKhorshidiDate(String gregorianId, LocalDate gregorian, ZoneId gregorianZone,
                                                                           String khorshidiId, LocalDate khorshidi, ZoneId khorshidiZone) {

    //When
    var result = convertCalendar(
        gregorian,
        gregorianId, new Locale("en", "US"), gregorianZone,
        khorshidiId, new Locale("fa", "IR"), khorshidiZone
    );

    //Then
    assertNotNull(result);
    assertNotNull(result._1);
    assertNotNull(result._2);
    assertEquals(khorshidi.getYear(), result._1.getYear());
    assertEquals(khorshidi.getMonthValue(), result._1.getMonthValue());
    assertEquals(khorshidi.getDayOfMonth(), result._1.getDayOfMonth());
    logger.info(result._2());
  }

  @ParameterizedTest(name = "[{index}]: From {0} To {1}")
  @ArgumentsSource(GregorianAndKhorshidiLocalDateList.class)
  @DisplayName("converting a khorshidi date in the Asia/Tehran zone to the gregorian date in a zone")
  void convertCalendar_date_IfParametersAreValid_ShouldReturnGregorianDate(String gregorianId, LocalDate gregorian, ZoneId gregorianZone,
                                                                           String khorshidiId, LocalDate khorshidi, ZoneId khorshidiZone) {

    //When
    var result = convertCalendar(
        khorshidi,
        khorshidiId, new Locale("fa", "IR"), khorshidiZone,
        gregorianId, new Locale("en", "US"), gregorianZone
    );

    //Then
    assertNotNull(result);
    assertNotNull(result._1);
    assertNotNull(result._2);
    assertEquals(gregorian.getYear(), result._1.getYear());
    assertEquals(gregorian.getMonthValue(), result._1.getMonthValue());
    assertEquals(gregorian.getDayOfMonth(), result._1.getDayOfMonth());
    logger.info(result._2());
  }

  @ParameterizedTest(name = "[{index}]: From {1} To {3}")
  @ArgumentsSource(GregorianAndKhorshidiZonedDateTimeList.class)
  @DisplayName("converting a gregorian datetime in any zone to the Khorshidi datetime in the Asia/Tehran zone")
  void convertCalendar_datetime_IfParametersAreValid_ShouldReturnKhorshidiDateTime(String gregorianId, ZonedDateTime gregorian,
                                                                                   String khorshidiId, ZonedDateTime khorshidi) {

    //When
    var result = convertCalendar(
        gregorian.toLocalDateTime(),
        gregorianId, new Locale("en", "US"), gregorian.getZone(),
        khorshidiId, new Locale("fa", "IR"), khorshidi.getZone()
    );

    //Then
    assertNotNull(result);
    assertNotNull(result._1);
    assertNotNull(result._2);
    assertEquals(khorshidi.getYear(), result._1.getYear());
    assertEquals(khorshidi.getMonthValue(), result._1.getMonthValue());
    assertEquals(khorshidi.getDayOfMonth(), result._1.getDayOfMonth());
    assertEquals(khorshidi.getHour(), result._1.getHour());
    assertEquals(khorshidi.getMinute(), result._1.getMinute());
    assertEquals(khorshidi.getSecond(), result._1.getSecond());
    logger.info(result._2());
  }

  @ParameterizedTest(name = "[{index}]: From {3} to {1}")
  @ArgumentsSource(GregorianAndKhorshidiZonedDateTimeList.class)
  @DisplayName("converting a Khorshidi datetime in the Asia/Tehran zone to the gregorian date in a zone")
  void convertCalendar_datetime_IfParametersAreValid_ShouldReturnGregorianDateTime(String gregorianId, ZonedDateTime gregorian,
                                                                                   String khorshidiId, ZonedDateTime khorshidi) {
    var result = convertCalendar(
        khorshidi.toLocalDateTime(),
        khorshidiId, new Locale("fa", "IR"), khorshidi.getZone(),
        gregorianId, new Locale("en", "US"), gregorian.getZone());

    //Then
    assertNotNull(result);
    assertNotNull(result._1);
    assertNotNull(result._2);
    assertEquals(gregorian.getYear(), result._1.getYear());
    assertEquals(gregorian.getMonthValue(), result._1.getMonthValue());
    assertEquals(gregorian.getDayOfMonth(), result._1.getDayOfMonth());
    assertEquals(gregorian.getHour(), result._1.getHour());
    assertEquals(gregorian.getMinute(), result._1.getMinute());
    assertEquals(gregorian.getSecond(), result._1.getSecond());
    logger.info(result._2());
  }

  @Test
  @DisplayName("checking a year is leap year")
  void isLeap_IfYearIsLeap_ShouldReturnTrue() {
    var givenYear = 1399;
    var givenId = "khorshidi";
    var givenLocale = new Locale("en", "US");

    var result = isLeap(givenYear, givenId, givenLocale);

    assertTrue(result);
  }

  @Test
  @DisplayName("checking a year is non leap year")
  void isLeap_IfYearIsNotLeap_ShouldReturnFalse() {
    var givenYear = 1400;
    var givenId = "khorshidi";
    var givenLocale = new Locale("en", "US");

    var result = isLeap(givenYear, givenId, givenLocale);

    assertFalse(result);
  }

  static class ZoneIdList implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of(ZoneId.of("Asia/Tehran"), ZoneId.of("Japan"), "+05:30"),
          Arguments.of(ZoneId.of("Asia/Tehran"), ZoneId.of("Europe/Berlin"), "-02:30"),
          Arguments.of(ZoneId.of("Asia/Tehran"), ZoneId.of("UTC"), "-03:30"),
          Arguments.of(ZoneId.of("Asia/Tehran"), ZoneId.of("America/New_York"), "-07:30"),
          Arguments.of(ZoneId.of("Europe/Berlin"), ZoneId.of("Japan"), "+08:00"),
          Arguments.of(ZoneId.of("Europe/Berlin"), ZoneId.of("Asia/Tehran"), "+02:30"),
          Arguments.of(ZoneId.of("Europe/Berlin"), ZoneId.of("UTC"), "-01:00"),
          Arguments.of(ZoneId.of("Europe/Berlin"), ZoneId.of("America/New_York"), "-05:00"),
          Arguments.of(ZoneId.of("America/New_York"), ZoneId.of("Japan"), "+13:00"),
          Arguments.of(ZoneId.of("America/New_York"), ZoneId.of("Asia/Tehran"), "+07:30"),
          Arguments.of(ZoneId.of("America/New_York"), ZoneId.of("Europe/Berlin"), "+05:00"),
          Arguments.of(ZoneId.of("America/New_York"), ZoneId.of("UTC"), "+04:00"),
          Arguments.of(ZoneId.of("Japan"), ZoneId.of("Asia/Tehran"), "-05:30"),
          Arguments.of(ZoneId.of("Japan"), ZoneId.of("Europe/Berlin"), "-08:00"),
          Arguments.of(ZoneId.of("Japan"), ZoneId.of("UTC"), "-09:00"),
          Arguments.of(ZoneId.of("Japan"), ZoneId.of("America/New_York"), "-13:00"),
          Arguments.of(ZoneId.of("UTC"), ZoneId.of("UTC"), "00:00")
      );
    }
  }

  static class GregorianCurrentZonedDateTimeList implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of(ZonedDateTime.now(ZoneId.of("Asia/Tehran")), ZonedDateTime.now(ZoneId.of("Japan"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Asia/Tehran")), ZonedDateTime.now(ZoneId.of("Europe/Berlin"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Asia/Tehran")), ZonedDateTime.now(ZoneId.of("UTC"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Asia/Tehran")), ZonedDateTime.now(ZoneId.of("America/New_York"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Europe/Berlin")), ZonedDateTime.now(ZoneId.of("Japan"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Europe/Berlin")), ZonedDateTime.now(ZoneId.of("Asia/Tehran"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Europe/Berlin")), ZonedDateTime.now(ZoneId.of("UTC"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Europe/Berlin")), ZonedDateTime.now(ZoneId.of("America/New_York"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("America/New_York")), ZonedDateTime.now(ZoneId.of("Japan"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("America/New_York")), ZonedDateTime.now(ZoneId.of("Asia/Tehran"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("America/New_York")), ZonedDateTime.now(ZoneId.of("Europe/Berlin"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("America/New_York")), ZonedDateTime.now(ZoneId.of("UTC"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Japan")), ZonedDateTime.now(ZoneId.of("Asia/Tehran"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Japan")), ZonedDateTime.now(ZoneId.of("Europe/Berlin"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Japan")), ZonedDateTime.now(ZoneId.of("UTC"))),
          Arguments.of(ZonedDateTime.now(ZoneId.of("Japan")), ZonedDateTime.now(ZoneId.of("America/New_York")))
      );
    }

  }

  static class GregorianAndKhorshidiZonedDateTimeList implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of(
              "gregorian", ZonedDateTime.of(LocalDateTime.of(2021, 3, 20, 20, 30, 0), ZoneId.of("UTC")),
              "khorshidi", ZonedDateTime.of(LocalDateTime.of(1399, 12, 30, 0, 0, 0), ZoneId.of("Asia/Tehran"))
          ),
          Arguments.of(
              "gregorian", ZonedDateTime.of(LocalDateTime.of(2021, 3, 20, 20, 30, 1), ZoneId.of("UTC")),
              "khorshidi", ZonedDateTime.of(LocalDateTime.of(1400, 1, 1, 0, 0, 1), ZoneId.of("Asia/Tehran"))
          ),
          Arguments.of(
              "gregorian", ZonedDateTime.of(LocalDateTime.of(2021, 3, 20, 23, 59, 59), ZoneId.of("UTC")),
              "khorshidi", ZonedDateTime.of(LocalDateTime.of(1400, 1, 1, 3, 29, 59), ZoneId.of("Asia/Tehran"))
          ),
          Arguments.of(
              "gregorian", ZonedDateTime.of(LocalDateTime.of(2021, 3, 21, 0, 0, 0), ZoneId.of("UTC")),
              "khorshidi", ZonedDateTime.of(LocalDateTime.of(1400, 1, 1, 3, 30, 0), ZoneId.of("Asia/Tehran"))
          ),
          Arguments.of(
              "gregorian", ZonedDateTime.of(LocalDateTime.of(2021, 4, 20, 19, 29, 59), ZoneId.of("UTC")),
              "khorshidi", ZonedDateTime.of(LocalDateTime.of(1400, 1, 31, 23, 59, 59), ZoneId.of("Asia/Tehran"))
          ),
          Arguments.of(
              "gregorian", ZonedDateTime.of(LocalDateTime.of(2021, 4, 20, 19, 30, 0), ZoneId.of("UTC")),
              "khorshidi", ZonedDateTime.of(LocalDateTime.of(1400, 1, 31, 0, 0, 0), ZoneId.of("Asia/Tehran"))
          ),
          Arguments.of(
              "gregorian", ZonedDateTime.of(LocalDateTime.of(2021, 4, 20, 0, 0, 0), ZoneId.of("Asia/Tehran")),
              "khorshidi", ZonedDateTime.of(LocalDateTime.of(1400, 1, 31, 0, 0, 0), ZoneId.of("Asia/Tehran"))
          ),
          Arguments.of(
              "gregorian", ZonedDateTime.of(LocalDateTime.of(2021, 3, 21, 8, 30, 0), ZoneId.of("Asia/Tehran")),
              "khorshidi", ZonedDateTime.of(LocalDateTime.of(1400, 1, 1, 8, 30, 0), ZoneId.of("Asia/Tehran"))
          ),
          Arguments.of(
              "gregorian", ZonedDateTime.of(LocalDateTime.of(2022, 3, 25, 13, 47, 0), ZoneId.of("UTC")),
              "khorshidi", ZonedDateTime.of(LocalDateTime.of(1401, 1, 5, 18, 17, 0), ZoneId.of("Asia/Tehran"))
          )
      );
    }
  }

  static class GregorianLocalDateList implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of(
              ZoneId.of("UTC"), LocalDate.of(2021, 3, 20),
              ZoneId.of("Asia/Tehran"), LocalDate.of(2021, 3, 20)
          ),
          Arguments.of(
              ZoneId.of("Japan"), LocalDate.of(2021, 3, 20),
              ZoneId.of("Asia/Tehran"), LocalDate.of(2021, 3, 19)
          ),
          Arguments.of(
              ZoneId.of("America/New_York"), LocalDate.of(2021, 3, 20),
              ZoneId.of("Asia/Tehran"), LocalDate.of(2021, 3, 20)
          ),
          Arguments.of(
              ZoneId.of("Japan"), LocalDate.of(2021, 3, 21),
              ZoneId.of("America/New_York"), LocalDate.of(2021, 3, 20)
          ),
          Arguments.of(
              ZoneId.of("UTC"), LocalDate.of(2021, 4, 20),
              ZoneId.of("Asia/Tehran"), LocalDate.of(2021, 4, 20)
          ),
          Arguments.of(
              ZoneId.of("Asia/Tehran"), LocalDate.of(2021, 4, 20),
              ZoneId.of("Europe/Berlin"), LocalDate.of(2021, 4, 19)
          ),
          Arguments.of(
              ZoneId.of("Asia/Tehran"), LocalDate.of(2021, 4, 20),
              ZoneId.of("Asia/Tehran"), LocalDate.of(2021, 4, 20)
          ),
          Arguments.of(
              ZoneId.of("Europe/Berlin"), LocalDate.of(2021, 3, 21),
              ZoneId.of("America/New_York"), LocalDate.of(2021, 3, 20)
          )
      );
    }
  }

  static class GregorianAndKhorshidiLocalDateList implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of(
              "gregorian", LocalDate.of(2021, 3, 20), ZoneId.of("UTC"),
              "khorshidi", LocalDate.of(1399, 12, 30), ZoneId.of("Asia/Tehran")
          ),
          Arguments.of(
              "gregorian", LocalDate.of(2021, 3, 21), ZoneId.of("UTC"),
              "khorshidi", LocalDate.of(1400, 1, 1), ZoneId.of("Asia/Tehran")
          ),
          Arguments.of(
              "gregorian", LocalDate.of(2021, 4, 20), ZoneId.of("UTC"),
              "khorshidi", LocalDate.of(1400, 1, 31), ZoneId.of("Asia/Tehran")
          ),
          Arguments.of(
              "gregorian", LocalDate.of(2021, 4, 20), ZoneId.of("UTC"),
              "khorshidi", LocalDate.of(1400, 1, 31), ZoneId.of("Asia/Tehran")
          ),
          Arguments.of(
              "gregorian", LocalDate.of(2021, 4, 20), ZoneId.of("Asia/Tehran"),
              "khorshidi", LocalDate.of(1400, 1, 31), ZoneId.of("Asia/Tehran")
          ),
          Arguments.of(
              "gregorian", LocalDate.of(2021, 3, 21), ZoneId.of("Asia/Tehran"),
              "khorshidi", LocalDate.of(1400, 1, 1), ZoneId.of("Asia/Tehran")
          )
      );
    }
  }
}
