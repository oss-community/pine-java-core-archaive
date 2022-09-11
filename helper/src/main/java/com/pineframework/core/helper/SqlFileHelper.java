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

import static com.pineframework.core.helper.FileUtils.readParts;
import static com.pineframework.core.helper.I18nUtils.i18n;
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static io.vavr.control.Try.withResources;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.stream;

import io.vavr.control.Try;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import org.slf4j.Logger;

/**
 * The {@link SqlFileHelper} class is a helper to execute all queries in side of an SQL file.
 * <p>
 * The queries should be separated by a <i>semicolon ({@code ;})</i>.
 * </p>
 *
 * @param connection {@link Connection}
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public record SqlFileHelper(Connection connection) {
  public static final Logger LOGGER = LogUtils.getLogger(SqlFileHelper.class.getSimpleName());

  private static final String SEPARATOR = ";";

  /**
   * This the constructor.
   *
   * @param connection {@link Connection}
   * @throws IllegalArgumentException if the {@code connection} is {@code null}
   */
  public SqlFileHelper {
    requireNonNull(connection, i18n("error.validation.should.not.be.null", i18n("var.name.connection")));
  }

  /**
   * The {@code create} method is static factory method to
   * create a new instance of {@link SqlFileHelper}.
   *
   * @param connection {@link Connection}
   * @return {@link SqlFileHelper}
   * @throws NullPointerException if {@code connection} is {@code null}
   */
  public static SqlFileHelper create(Connection connection) {
    return new SqlFileHelper(connection);
  }

  /**
   * The {@code execute} method executes all queries and, at
   * the end of all execution it will commit the transactions.
   * <p>
   * It returns {@code true} if all queries execute successful otherwise it returns {@code false}.
   * </p>
   *
   * @param sqlFile {@link Path}
   * @return {@link Boolean}
   * @throws NullPointerException if {@code sqlFile} is {@code null}
   */
  public Boolean execute(Path sqlFile) {
    requireNonNull(sqlFile, i18n("error.validation.should.not.be.null", i18n("var.name.sqlFile")));

    return withResources(connection::createStatement)
        .of(statement -> execute(sqlFile, statement))
        .get();
  }

  /**
   * The {@code executeQueries} method executes all SQLs written in the file.
   * <p>
   * It returns {@code true} if all queries execute successful otherwise it returns {@code false}.
   * </p>
   *
   * @param sqlFile   {@link Path}
   * @param statement {@link Statement}
   * @return {@code boolean}
   * @throws NullPointerException if any parameter is {@code null}
   */
  private boolean execute(Path sqlFile, Statement statement) {
    requireNonNull(sqlFile, i18n("error.validation.should.not.be.null", i18n("var.name.sqlFile")));
    requireNonNull(statement, i18n("error.validation.should.not.be.null", i18n("var.name.statement")));

    return stream(readParts(sqlFile, SEPARATOR))
        .map(query -> isSuccess(statement, query))
        .allMatch(result -> result == TRUE);
  }

  /**
   * The {@code isSuccess} methods check whether query is executed successful or no.
   *
   * @param statement {@link Statement}
   * @param query     SQL
   * @return {@code boolean}
   */
  private boolean isSuccess(Statement statement, String query) {
    return Try.of(() -> statement.execute(query.trim()))
        .onFailure(e -> LOGGER.error(e.getMessage()))
        .isSuccess();
  }

}
