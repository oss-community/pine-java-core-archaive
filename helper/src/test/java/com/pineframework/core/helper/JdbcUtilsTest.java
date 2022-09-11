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
import static com.pineframework.core.helper.TestEnvironmentConfig.TEST_DB;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.vavr.control.Try;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link JdbcUtilsTest} class provides unit tests for {@link JdbcUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see JdbcUtils
 * @since 2022-01-01
 */
@DisplayName("Jdbc Utils Tests")
class JdbcUtilsTest extends AbstractUtilsTest {

  private static Connection connection;

  @BeforeAll
  static void openConnection() {
    connection = TEST_DB.createH2Connection(JdbcUtilsTest.class.getSimpleName());
    TEST_DB.executeSqlFile(connection, "sql/test-db.sql");
  }

  @AfterAll
  static void closeConnection() {
    TEST_DB.close(connection);
  }

  @Test
  @DisplayName("execute SQL query if there is at least one tuple")
  void createSpliterator_IfParameterIsValid_ShouldReturnListOfTuples() {
    //Given
    var givenQuery = "SELECT * FROM TEST_T";

    //Expectation
    var expectedSize = 3;

    //When
    var result = Try.of(() -> {
      var statement = connection.prepareStatement(givenQuery);
      var resultSet = statement.executeQuery();

      var models = StreamSupport.stream(createSpliterator(resultSet), false)
          .map(tuple -> TestDataTransformer.create().apply(tuple))
          .collect(toList());

      statement.close();
      resultSet.close();

      return models;
    }).get();

    //Then
    assertEquals(expectedSize, result.size());

    TestData tuple1 = result.get(0);
    assertEquals(1, tuple1.id);
    assertEquals("text1", tuple1.text);
    assertEquals(1, tuple1.intNumber);

    TestData tuple2 = result.get(1);
    assertEquals(2, tuple2.id);
    assertEquals("text2", tuple2.text);
    assertEquals(2, tuple2.intNumber);

    TestData tuple3 = result.get(2);
    assertEquals(3, tuple3.id);
    assertEquals("text3", tuple3.text);
    assertEquals(3, tuple3.intNumber);
  }

  @Test
  @DisplayName("execute SQL query if there is no tuple")
  void createSpliterator_IfThereIsNoTuple_ShouldReturnNoSuchElementException() {
    //Given
    ResultSet givenResultSet = null;

    //Expectation
    var expectedException = NoSuchElementException.class;

    //When
    var iterator = new ResultSetIterator(givenResultSet);
    var result = assertThrows(expectedException, iterator::next);

    //Then
    assertEquals(i18n("error.validation.can.not.find", i18n("var.name.element")), result.getMessage());
  }

  static class TestData {
    Integer id;
    String text;
    Integer intNumber;
  }

  static class TestDataTransformer implements Function<ResultSet, TestData> {

    public static TestDataTransformer create() {
      return new TestDataTransformer();
    }

    @Override
    public TestData apply(ResultSet rs) {
      return Try.of(() -> {
        TestData entity = new TestData();
        entity.id = rs.getInt("ID");
        entity.text = rs.getNString("TEXT");
        entity.intNumber = rs.getInt("INT_NUMBER");
        return entity;
      }).get();
    }
  }

}
