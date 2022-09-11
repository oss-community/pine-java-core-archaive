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
import static java.lang.Thread.currentThread;
import static java.nio.charset.StandardCharsets.UTF_8;

import io.vavr.control.Try;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * The {@link FileUtils} class provides utility functions for files.
 * <ul>
 *   <li>{@link #toPath(String)}</li>
 *   <li>{@link #toPackage(Path)}</li>
 *   <li>{@link #toUrl(Path)}</li>
 *   <li>{@link #toUri(Path)}</li>
 *   <li>{@link #toFullPath(Path)}</li>
 *   <li>{@link #walkThrowPackage(Path)}</li>
 *   <li>{@link #readContentAsByteArray(Path)}</li>
 *   <li>{@link #readContentAsString(Path)}</li>
 *   <li>{@link #readParts(Path, String)}</li>
 *   <li>{@link #getProperties(Path)}</li>
 *   <li>{@link #createFile(Path, byte[])}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class FileUtils {

  private FileUtils() {
  }

  /**
   * The {@code toPath} method replaces dot with slash and return path of file as a {@link String}.
   *
   * @param packageName the package name
   * @return path
   * @throws IllegalArgumentException if {@code packageName} is {@code null} or empty
   */
  public static Path toPath(String packageName) {
    requireNonEmptyOrNull(packageName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.packageName")));

    return Paths.get(packageName.replace('.', File.separatorChar));
  }

  /**
   * The {@code toPackage} method converts a path to the package name.
   *
   * @param path {@link Path}
   * @return package name
   * @throws NullPointerException if the {@code path} is null
   */
  public static String toPackage(Path path) {
    requireNonNull(path, i18n("error.validation.should.not.be.null", i18n("var.name.path")));

    return path.toString().replace(File.separatorChar, '.');
  }

  /**
   * The {@code toUrl} method converts a path to the {@link URL}.
   *
   * @param path {@link Path}
   * @return {@link URL}
   * @throws IllegalArgumentException if {@code path} is {@code null} or empty
   * @see #toPath(String)
   */
  public static URL toUrl(Path path) {
    requireNonNull(path, i18n("error.validation.should.not.be.null", i18n("var.name.path")));

    return currentThread().getContextClassLoader().getResource(path.toString());
  }

  /**
   * The {@code toUri} method converts a path to the {@link URI}.
   *
   * @param path {@link Path}
   * @return {@link URL}
   * @throws IllegalArgumentException if {@code path} is {@code null} or empty
   * @see #toPath(String)
   */
  public static URI toUri(Path path) {
    requireNonNull(path, i18n("error.validation.should.not.be.null", i18n("var.name.path")));

    return Try.of(() -> toUrl(path).toURI()).get();
  }

  /**
   * The {@code toFullPath} method converts a path to the {@link Path}.
   * <p>
   * Also it uses to returning file path under {@literal resources} folder.
   * </p>
   *
   * @param path {@link Path}
   * @return {@link Path}
   * @throws IllegalArgumentException if {@code path} is {@code null} or empty
   * @see #toPath(String)
   */
  public static Path toFullPath(Path path) {
    requireNonNull(path, i18n("error.validation.should.not.be.null", i18n("var.name.path")));

    return Try.of(() -> Paths.get(toUri(path))).get();
  }

  /**
   * The {@code walkThrowPackage} method returns all classes and sub packages under the directory as a {@link Stream}.
   *
   * @param path {@link Path}
   * @return {@link Stream}
   * @throws IllegalArgumentException if {@code path} is {@code null} or empty
   * @see #toPath(String)
   */
  public static Stream<String> walkThrowPackage(Path path) {
    requireNonNull(path, i18n("error.validation.should.not.be.null", i18n("var.name.path")));

    return Try.of(() -> Files.walk(toFullPath(path)).map(FileUtils::toPackage)).get();
  }

  /**
   * The {@code readContentAsByteArray} method reads content of a file as a byte array.
   *
   * @param path {@link Path}
   * @return {@code byte}[]
   * @throws NullPointerException if {@code path} is {@code null}
   */
  public static byte[] readContentAsByteArray(Path path) {
    requireNonNull(path, i18n("error.validation.should.not.be.null", i18n("var.name.path")));

    return Try.of(() -> Files.readString(path).replace("\r", "").getBytes(UTF_8)).get();
  }

  /**
   * The {@code readContentAsString} method reads content of a file as a string.
   *
   * @param path {@link Path}
   * @return content of file
   * @throws NullPointerException if {@code path} is {@code null}
   */
  public static String readContentAsString(Path path) {
    requireNonNull(path, i18n("error.validation.should.not.be.null", i18n("var.name.path")));

    return Try.of(() -> Files.readString(path, UTF_8).replace("\r", "")).get();
  }

  /**
   * The {@code readLines} method reads all separated parts of a file as an array.
   *
   * @param path      {@link Path}
   * @param separator parts separator
   * @return {@link String}[]
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static String[] readParts(Path path, String separator) {
    requireNonNull(path, i18n("error.validation.should.not.be.null", i18n("var.name.path")));
    requireNonEmptyOrNull(separator, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.separator")));

    return Try.of(() -> Files.readString(path, UTF_8).replace("\r", "").split(separator)).get();
  }

  /**
   * The {@code getProperties} method returns properties file under {@literal resources} folder.
   *
   * @param path {@link Path}, <example>path/to/file-name.properties</example>
   * @return {@link Properties}
   * @throws IllegalArgumentException if {@code path} is {@code null} or empty, or file does not exist
   */
  public static Properties getProperties(Path path) {
    requireNonNull(path, i18n("error.validation.should.not.be.null", i18n("var.name.path")));

    var inputStream = Try.of(() -> currentThread().getContextClassLoader().getResourceAsStream(path.toString()))
        .getOrElseThrow(() -> {
          throw new IllegalArgumentException(i18n("error.validation.is.wrong", i18n("var.name.path")));
        });

    var properties = new Properties();
    Try.run(() -> properties.load(inputStream));
    return properties;
  }

  /**
   * The {@code createFile} method creates a new file and writes any content to the file.
   *
   * @param path    {@link Path}
   * @param content content of file
   * @return {@link FileOutputStream}
   * @throws NullPointerException if any parameter is {@code null}
   */
  public static FileOutputStream createFile(Path path, byte[] content) {
    requireNonNull(path, i18n("error.validation.should.not.be.null", i18n("var.name.path")));
    requireNonNull(content, i18n("error.validation.should.not.be.null", i18n("var.name.content")));

    return Try.withResources(() -> new FileOutputStream(path.toFile())).of(output -> {
      output.write(content);
      return output;
    }).get();
  }
}
