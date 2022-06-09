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

import static com.pineframework.core.helper.OracleJdbcUtils.convertToJavaType;
import static com.pineframework.core.helper.OracleJdbcUtils.isListenerExist;
import static com.pineframework.core.helper.OracleJdbcUtils.registerChangeNotification;
import static com.pineframework.core.helper.OracleJdbcUtils.unregisterChangeNotification;
import static com.pineframework.core.helper.TestEnvironmentConfig.TEST_DB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pineframework.core.helper.JdbcUtils.Oracle;
import io.vavr.control.Try;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.stream.Stream;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.sql.TIMESTAMP;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * {@link OracleJdbcUtilsTest} class provides unit tests for {@link OracleJdbcUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see OracleJdbcUtils
 * @since 2022-01-01
 */
@Disabled("Disabled until providing Oracle DB instance")
@DisplayName("Oracle Jdbc Utils Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OracleJdbcUtilsTest extends AbstractUtilsTest {

  private static OracleConnection connection;

  @BeforeAll
  static void openConnection() {
    connection = (OracleConnection) Oracle.createThinConnection("localhost", "xe", "system", "123456");
    assertNotNull(connection);
    TEST_DB.executeSqlFile(connection, "sql/test-oracle-db-schema-create.sql");
  }

  @AfterAll
  static void closeConnection() {
    TEST_DB.executeSqlFile(connection, "sql/test-oracle-db-schema-delete.sql");
    Try.run(() -> connection.close());
  }

  @Test
  @Order(1)
  @DisplayName("register change notification")
  void registerChangeNotification_IfParametersAreValid_ShouldCreateTableListener() {

    registerChangeNotification(connection,
        event -> {
          if (Objects.isNull(event.getTableChangeDescription()[0].getRowChangeDescription())
              || event.getTableChangeDescription()[0].getRowChangeDescription().length == 0) {
            return;
          }

          RowChangeDescription changes = event.getTableChangeDescription()[0].getRowChangeDescription()[0];
          logger.info("CDC: {}, {}, {}",
              event.getTableChangeDescription()[0].getTableName(),
              changes.getRowOperation(),
              changes.getRowid());
        },
        "test_t");

    TEST_DB.executeSqlFile(connection, "sql/test-oracle-db-init.sql");
    assertTrue(isListenerExist(connection, "system.test_t"));
  }

  @Test
  @Order(2)
  @DisplayName("unregister change notification")
  void unregisterChangeNotification_IfParametersAreValid_ShouldDeleteTableListener() {
    unregisterChangeNotification(connection, "test_t");
    assertFalse(isListenerExist(connection, "system.test_t"));
  }

  @ParameterizedTest(name = "[{index}]: given {0} convert to {1}")
  @ArgumentsSource(OracleType.class)
  @Order(3)
  @DisplayName("convert to java type")
  void convertToJavaType_IfParameterIsNotNull_ShouldReturnJavaType(Object givenValue, Class<?> type, Object expectedValue) {

    //When
    var result = convertToJavaType(givenValue, type);

    //Then
    assertEquals(expectedValue, result);
  }

  static class OracleType implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of("1", Integer.class, 1),
          Arguments.of(new TIMESTAMP("1990-01-01 00:00:00"), LocalDate.class, LocalDate.of(1990, 1, 1)),
          Arguments.of(new TIMESTAMP("1990-01-01 09:15:45"), LocalTime.class, LocalTime.of(9, 15, 45)),
          Arguments.of(new TIMESTAMP("1990-01-01 09:15:45"), LocalDateTime.class, LocalDateTime.of(1990, 1, 1, 9, 15, 45)),
          Arguments.of("1", Boolean.class, true),
          Arguments.of("0", Boolean.class, false),
          Arguments.of("string", String.class, "string"));
    }
  }

}
