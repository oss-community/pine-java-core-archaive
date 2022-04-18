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

import static com.pineframework.core.helper.FileUtils.toFullPath;
import static com.pineframework.core.helper.TestEnvironmentConfig.TEST_DB;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.sql.Connection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link SqlFileHelperTest} class provides unit tests for {@link SqlFileHelper}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see SqlFileHelper
 * @since 2022-01-01
 */
@DisplayName("SQL File Executor Tests")
class SqlFileHelperTest extends AbstractUtilsTest {

  private Connection connection;

  private SqlFileHelper underTest;

  @BeforeEach
  void init() {
    connection = TEST_DB.createH2Connection(SqlFileHelperTest.class.getSimpleName());
    underTest = SqlFileHelper.create(connection);
    assertNotNull(underTest);
  }

  @AfterEach
  void close() {
    underTest = null;
    TEST_DB.close(connection);
  }

  @Test
  @DisplayName("executing the valid SQL file")
  void ifSqlFileIsValid_ShouldReturnTrue() {
    //Given
    var givenSqlFile = toFullPath(Paths.get("sql/test-db.sql"));

    //When
    var result = underTest.execute(givenSqlFile);

    //Then
    assertTrue(result);
    logInfo("execute the SQL file");
  }

  @Test
  @DisplayName("executing the invalid SQL file")
  void ifSqlFileIsNotValid_ShouldReturnFalse() {
    //Given
    var givenSqlFile = toFullPath(Paths.get("sql/test-failed-db.sql"));

    //When
    var result = underTest.execute(givenSqlFile);

    //Then
    assertFalse(result);
    logInfo("catch SQL exception related to execution of incorrect SQL file");
  }
}
