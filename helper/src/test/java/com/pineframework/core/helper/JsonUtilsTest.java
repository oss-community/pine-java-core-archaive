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
import static com.pineframework.core.helper.JsonUtils.contain;
import static com.pineframework.core.helper.JsonUtils.getNode;
import static com.pineframework.core.helper.JsonUtils.isConvertible;
import static com.pineframework.core.helper.JsonUtils.readArray;
import static com.pineframework.core.helper.JsonUtils.readPrimitive;
import static com.pineframework.core.helper.JsonUtils.toJsonObject;
import static com.pineframework.core.helper.JsonUtils.toJsonString;
import static com.pineframework.core.helper.JsonUtils.toMap;
import static com.pineframework.core.helper.JsonUtils.toStringList;
import static com.pineframework.core.helper.JsonUtils.toType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link JsonUtilsTest} class provides unit tests for {@link JsonUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see JsonUtils
 * @since 2022-01-01
 */
@SuppressWarnings({"UnnecessaryLocalVariable", "unchecked"})
@DisplayName("JSON Utils Tests")
class JsonUtilsTest {

  private static final File JSON_FILE = toFullPath(Paths.get("json/personal-information.json")).toFile();
  private static final String JSON_STRING = """
      {
        "name" : "Saman Alishirishahrbabak",
        "age" : 35,
        "languages" : [ "C", "C++", "Java", "Python", "Go", "JS" ],
        "address" : {
          "city" : "Tehran",
          "zipCode" : 12345
        }
      }""";
  private static final JsonObject JSON_OBJECT;
  private static final JsonDto JSON_DTO;

  static {
    JsonBuilderFactory factory = Json.createBuilderFactory(new HashMap<>());
    JSON_OBJECT = factory.createObjectBuilder()
        .add("name", "Saman Alishirishahrbabak")
        .add("age", 35)
        .add("address", factory.createObjectBuilder()
            .add("city", "Tehran")
            .add("zipCode", "12345"))
        .add("languages", factory.createArrayBuilder()
            .add("C")
            .add("C++")
            .add("Java")
            .add("Python")
            .add("Go")
            .add("JS"))
        .build();

    JSON_DTO = new JsonDto();
    JSON_DTO.name = "Saman Alishirishahrbabak";
    JSON_DTO.age = 35;
    JSON_DTO.languages = new String[] {"C", "C++", "Java", "Python", "Go", "JS"};
    JSON_DTO.address = new AddressDto("Tehran", 12345);
  }

  @Test
  @DisplayName("converting a JSON String to the map")
  void toMap_IfParameterIsValid_ShouldReturnMap() {
    //Given
    var givenJsonString = JSON_STRING;

    //Expectation
    var expectedDto = JSON_DTO;

    //When
    var result = toMap(givenJsonString);

    //Then
    assertNotNull(result);
    assertEquals(expectedDto.name, result.get("name"));
    assertEquals(expectedDto.age, Integer.parseInt(String.valueOf(result.get("age"))));
    assertThat((ArrayList<String>) result.get("languages")).containsOnly(expectedDto.languages);

    var address = (Map<String, Object>) result.get("address");
    assertEquals(expectedDto.address.city, address.get("city"));
    assertEquals(expectedDto.address.zipCode, Integer.parseInt(String.valueOf(address.get("zipCode"))));
  }

  @Test
  @DisplayName("reading a JSON array from JSON String")
  void readArray_JsonString_IfParametersAreValid_ShouldReturnArray() {
    //Given
    var givenJsonString = JSON_STRING;
    var givenFieldName = "languages";
    var givenArrayType = String[].class;

    //Expectation
    var expectedLanguage = JSON_DTO.languages;

    //When
    var result = readArray(givenJsonString, givenFieldName, givenArrayType);

    //Then
    assertThat(result).containsOnly(expectedLanguage);
  }

  @Test
  @DisplayName("reading a JSON array from JSON file")
  void readArray_JsonFile_IfParametersAreValid_ShouldReturnArray() {
    //Given
    var givenJsonFile = JSON_FILE;
    var givenFieldName = "languages";
    var givenArrayType = String[].class;

    //Expectation
    var expectedLanguage = JSON_DTO.languages;

    //When
    var result = readArray(givenJsonFile, givenFieldName, givenArrayType);

    //Then
    assertThat(result).containsOnly(expectedLanguage);
  }

  @Test
  @DisplayName("getting a JSON node from JSON string")
  void getNode_JsonString_IfParametersAreValid_ShouldReturnJsonNode() {
    //Given
    var givenFieldName = "address";
    var givenJsonString = JSON_STRING;

    //Expectation
    var expectedDto = JSON_DTO;

    //When
    var result = getNode(givenJsonString, givenFieldName, AddressDto.class);

    //Then
    assertNotNull(result);
    assertEquals(expectedDto.address.city, result.city);
    assertEquals(expectedDto.address.zipCode, result.zipCode);
  }

  @Test
  @DisplayName("getting a JSON node from JSON file")
  void getNode_JsonFile_IfParametersAreValid_ShouldReturnJsonNode() {
    //Given
    var givenJsonFile = JSON_FILE;
    var givenFieldName = "address";
    var givenType = AddressDto.class;

    //Expectation
    var expectedDto = JSON_DTO;

    //When
    var result = getNode(givenJsonFile, givenFieldName, givenType);

    //Then
    assertNotNull(result);
    assertEquals(result.city, expectedDto.address.city);
    assertEquals(result.zipCode, expectedDto.address.zipCode);
  }

  @Test
  @DisplayName("converting a JSON string to the java type")
  void toType_IfParametersAreValid_ShouldReturnJavaType() {
    //Given
    var givenJsonString = JSON_STRING;
    var givenType = JsonDto.class;

    //Expectation
    var expectedJson = JSON_DTO;

    //When
    var result = toType(givenJsonString, givenType);

    //Then
    assertNotNull(result);
    assertEquals(expectedJson.name, result.name);
    assertEquals(expectedJson.age, result.age);
    assertThat(result.languages).containsOnly(expectedJson.languages);
    assertEquals(expectedJson.address.city, result.address.city);
    assertEquals(expectedJson.address.zipCode, result.address.zipCode);
  }

  @Test
  @DisplayName("converting a JSON file to the JSON object")
  void toJsonObject_IfParameterIsValid_ShouldReturnJsonObject() {
    //Given
    var givenJsonFile = JSON_FILE;

    //Expectation
    var expectedDto = JSON_DTO;

    //When
    var result = toJsonObject(givenJsonFile);

    //Then
    assertNotNull(result);
    assertEquals(expectedDto.name, result.getString("name"));
    assertEquals(expectedDto.age, result.getInt("age"));
    assertThat(result.getJsonArray("languages")
        .stream()
        .map(e -> ((JsonString) e).getString())
        .toArray(String[]::new))
        .containsOnly(expectedDto.languages);
    assertEquals(expectedDto.address.city, result.getJsonObject("address").getString("city"));
    assertEquals(expectedDto.address.zipCode, result.getJsonObject("address").getInt("zipCode"));
  }

  @Test
  @DisplayName("getting a primitive field from JSON string")
  void readPrimitive_IfParametersAreValid_ShouldReturnPrimitiveWrapper() {
    //Given
    var givenJsonString = JSON_STRING;
    var givenFieldName = "age";
    var givenFieldType = Integer.class;

    //Expectation
    var expectedResultType = Integer.class;
    var expectedDto = JSON_DTO;

    //When
    var result = readPrimitive(givenJsonString, givenFieldName, givenFieldType);

    //Then
    assertNotNull(result);
    assertEquals(expectedResultType, result.getClass());
    assertEquals(expectedDto.age, result);
  }

  @Test
  @DisplayName("checking a JSON string contains the field")
  void contain_IfJsonContainsField_ShouldReturnTrue() {
    //Given
    var givenJsonString = JSON_STRING;
    var givenFieldName = "age";

    //When
    var result = contain(givenJsonString, givenFieldName);

    //Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking a JSON string does not contain a field")
  void contain_IfJsonDoesNotContainField_ShouldReturnFalse() {
    //Given
    var givenJsonString = JSON_STRING;
    var givenFieldName = "notAge";

    //When
    var result = contain(givenJsonString, givenFieldName);

    //Then
    assertFalse(result);
  }

  @Test
  @DisplayName("checking a JSON string is convertible")
  void gisConvertible_IfJsonStringIsConvertible_ShouldReturnTrue() {
    //Given
    var givenJsonString = JSON_STRING;
    var givenType = JsonDto.class;

    //When
    var result = isConvertible(givenJsonString, givenType);

    //Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking a JSON string is not convertible")
  void gisConvertible_IfJsonStringIsNotConvertible_ShouldReturnFalse() {
    //Given
    var givenJsonString = JSON_STRING;
    var givenType = WrongType.class;

    //When
    var result = isConvertible(givenJsonString, givenType);

    //Then
    assertFalse(result);
  }

  @Test
  @DisplayName("converting a JSON array to the list of string")
  void toStringList_IfParameterIsValid_ShouldReturnList() {
    //Given
    var givenJsonObject = JSON_OBJECT;

    //Expectation
    var expectedDto = JSON_DTO;

    //When
    var result = toStringList(givenJsonObject.getJsonArray("languages"));

    //Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertThat(result).containsOnly(expectedDto.languages);
  }

  @Test
  @DisplayName("converting a JSON object to the JSON string")
  void toJsonString_JavaType_IfParameterIsValid_ShouldReturnJsonString() {
    //Given
    var givenJsonDto = JSON_DTO;

    //Expectation
    var os = System.getProperty("os.name").toLowerCase();
    var expectedJsonString = os.contains("win") ? JSON_STRING.replace("\n", "\r\n") : JSON_STRING;

    //When
    var result = toJsonString(givenJsonDto);

    //Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(expectedJsonString, result);
  }

  @Test
  @DisplayName("converting a JSON object to a JSON string")
  void toJsonString_ViewClass_IfParametersAreValid_ShouldReturnJsonString() {
    //Given
    var givenJsonDto = JSON_DTO;

    //When
    var result = toJsonString(givenJsonDto, Required.class);

    //Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertTrue(contain(result, "name"));
    assertFalse(contain(result, "age"));
    assertFalse(contain(result, "languages"));
    assertFalse(contain(result, "address"));
  }

  interface Required {
  }

  static class JsonDto {
    @JsonView(Required.class)
    @JsonProperty("name")
    String name;
    @JsonProperty("age")
    int age;
    @JsonProperty("languages")
    String[] languages;
    @JsonProperty("address")
    AddressDto address;
  }

  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
  static class AddressDto {
    String city;
    int zipCode;

    @JsonCreator
    public AddressDto(@JsonProperty("city") String city, @JsonProperty("zipCode") int zipCode) {
      this.city = city;
      this.zipCode = zipCode;
    }
  }

  static class WrongType {
  }
}
