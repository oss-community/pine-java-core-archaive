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
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static com.pineframework.core.helper.validator.StringValidator.requireNonEmptyOrNull;
import static java.sql.DriverManager.getConnection;

import io.vavr.control.Try;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Spliterator;
import org.slf4j.Logger;

/**
 * The {@link JdbcUtils} class provides utility functions for JDBC.
 * <ul>
 *   <li>{@link #createSpliterator(ResultSet)}</li>
 *   <li>{@link H2#makeH2ConnectionString(String, String)}</li>
 *   <li>{@link H2#createInMemoryH2Connection(String, String)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class JdbcUtils {

  public static final Logger LOGGER = LogUtils.getLogger(JdbcUtils.class.getSimpleName());

  private JdbcUtils() {

  }

  /**
   * The {@code createSpliterator} method creates spliterator for the
   * {@link ResultSet}.
   *
   * @param rs {@link ResultSet}
   * @return {@link Spliterator}
   * @throws IllegalArgumentException if {@code rs} is {@code null}
   */
  public static Spliterator<ResultSet> createSpliterator(ResultSet rs) {
    requireNonNull(rs, i18n("error.validation.should.not.be.null", i18n("var.name.resultSet")));

    Iterable<ResultSet> iterable = () -> new ResultSetIterator(rs);
    return iterable.spliterator();
  }

  /**
   * The {@link H2} class provides utility functions for H2 database.
   *
   * @author Saman Alishirishahrbabak
   * @version 1.0.0
   * @since 2022-01-01
   */
  public interface H2 {
    String H2_TYPE_MEM = "mem";

    String H2_DEFAULT_USER = "sa";

    String H2_DEFAULT_PASSWD = "";

    /**
     * The {@code createH2Connection} creates a new {@link Connection}.
     *
     * @param name   database name
     * @param passwd password of database username
     * @return H2 {@link Connection}
     * @throws IllegalArgumentException if any parameter is {@code null} or empty
     */
    static Connection createInMemoryH2Connection(String name, String passwd) {
      requireNonEmptyOrNull(name, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.name")));

      return Try.of(() -> {
        DriverManager.registerDriver(new org.h2.Driver());
        return getConnection(makeH2ConnectionString(H2_TYPE_MEM, name), H2_DEFAULT_USER, passwd);
      }).onFailure(exception -> LOGGER.error(exception.getMessage())).get();
    }

    /**
     * The {@code makeH2ConnectionString} method generates H2 connection string.
     *
     * @param type type of storage (i.e. inmemory, file, ...)
     * @param name database name
     * @return connection string
     * @throws IllegalArgumentException if any parameter is {@code null} or empty
     */
    static String makeH2ConnectionString(String type, String name) {
      requireNonEmptyOrNull(name, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.name")));
      requireNonEmptyOrNull(type, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.type")));

      return String.format("jdbc:h2:%s:%s;DB_CLOSE_DELAY=-1", type, name);
    }
  }


  /**
   * The {@link Oracle} class provides utility functions for Oracle database.
   *
   * @author Saman Alishirishahrbabak
   * @version 1.0.0
   * @since 2022-01-01
   */
  public interface Oracle {

    /**
     * The {@code createThinConnection} creates a new {@link Connection}.
     *
     * @param host     database host
     * @param name     database name
     * @param username database username
     * @param passwd   database password
     * @return Oracle {@link Connection}
     * @throws IllegalArgumentException if any parameter is {@code null} or empty
     */
    static Connection createThinConnection(String host, String name, String username, String passwd) {
      requireNonEmptyOrNull(name, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.name")));

      return Try.of(() -> {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        return getConnection(makeThinConnectionString(host, name, username, passwd));
      }).onFailure(exception -> LOGGER.error(exception.getMessage())).get();
    }

    /**
     * The {@code makeThinConnectionString} method generates Oracle connection string.
     *
     * @param host     database host
     * @param name     database name
     * @param username database username
     * @param passwd   database password
     * @return connection string
     * @throws IllegalArgumentException if any parameter is {@code null} or empty
     */
    static String makeThinConnectionString(String host, String name, String username, String passwd) {
      requireNonEmptyOrNull(name, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.name")));

      return String.format("jdbc:oracle:thin:%s/%s@%s:1521:%s", username, passwd, host, name);
    }
  }
}
