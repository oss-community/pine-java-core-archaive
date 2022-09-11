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
import static com.pineframework.core.helper.NumberUtils.NEGATIVE;
import static com.pineframework.core.helper.NumberUtils.POSITIVE;
import static com.pineframework.core.helper.NumberUtils.getSign;
import static com.pineframework.core.helper.NumberUtils.getSignChar;
import static com.pineframework.core.helper.validator.DateTimeValidator.isTime;
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static com.pineframework.core.helper.validator.StringValidator.requireNonEmptyOrNull;
import static java.lang.Integer.parseInt;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.US;
import static java.util.ResourceBundle.getBundle;
import static java.util.stream.Collectors.toMap;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * The {@link DateUtils} class provides utility functions for date.
 * <ul>
 *   <li>{@link #createFormat(String, Locale, String)}</li>
 *   <li>{@link #allZoneAndOffsetRelatedTo(ZoneId)}</li>
 *   <li>{@link #calculateOffset(ZoneId, ZoneId)}</li>
 *   <li>{@link #toSecond(String)}</li>
 *   <li>{@link #toDate(LocalDateTime, String, Locale)}</li>
 *   <li>{@link #toDate(LocalDate, String, Locale)}</li>
 *   <li>{@link #changeZone(LocalDateTime, ZoneId, ZoneId)}</li>
 *   <li>{@link #changeZone(LocalDate, ZoneId, ZoneId)}</li>
 *   <li>{@link #changeZoneRightNow(LocalDate, ZoneId, ZoneId)}</li>
 *   <li>{@link #plus(LocalDate, int, int, int, String, Locale)}</li>
 *   <li>{@link #getCurrentDateTime(String, Locale, ZoneId)} </li>
 *   <li>{@link #convertCalendar(LocalDateTime, String, Locale, ZoneId, String, Locale, ZoneId)}</li>
 *   <li>{@link #convertCalendar(LocalDate, String, Locale, ZoneId, String, Locale, ZoneId)}</li>
 *   <li>{@link #isLeap(int, String, Locale)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
@SuppressWarnings("DuplicatedCode")
public final class DateUtils {

  /**
   * The {@code PROFILES} is included all resources with the name
   * like {@code calendar_}*.
   * <p>
   * There are two default profiles, Persian and the other one is Gregorian.
   * </p>
   */
  private static final Map<String, ResourceBundle> PROFILES;

  static {
    var path = Try.of(() -> new File("src/main/resources")).get();
    var files = requireNonNull(path.listFiles(), i18n("error.validation.should.not.be.null", i18n("var.name.file")));
    PROFILES = Stream.of(files)
        .filter(file -> file.getName().startsWith("calendar_"))
        .collect(toMap(file -> file.getName().split("\\.")[0].split("_")[1], file -> getBundle(file.getName().split("\\.")[0])));
  }

  private DateUtils() {
  }


  /**
   * The {@code forLocale} method finds the proper {@link ULocale} for the {@link Locale}.
   *
   * @param id     unique id that is part of the name of the profile properties
   * @param locale {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @return {@link ULocale}, ICU4j locale
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  private static ULocale forLocale(String id, Locale locale) {
    requireNonEmptyOrNull(id, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(locale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));

    var bundle = PROFILES.get(id);
    return new ULocale(locale + "@calendar=" + bundle.getString("calendar.instance"));
  }

  /**
   * The {@code createFormat} method creates {@link SimpleDateFormat}.
   *
   * @param id      unique id that is part of the name of the profile properties
   * @param locale  {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @param pattern date format <example>{@code "yyyy-MMM-dd'T'HH:mm:ss"}</example>
   * @return {@link SimpleDateFormat}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static SimpleDateFormat createFormat(String id, Locale locale, String pattern) {
    requireNonEmptyOrNull(id, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(locale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));
    requireNonEmptyOrNull(pattern, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.pattern")));

    return new SimpleDateFormat(pattern, forLocale(id, locale));
  }

  /**
   * The {@code allZoneAndOffsetRelatedTo} method returns a {@link Map}
   * include all time zones with offsets related to the {@code zone}.
   *
   * @param zone {@link ZoneId}
   * @return {@link Map}
   * @throws IllegalArgumentException if {@code zone} is {@code null}
   */
  public static Map<String, String> allZoneAndOffsetRelatedTo(ZoneId zone) {
    requireNonNull(zone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));

    return ZoneId.getAvailableZoneIds().stream().map(ZoneId::of)
        .map(zoneId -> new SimpleEntry<>(zoneId.toString(), calculateOffset(zone, zoneId)))
        .collect(toMap(SimpleEntry::getKey, SimpleEntry::getValue));
  }

  /**
   * The {@code calculateOffset} method calculates offset between
   * two time-zones and returns it as a string time with (±00:00) format.
   *
   * @param zone1 {@link ZoneId} reference zone
   * @param zone2 {@link ZoneId} function zone
   * @return offset as a {@link String} with (+HH:mm) format
   * @throws IllegalArgumentException if {@code zone1} or {@code zone2} is {@code null}
   */
  public static String calculateOffset(ZoneId zone1, ZoneId zone2) {
    requireNonNull(zone1, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));
    requireNonNull(zone2, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));

    var referenceOffset = ZonedDateTime.now(zone1).getOffset().getTotalSeconds();
    var functionOffset = ZonedDateTime.now(zone2).getOffset().getTotalSeconds();
    var offsetDiff = functionOffset - referenceOffset;

    var time = ofPattern("HH:mm").format(LocalTime.ofSecondOfDay(Math.abs(offsetDiff)));

    var sign = switch (Integer.compare(offsetDiff, 0)) {
      case 1 -> "+";
      case -1 -> "-";
      default -> "";
    };

    return String.format("%s%s", sign, time);
  }

  /**
   * The {@code calculateOffset} method calculates offset between
   * two time-zones and returns it as a string time with (±00:00) format.
   *
   * @param zone1 {@link ZoneId} reference zone
   * @param zone2 {@link ZoneId} function zone
   * @return offset as a {@link String} with (+HH:mm) format
   * @throws IllegalArgumentException if {@code zone1} or {@code zone2} is {@code null}
   */
  public static String calculateOffset(LocalDateTime dateTime, ZoneId zone1, ZoneId zone2) {
    requireNonNull(zone1, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));
    requireNonNull(zone2, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));

    var referenceOffset = ZonedDateTime.of(dateTime, zone1).getOffset().getTotalSeconds();
    var functionOffset = ZonedDateTime.of(dateTime, zone2).getOffset().getTotalSeconds();
    var offsetDiff = functionOffset - referenceOffset;

    var time = ofPattern("HH:mm").format(LocalTime.ofSecondOfDay(Math.abs(offsetDiff)));

    return String.format("%s%s", offsetDiff > 0 ? "+" : "-", time);
  }

  /**
   * The {@code toSecond} method converts string time with ISO format
   * (HH:mm:ss) or (+HH:mm) to second.
   *
   * @param time anytime with (HH:mm:ss) or (+HH:mm) format
   * @return seconds as an {@code int} value
   * @throws IllegalArgumentException if {@code time} is {@code null} or empty, or it does not have valid format
   */
  public static int toSecond(String time) {
    isTime(time).no(() -> {
      throw new IllegalArgumentException(i18n("error.validation.is.not.valid", i18n("var.name.format"), i18n("var.name.time")));
    });

    var timeParts = time.split(":");

    var sign = getSign(timeParts[0]);

    var h = parseInt(timeParts[0]) * 3600;
    var m = parseInt(timeParts[1]) * 60 * sign;
    var s = (timeParts.length > 2) ? parseInt(timeParts[2]) * sign : 0;

    return h + m + s;
  }

  /**
   * The {@code toDate} method converts a {@link LocalDateTime} to a {@link Date}
   * in a specific calendar.
   *
   * @param dateTime {@link LocalDateTime}
   * @param id       unique id that is part of the name of the profile properties
   * @param locale   {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @return {@link Date}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static Date toDate(LocalDateTime dateTime, String id, Locale locale) {
    requireNonNull(dateTime, i18n("error.validation.should.not.be.null", i18n("var.name.dataAndTime")));
    requireNonEmptyOrNull(id, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(locale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));

    var calendar = Calendar.getInstance(forLocale(id, locale));
    calendar.set(dateTime.getYear(), dateTime.getMonthValue() - 1, dateTime.getDayOfMonth(),
        dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond());

    return calendar.getTime();
  }

  /**
   * The {@code toDate} method converts a {@link LocalDate} to a
   * {@link Date} in a specific calendar.
   *
   * @param date   {@link LocalDate}
   * @param id     unique id that is part of the name of the profile properties
   * @param locale {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @return {@link Date}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static Date toDate(LocalDate date, String id, Locale locale) {
    requireNonNull(date, i18n("error.validation.should.not.be.null", i18n("var.name.date")));
    requireNonEmptyOrNull(id, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(locale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));

    var calendar = Calendar.getInstance(forLocale(id, locale));
    calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());

    return calendar.getTime();
  }

  /**
   * The {@code changeZone} method changes the time zone of {@link LocalDateTime}.
   *
   * @param dateTime    {@link LocalDateTime}
   * @param currentZone {@link ZoneId}
   * @param nextZone    {@link ZoneId}
   * @return {@link LocalDateTime}
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  public static LocalDateTime changeZone(LocalDateTime dateTime, ZoneId currentZone, ZoneId nextZone) {
    requireNonNull(dateTime, i18n("error.validation.should.not.be.null", i18n("var.name.dataAndTime")));
    requireNonNull(currentZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));
    requireNonNull(nextZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));

    return ZonedDateTime.of(dateTime, currentZone).withZoneSameInstant(nextZone).toLocalDateTime();
  }

  /**
   * The {@code changeZone} method changes the time zone of {@link LocalDate}
   * from start of the day.
   *
   * @param date        {@link LocalDate}
   * @param currentZone {@link ZoneId}
   * @param nextZone    {@link ZoneId}
   * @return {@link LocalDate}
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  public static LocalDate changeZone(LocalDate date, ZoneId currentZone, ZoneId nextZone) {
    requireNonNull(date, i18n("error.validation.should.not.be.null", i18n("var.name.date")));
    requireNonNull(currentZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));
    requireNonNull(nextZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));

    return date.atStartOfDay(currentZone).withZoneSameInstant(nextZone).toLocalDate();
  }

  /**
   * The {@code changeZone} method changes the time zone of {@link LocalDate} at right now.
   *
   * @param date     {@link LocalDate}
   * @param nextZone {@link ZoneId}
   * @return {@link LocalDate}
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  public static LocalDate changeZoneRightNow(LocalDate date, ZoneId currentZone, ZoneId nextZone) {
    requireNonNull(date, i18n("error.validation.should.not.be.null", i18n("var.name.date")));
    requireNonNull(currentZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));
    requireNonNull(nextZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));

    return date.atTime(LocalTime.now(currentZone)).atZone(currentZone).withZoneSameInstant(nextZone).toLocalDate();
  }

  /**
   * The {@code plus} method Adds year(s), month(s) and/or day(s) to a specific {@link LocalDate}.
   *
   * @param date   {@link LocalDate}
   * @param years  additional year(s)
   * @param months additional month(s)
   * @param days   additional day(s)
   * @param id     unique id that is part of the name of the profile properties
   * @param locale {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @return {@link LocalDate}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static LocalDate plus(LocalDate date, int years, int months, int days, String id, Locale locale) {
    requireNonNull(date, i18n("error.validation.should.not.be.null", i18n("var.name.date")));
    requireNonEmptyOrNull(id, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(locale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));

    var calendar = Calendar.getInstance(forLocale(id, locale));
    calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
    calendar.add(Calendar.YEAR, years);
    calendar.add(Calendar.MONTH, months);
    calendar.add(Calendar.DATE, days);

    var dateFormat = createFormat(id, locale, "yyyy-MM-dd");
    dateFormat.setNumberFormat(NumberFormat.getNumberInstance());

    return LocalDate.parse(dateFormat.format(calendar.getTime()), ofPattern("yyyy-MM-dd"));
  }

  /**
   * The {@code getDayDifference} method calculates the day difference between
   * two time zones in a specific time.
   *
   * @param currentTime {@link LocalTime}
   * @param currentZone {@link ZoneId}
   * @param nextTime    {@link LocalTime}
   * @param nextZone    {@link ZoneId}
   * @return day as an {@code int} value
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static int getDayDifference(LocalDate date, LocalTime currentTime, ZoneId currentZone, LocalTime nextTime, ZoneId nextZone) {
    requireNonNull(currentTime, i18n("error.validation.should.not.be.null", i18n("var.name.time")));
    requireNonNull(currentZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));
    requireNonNull(nextTime, i18n("error.validation.should.not.be.null", i18n("var.name.time")));
    requireNonNull(nextZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));

    var currentOffset = calculateOffset(LocalDateTime.of(date, currentTime), ZoneId.of("UTC"), currentZone);
    var nextOffset = calculateOffset(LocalDateTime.of(date, nextTime), ZoneId.of("UTC"), nextZone);
    var offsetDiff = toSecond(nextOffset) - toSecond(currentOffset);

    var time = LocalTime.ofSecondOfDay(Math.abs(offsetDiff));

    return switch (getSignChar(offsetDiff)) {
      case POSITIVE -> nextTime.isBefore(time) && currentTime.isAfter(LocalTime.MIDNIGHT.minusSeconds(time.toSecondOfDay())) ? 1 : 0;
      case NEGATIVE -> currentTime.isBefore(time) && nextTime.isAfter(LocalTime.MIDNIGHT.minusSeconds(time.toSecondOfDay())) ? -1 : 0;
      default -> 0;
    };
  }

  /**
   * The {@code getCurrentDateTime} methods returns date-time of belonging to specific region.
   *
   * @param id     unique id that is part of the name of the profile properties
   * @param locale {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @param zone   {@link ZoneId}
   * @return {@link Tuple2}, include two date-times with different formats
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static Tuple2<LocalDateTime, String> getCurrentDateTime(String id, Locale locale, ZoneId zone) {
    requireNonEmptyOrNull(id, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(locale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));
    requireNonNull(zone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));

    var date = toDate(LocalDateTime.now(zone), id, locale);

    var bundle = PROFILES.get(id);
    var format1 = createFormat(id, locale, bundle.getString("calendar.format.dateTime1"));
    var format2 = createFormat(id, locale, bundle.getString("calendar.format.dateTime2"));

    return new Tuple2<>(LocalDateTime.parse(format1.format(date)), format2.format(date));
  }

  /**
   * The {@code covertCalendar} method converts a {@link LocalDate} to another calendars.
   *
   * @param currentDate   {@link ZonedDateTime}
   * @param currentId     unique id that is part of the name of the profile properties
   * @param currentLocale {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @param currentZone   {@link ZoneId}
   * @param nextId        unique id that is part of the name of the profile properties
   * @param nextLocale    {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @param nextZone      {@link ZoneId}
   * @return {@link Tuple2}, include two dates with different formats
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  static Tuple2<LocalDate, String> convertCalendar(LocalDate currentDate, String currentId, Locale currentLocale, ZoneId currentZone,
                                                   String nextId, Locale nextLocale, ZoneId nextZone) {

    requireNonNull(currentDate, i18n("error.validation.should.not.be.null", i18n("var.name.date")));
    requireNonEmptyOrNull(currentId, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(currentLocale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));
    requireNonNull(currentZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));
    requireNonEmptyOrNull(nextId, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(nextLocale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));
    requireNonNull(nextZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));

    var bundle = PROFILES.get(nextId);
    var dateFormat = createFormat(nextId, US, bundle.getString("calendar.format.date1"));
    var nextDate = LocalDate.parse(dateFormat.format(toDate(currentDate, currentId, currentLocale)));

    var datTimeFormat = createFormat(nextId, nextLocale, bundle.getString("calendar.format.date2"));
    var nextDateAsString = datTimeFormat.format(toDate(currentDate, currentId, currentLocale));

    return new Tuple2<>(nextDate, nextDateAsString);
  }

  /**
   * The {@code covertCalendar} method converts a date-time to another calendars.
   *
   * @param currentDateTime {@link ZonedDateTime}
   * @param currentId       unique id that is part of the name of the profile properties
   * @param currentLocale   {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @param currentZone     {@link ZoneId}
   * @param nextId          unique id that is part of the name of the profile properties
   * @param nextLocale      {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @param nextZone        {@link ZoneId}
   * @return {@link Tuple2}, include two date-times with different formats
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  static Tuple2<LocalDateTime, String> convertCalendar(LocalDateTime currentDateTime, String currentId, Locale currentLocale,
                                                       ZoneId currentZone, String nextId, Locale nextLocale, ZoneId nextZone) {

    requireNonNull(currentDateTime, i18n("error.validation.should.not.be.null", i18n("var.name.dataAndTime")));
    requireNonEmptyOrNull(currentId, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(currentLocale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));
    requireNonNull(currentZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));
    requireNonEmptyOrNull(nextId, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(nextLocale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));
    requireNonNull(nextZone, i18n("error.validation.should.not.be.null", i18n("var.name.zone")));

    var currentTime = currentDateTime.toLocalTime();
    var currentDate =
        convertCalendar(currentDateTime.toLocalDate(), currentId, currentLocale, currentZone, "gregorian", new Locale("en", "US"),
            currentZone)._1;
    var nextTime = changeZone(LocalDateTime.of(currentDate, currentTime), currentZone, nextZone).toLocalTime();
    var nextDate = convertCalendar(currentDateTime.toLocalDate(), currentId, currentLocale, currentZone, nextId, nextLocale, nextZone)._1;

    var dayDifference = getDayDifference(currentDate, currentTime, currentZone, nextTime, nextZone);
    currentDate = plus(currentDateTime.toLocalDate(), 0, 0, dayDifference, currentId, currentLocale);
    nextDate = plus(nextDate, 0, 0, dayDifference, nextId, nextLocale);

    var nextDatTime = LocalDateTime.of(nextDate, nextTime);

    var bundle = PROFILES.get(nextId);
    var datTimeFormat = createFormat(nextId, nextLocale, bundle.getString("calendar.format.dateTime2"));
    var nextDateTimeAsString = datTimeFormat.format(toDate(LocalDateTime.of(currentDate, nextTime), currentId, currentLocale));

    return new Tuple2<>(nextDatTime, nextDateTimeAsString);
  }

  /**
   * The {@code isLeap} method checks the {@code year} is leap or not.
   *
   * @param year   year
   * @param id     unique id that is part of the name of the profile properties
   * @param locale {@link Locale}, language of calendar <example>{@code new Locale("en", "US")}</example>
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static boolean isLeap(int year, String id, Locale locale) {
    requireNonEmptyOrNull(id, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.id")));
    requireNonNull(locale, i18n("error.validation.should.not.be.null", i18n("var.name.locale")));

    var calendar = Calendar.getInstance(forLocale(id, locale));
    calendar.set(year, 1, 1);

    return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) == 366;
  }
}
