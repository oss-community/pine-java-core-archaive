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
import static com.pineframework.core.helper.YesNoQuestion.ask;
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static com.pineframework.core.helper.validator.StringValidator.requireNonEmptyOrNull;
import static io.vavr.control.Try.withResources;
import static java.util.stream.StreamSupport.stream;

import io.vavr.control.Try;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * The {@link  DbMetadataUtils} class provides utility functions for metadata of relational databases.
 * <ul>
 *   <li>{@link #getTables(Connection)}</li>
 *   <li>{@link #getColumns(Connection, String)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class DbMetadataUtils {

  private DbMetadataUtils() {
  }

  /**
   * The {@code getObject} method get a column values from {@link ResultSet}.
   *
   * @param rs     {@link ResultSet}
   * @param object name of metadata entity
   * @return {@link String}[]
   */
  private static String[] getObject(ResultSet rs, String object) {
    return stream(createSpliterator(rs), false)
        .map(tuple -> Try.of(() -> tuple.getString(object)).get())
        .toArray(String[]::new);
  }

  /**
   * The {@code getTables} method returns all table names belonging to a database.
   *
   * @param connection {@link Connection}
   * @return {@link String}[]
   * @throws IllegalArgumentException if {@code connection} is {@code null}
   */
  public static String[] getTables(Connection connection) {
    requireNonNull(connection, i18n("error.validation.should.not.be.null", i18n("var.name.connection")));
    ask(Try.of(connection::isClosed).get())
        .yes(() -> {
          throw new IllegalArgumentException(i18n("error.validation.connection.is.close"));
        });

    return withResources(() -> connection.getMetaData().getTables(null, null, null, new String[] {"TABLE"}))
        .of(rs -> getObject(rs, "TABLE_NAME"))
        .get();
  }

  /**
   * The {@code getColumns} method returns name of table columns as a string array.
   *
   * @param connection {@link Connection}
   * @param table      a table name
   * @return {@link String}[]
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static String[] getColumns(Connection connection, String table) {
    requireNonNull(connection, i18n("error.validation.should.not.be.null", i18n("var.name.connection")));
    requireNonEmptyOrNull(table, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.tableName")));
    ask(Try.of(connection::isClosed).get())
        .yes(() -> {
          throw new IllegalArgumentException(i18n("error.validation.connection.is.close"));
        });

    return withResources(() -> connection.getMetaData().getColumns(null, null, table.toUpperCase(), null))
        .of(rs -> getObject(rs, "COLUMN_NAME"))
        .get();
  }
}
