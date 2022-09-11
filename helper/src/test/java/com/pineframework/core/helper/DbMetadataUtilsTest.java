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

import static com.pineframework.core.helper.DbMetadataUtils.getColumns;
import static com.pineframework.core.helper.DbMetadataUtils.getTables;
import static com.pineframework.core.helper.I18nUtils.i18n;
import static com.pineframework.core.helper.TestEnvironmentConfig.TEST_DB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.vavr.control.Try;
import java.sql.Connection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link DbMetadataUtilsTest} class provides unit tests for {@link DbMetadataUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see DbMetadataUtils
 * @since 2022-01-01
 */
@SuppressWarnings("ConstantConditions")
@DisplayName("Database Metadata Utils Test")
class DbMetadataUtilsTest extends AbstractUtilsTest {

  private static Connection connection;

  @BeforeAll
  static void init() {
    connection = TEST_DB.createH2Connection(DbMetadataUtilsTest.class.getSimpleName());
    TEST_DB.executeSqlFile(connection, "sql/test-db.sql");
  }

  @AfterAll
  static void close() {
    TEST_DB.close(connection);
  }

  @Test
  @DisplayName("getting tables name if connection is null")
  void getTables_IfConnectionIsNull_ShouldIllegalArgumentException() {
    //Given
    Connection givenConnection = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> getTables(givenConnection));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.connection"), 0), message);
  }

  @Test
  @DisplayName("getting tables name if connection be closed")
  void getTables_IfConnectionBeClosed_ShouldIllegalArgumentException() {
    //Given
    var givenConnection = connection;
    Try.run(givenConnection::close);

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> getTables(givenConnection));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.connection.is.close"), message);
  }

  @Test
  @DisplayName("getting tables name if connection is open")
  void getTables_IfConnectionIsOpen_ShouldReturnTableNames() {
    //Expectation
    var expectedTables = new String[] {"TEST_T", "TEST_T2"};

    //when
    var result = getTables(connection);

    //Then
    assertNotNull(result);
    assertThat(result).contains(expectedTables);
  }

  @Test
  @DisplayName("getting columns name of a table if connection is null")
  void getColumns_IfConnectionIsNull_ShouldIllegalArgumentException() {
    //Given
    Connection givenConnection = null;
    var givenTableName = "dummy";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> getColumns(givenConnection, givenTableName));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.connection"), 0), message);
  }

  @Test
  @DisplayName("getting columns name of a table if table name is null")
  void getColumns_IfTableNameIsNull_ShouldIllegalArgumentException() {
    //Given
    var givenConnection = connection;
    String givenTableName = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> getColumns(givenConnection, givenTableName));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.tableName"), 0), message);
  }

  @Test
  @DisplayName("getting columns name of a table if table name is empty")
  void getColumns_IfTableNameIsEmpty_ShouldIllegalArgumentException() {
    //Given
    var givenConnection = connection;
    var givenTableName = "";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> getColumns(givenConnection, givenTableName));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.tableName"), 0), message);
  }

  @Test
  @DisplayName("getting columns name of a table if connection be closed")
  void getColumns_IfConnectionBeClosed_ShouldIllegalArgumentException() {
    //Given
    var givenConnection = connection;
    Try.run(givenConnection::close);

    var givenTableName = "TEST_T";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> getColumns(givenConnection, givenTableName));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.connection.is.close"), message);
  }

  @Test
  @DisplayName("getting columns name of a table if parameters are valid")
  void getColumns_IfParametersAreValid_ShouldReturnColumnNames() {
    //Given
    var givenTableName = "TEST_T";

    //Expectation
    var expectedColumns = new String[] {"ID", "TEXT", "INT_NUMBER"};

    //when
    var result = getColumns(connection, givenTableName);

    //Then
    assertNotNull(result);
    assertEquals(expectedColumns.length, result.length);
    assertThat(result).containsOnly(expectedColumns);
  }
}
