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
import static com.pineframework.core.helper.JdbcUtils.createSpliterator;
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static com.pineframework.core.helper.validator.StringValidator.requireNonEmptyOrNull;

import io.vavr.control.Try;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.StreamSupport;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;
import oracle.sql.TIMESTAMP;
import org.slf4j.Logger;

/**
 * The {@link OracleJdbcUtils} class provides utility functions to work with Oracle JDBC.
 * <ul>
 *  <li>{@link #getListenerProperties()}</li>
 *  <li>{@link #registerChangeNotification(OracleConnection, DatabaseChangeListener, String)}</li>
 *  <li>{@link #convertToJavaType(Object, Class)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class OracleJdbcUtils {

  public static final Logger LOGGER = LogUtils.getLogger(OracleJdbcUtils.class.getSimpleName());

  private OracleJdbcUtils() {
  }

  /**
   * The {@code getListenerProperties} method is included properties of oracle database listener.
   *
   * @return {@link Properties}
   */
  public static Properties getListenerProperties() {
    Properties properties = new Properties();
    properties.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
    return properties;
  }

  /**
   * The {@code registerChangeNotification} method registers a listener for a table.
   *
   * @param connection {@link OracleConnection}
   * @param listener   {@link DatabaseChangeListener}
   * @param tableName  name of the table
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static void registerChangeNotification(OracleConnection connection, DatabaseChangeListener listener, String tableName) {
    requireNonNull(connection, i18n("error.validation.should.not.be.null", i18n("var.name.connection")));
    requireNonNull(listener, i18n("error.validation.should.not.be.null", i18n("var.name.listener")));
    requireNonEmptyOrNull(tableName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.tableName")));

    Try.run(() -> {
      DatabaseChangeRegistration notification = connection.registerDatabaseChangeNotification(getListenerProperties());
      notification.addListener(listener);

      Try.withResources(connection::createStatement)
          .of(statement -> {
            ((OracleStatement) statement).setDatabaseChangeRegistration(notification);
            return statement.executeQuery(String.format("SELECT * FROM %s WHERE 1=2", tableName.toUpperCase()));
          });
      LOGGER.info("register Oracle DB listener: {}", connection.getUserName().toUpperCase() + "." + tableName.toUpperCase());
    }).onFailure(exception -> LOGGER.error(exception.getMessage()));
  }

  /**
   * The {@code unregisterChangeNotification} method unregisters a listener for a table.
   *
   * @param connection {@link OracleConnection}
   * @param tableName  name of the table
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static void unregisterChangeNotification(OracleConnection connection, String tableName) {
    Try.run(() -> {
      var query = "SELECT REGID,CALLBACK FROM USER_CHANGE_NOTIFICATION_REGS WHERE TABLE_NAME = ?";
      var statement = connection.prepareStatement(query);
      statement.setString(1, connection.getUserName().toUpperCase() + "." + tableName.toUpperCase());
      var resultSet = statement.executeQuery();
      StreamSupport.stream(createSpliterator(resultSet), false)
          .forEach(tuple -> Try.run(() -> {
            var regId = tuple.getLong("REGID");
            var callback = tuple.getString("CALLBACK");
            connection.unregisterDatabaseChangeNotification(regId, callback);
            LOGGER.info("unregister Oracle DB listener: [REGID={}] , [CALLBACK=♦{}]", regId, callback);
          }));
      resultSet.close();
      statement.close();
    }).onFailure(exception -> LOGGER.error(exception.getMessage()));
  }

  /**
   * The {@code isListenerExist} method {@code USER_CHANGE_NOTIFICATION_REGS} table
   * and returns {@code true} if there is a listener belong to the table, otherwise
   * it returns {@code false}.
   *
   * @param connection {@link Connection}
   * @param tableName  name of table
   * @return {@code true} if there is a listener belong to the table, otherwise
   * it returns {@code false}
   */
  public static boolean isListenerExist(Connection connection, String tableName) {
    return Try.of(() -> {
      var query = "SELECT count(*) as row_no FROM USER_CHANGE_NOTIFICATION_REGS WHERE TABLE_NAME = ?";
      var statement = connection.prepareStatement(query);
      statement.setString(1, tableName.toUpperCase());
      var resultSet = statement.executeQuery();

      resultSet.next();
      var rowNo = resultSet.getLong("row_no");

      resultSet.close();
      statement.close();

      return rowNo > 0;
    }).onFailure(exception -> LOGGER.info(exception.getMessage())).get();
  }

  /**
   * The {@code convertToJavaType} converts a data with Oracle database data type to a data with Java type.
   *
   * @param value database data
   * @param type  class object of {@code E}
   * @param <T>   Java type
   * @return data with Java type
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  @SuppressWarnings("unchecked")
  public static <T> T convertToJavaType(Object value, Class<T> type) {
    requireNonNull(value, i18n("error.validation.should.not.be.null", i18n("var.name.value")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    return (T) DataBaseTypeConverter.CONVERTERS.get(type).apply(value);
  }

  /**
   * The {@link DataBaseTypeConverter} class is included java data type converters.
   *
   * @author Saman Alishirishahrbabak
   * @version 1.0.0
   * @since 2022-01-01
   */
  private static final class DataBaseTypeConverter {

    private static final Map<Class<?>, Function<Object, Object>> CONVERTERS = new HashMap<>();

    static {
      CONVERTERS.put(Integer.class, o -> Integer.valueOf(String.valueOf(o)));
      CONVERTERS.put(Long.class, o -> Long.valueOf(String.valueOf(o)));
      CONVERTERS.put(Float.class, o -> Float.valueOf(String.valueOf(o)));
      CONVERTERS.put(Double.class, o -> Double.valueOf(String.valueOf(o)));
      CONVERTERS.put(String.class, String::valueOf);
      CONVERTERS.put(LocalDateTime.class, o -> Try.of(() -> ((TIMESTAMP) o).timestampValue().toLocalDateTime()).get());
      CONVERTERS.put(LocalDate.class, o -> Try.of(() -> ((TIMESTAMP) o).dateValue().toLocalDate()).get());
      CONVERTERS.put(LocalTime.class, o -> Try.of(() -> ((TIMESTAMP) o).timeValue().toLocalTime()).get());
      CONVERTERS.put(Boolean.class, o -> Integer.parseInt(String.valueOf(o)) == 1);
    }
  }
}
