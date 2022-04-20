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

import static com.pineframework.core.helper.FileUtils.createFile;
import static com.pineframework.core.helper.FileUtils.getProperties;
import static com.pineframework.core.helper.FileUtils.readContentAsByteArray;
import static com.pineframework.core.helper.FileUtils.readContentAsString;
import static com.pineframework.core.helper.FileUtils.readParts;
import static com.pineframework.core.helper.FileUtils.toFullPath;
import static com.pineframework.core.helper.FileUtils.toPackage;
import static com.pineframework.core.helper.FileUtils.toPath;
import static com.pineframework.core.helper.FileUtils.toUri;
import static com.pineframework.core.helper.FileUtils.toUrl;
import static com.pineframework.core.helper.FileUtils.walkThrowPackage;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link FileUtilsTest} class provides unit tests for {@link FileUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see FileUtils
 * @since 2022-01-01
 */
@DisplayName("File Utils Tests")
class FileUtilsTest extends AbstractUtilsTest {

  @Test
  @DisplayName("converting a package name to the string path")
  void toPath_IfPackageNameIsValid_ShouldReturnStringPath() {
    //Given
    var givenPackageName = "com.pineframework.core";

    //Expectation
    var expectedPath = "com" + File.separatorChar + "pineframework" + File.separatorChar + "core";

    //When
    var result = toPath(givenPackageName);

    //Then
    assertNotNull(result);
    assertEquals(expectedPath, result.toString());
  }

  @Test
  @DisplayName("converting a path to the package name")
  void toPackage_IfPathIsValid_ShouldReturnPackageName() {
    //Given
    var givenPath = Paths.get("com" + File.separatorChar + "pineframework" + File.separatorChar + "core");

    //Expectation
    var expectedPackageName = "com.pineframework.core";

    //When
    var result = toPackage(givenPath);

    //Then
    assertNotNull(result);
    assertEquals(expectedPackageName, result);
  }

  @Test
  @DisplayName("converting a path to the URL")
  void toUrl_IfPathIsValid_ShouldReturnUrl() {
    //Given
    var givenPath = Paths.get("com/pineframework/core");

    //Expectation
    var expectedProtocol = "file";
    var expectedClass = URL.class;

    //When
    var result = toUrl(givenPath);

    //Then
    assertNotNull(result);
    assertEquals(expectedClass, result.getClass());
    assertEquals(expectedProtocol, result.getProtocol());
  }

  @Test
  @DisplayName("converting a path to the URI")
  void toUri_IfPathIsValid_ShouldReturnUri() {
    //Given
    var givenPath = Paths.get("com/pineframework/core");

    //Expectation
    var expectedProtocol = "file";
    var expectedClass = URI.class;

    //When
    var result = toUri(givenPath);

    //Then
    assertNotNull(result);
    assertEquals(expectedClass, result.getClass());
    assertEquals(expectedProtocol, result.getScheme());
  }

  @Test
  @DisplayName("getting system path for a package")
  void toFullPath_IfPathIsValid_ShouldReturnPath() {
    //Given
    var givenPath = toPath("com.pineframework.core");

    //Expectation
    var expectedClass = Path.class;

    //When
    var result = toFullPath(givenPath);

    //Then
    assertNotNull(result);
    assertEquals(expectedClass, result.getClass().getInterfaces()[0]);
  }

  @Test
  @DisplayName("getting all sub directories")
  void walkThrowPackage_IfPathIsValid_ShouldReturnAllSubDirectories() {
    //Given
    var givenPath = Paths.get("com/pineframework/core");

    //When
    var result = walkThrowPackage(givenPath);

    //Then
    assertTrue(result.findAny().isPresent());
  }

  @Test
  @DisplayName("reading an empty file as a byte array")
  void readContentAsByteArray_IfFileIsEmpty_ShouldReturnAnEmptyByteArray() {
    //Given
    var givenPath = toFullPath(Paths.get("txt/empty.txt"));

    //Expectation
    var expectedArraySize = 0;

    //When
    var result = readContentAsByteArray(givenPath);

    //Then
    assertEquals(expectedArraySize, result.length);
  }

  @Test
  @DisplayName("reading a file as a byte array")
  void readContentAsByteArray_IfFileHasContent_ShouldReturnNotEmptyByteArray() {
    //Given
    var givenPath = toFullPath(Paths.get("txt/greeting.txt"));

    //Expectation
    var expectedContent = "Hello\nGoodbye".getBytes(UTF_8);

    //When
    var result = readContentAsByteArray(givenPath);

    //Then
    assertNotNull(result);
    assertTrue(result.length > 0);
    assertArrayEquals(expectedContent, result);
  }

  @Test
  @DisplayName("reading an empty file as a string")
  void readContentAsString_IfFileIsEmpty_ShouldReturnAnEmptyString() {
    //Given
    var givenPath = toFullPath(Paths.get("txt/empty.txt"));

    //When
    var result = readContentAsString(givenPath);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("reading a file with content as a string")
  void readContentAsString_IfFileIsNotEmpty_ShouldReturnString() {
    //Given
    var givenPath = toFullPath(Paths.get("txt/greeting.txt"));

    //Expectation
    var expectedContent = "Hello\nGoodbye";

    //When
    var result = readContentAsString(givenPath);

    //Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(expectedContent, result);
  }

  @Test
  @DisplayName("creating a file")
  void createFile_IfParametersAreValid_ShouldReturnOutputStream() {
    //Given
    var givenPath = Paths.get("./target/creat-any-file.txt");
    var givenContent = "Hello".getBytes();

    //When
    var result = createFile(givenPath, givenContent);

    //Then
    assertNotNull(result);
  }

  @Test
  @DisplayName("reading file lines as an array")
  void readParts_IfParametersAreValid_ShouldReturnArray() {
    //Given
    var givenPath = toFullPath(Paths.get("txt/greeting.txt"));
    var givenSeparator = "\n";

    //Expectation
    var expectedContent = new String[] {"Hello", "Goodbye"};

    //When
    var result = readParts(givenPath, givenSeparator);

    //Then
    assertNotNull(result);
    assertTrue(result.length > 0);
    assertArrayEquals(expectedContent, result);
  }

  @Test
  @DisplayName("getting resource properties")
  void getProperties_IfFilePathIsValid_ShouldReturnProperties() {
    //Given
    var givenPath = Paths.get("test.properties");

    //When
    var result = getProperties(givenPath);

    //Then
    assertNotNull(result);
    assertTrue(result.containsKey("key"));
    assertTrue(result.containsValue("value"));
  }

}
