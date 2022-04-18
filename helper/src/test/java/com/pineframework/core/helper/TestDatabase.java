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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pineframework.core.helper.JdbcUtils.H2;
import io.vavr.control.Try;
import java.nio.file.Paths;
import java.sql.Connection;
import org.slf4j.Logger;

/**
 * {@link TestDatabase} class provides test utilities for database.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public class TestDatabase {

  private final Logger logger = LogUtils.getLogger(TestDatabase.class.getSimpleName());

  public TestDatabase() {

  }

  /**
   * The  {@code createH2Connection} method creates connection to H2 database.
   *
   * @param dbName name of database
   * @return {@link Connection}
   */
  public Connection createH2Connection(String dbName) {
    var connection = H2.createInMemoryH2Connection(dbName, H2.H2_DEFAULT_PASSWD);
    assertNotNull(connection);
    logger.info("create database(H2) connection");

    return connection;
  }

  /**
   * The {@code closeConnection} method closes the connection.
   *
   * @param connection {@link Connection}
   */
  public void close(Connection connection) {
    Try.run(connection::close);
    logger.info("close database connection");
  }

  /**
   * The {@code executeSqlFile} method executes sql file.
   *
   * @param connection {@link Connection}
   * @param filePath   path of SQL file
   */
  public void executeSqlFile(Connection connection, String filePath) {
    var sqlFileHelper = SqlFileHelper.create(connection);
    var result = sqlFileHelper.execute(toFullPath(Paths.get(filePath)));
    assertTrue(result);
    logger.info("execute SQL file");
  }

}
