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
import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.vavr.control.Try;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import org.slf4j.Logger;

/**
 * The {@link JsonUtils} class provides utility functions for JSON data structures.
 * <ul>
 *   <li>{@link #toMap(String)}</li>
 *   <li>{@link #readArray(String, String, Class)}</li>
 *   <li>{@link #readArray(File, String, Class)}</li>
 *   <li>{@link #getNode(String, String, Class)}</li>
 *   <li>{@link #getNode(File, String, Class)}</li>
 *   <li>{@link #toType(String, Class)}</li>
 *   <li>{@link #toJsonObject(File)}</li>
 *   <li>{@link #readPrimitive(String, String, Class)}</li>
 *   <li>{@link #contain(String, String)}</li>
 *   <li>{@link #isConvertible(String, Class)}</li>
 *   <li>{@link #toStringList(JsonArray)}</li>
 *   <li>{@link #toJsonString(Object)}</li>
 *   <li>{@link #toJsonString(Object, Class)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class JsonUtils {

  private static final Logger LOGGER = LogUtils.getLogger(JsonUtils.class.getSimpleName());

  private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder().disable(MapperFeature.DEFAULT_VIEW_INCLUSION).build();

  private JsonUtils() {
  }

  /**
   * The {@code toMap} method converts a JSON string to the {@link Map}.
   *
   * @param jsonString JSON as a string
   * @return {@link Map}
   * @throws IllegalArgumentException when {@code jsonString} is {@code null} or empty
   */
  public static Map<String, Object> toMap(String jsonString) {
    requireNonEmptyOrNull(jsonString, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.json")));

    return Try.of(() -> OBJECT_MAPPER.readValue(jsonString, new TypeReference<Map<String, Object>>() {
    })).get();
  }

  /**
   * The {@code readArray} method returns the array from JSON string.
   *
   * @param jsonString JSON as a string
   * @param fieldName  name of array field
   * @param type       class object of {@link T}
   * @param <T>        type of array
   * @return {@link T}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T> T readArray(String jsonString, String fieldName, Class<T> type) {
    requireNonEmptyOrNull(jsonString, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.json")));
    requireNonEmptyOrNull(fieldName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.fieldName")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    return Try.of(() -> OBJECT_MAPPER.convertValue(OBJECT_MAPPER.readTree(jsonString).withArray(fieldName), type)).get();
  }

  /**
   * The {@code readArray} method returns the array from JSON file.
   *
   * @param jsonFile  JSON file
   * @param fieldName name of array field
   * @param type      class object of {@link T}
   * @param <T>       type of array
   * @return {@link T}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T> T readArray(File jsonFile, String fieldName, Class<T> type) {
    requireNonNull(jsonFile, i18n("error.validation.should.not.be.null", i18n("var.name.jsonFile")));
    requireNonEmptyOrNull(fieldName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.fieldName")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    return Try.of(() -> OBJECT_MAPPER.convertValue(OBJECT_MAPPER.readTree(jsonFile).withArray(fieldName), type)).get();
  }

  /**
   * The {@code getNode} method returns the JSON node from JSON string.
   *
   * @param jsonString JSON as a string
   * @param nodeName   name of JSON node
   * @param type       class object of {@link T}
   * @param <T>        type of JSON node
   * @return {@link T}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T> T getNode(String jsonString, String nodeName, Class<T> type) {
    requireNonEmptyOrNull(jsonString, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.jsonString")));
    requireNonEmptyOrNull(nodeName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.nodeName")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    return Try.of(() -> OBJECT_MAPPER.convertValue(OBJECT_MAPPER.readTree(jsonString).with(nodeName), type)).get();
  }

  /**
   * The {@code getNode} method returns a JSON node from JSON file.
   *
   * @param jsonFile JSON file
   * @param nodeName name of JSON node
   * @param type     class object of {@link T}
   * @param <T>      type of JSON node
   * @return {@link T}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T> T getNode(File jsonFile, String nodeName, Class<T> type) {
    requireNonNull(jsonFile, i18n("error.validation.should.not.be.null", i18n("var.name.jsonFile")));
    requireNonEmptyOrNull(nodeName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.nodeName")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    return Try.of(() -> OBJECT_MAPPER.convertValue(OBJECT_MAPPER.readTree(jsonFile).with(nodeName), type)).get();
  }

  /**
   * The {@code toType} method converts a JSON string to the java type.
   *
   * @param jsonString JSON as a string
   * @param type       class object of {@link T}
   * @param <T>        java type
   * @return {@link T}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T> T toType(String jsonString, Class<T> type) {
    requireNonEmptyOrNull(jsonString, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.jsonString")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    return Try.of(() -> OBJECT_MAPPER.readValue(jsonString, type)).get();
  }

  /**
   * The  {@code toJsonObject} method converts a JSON file to JSON object.
   *
   * @param file {@link File}, json file
   * @return {@link JsonObject}
   * @throws NullPointerException if {@code file} is {@code null}
   */
  public static JsonObject toJsonObject(File file) {
    requireNonNull(file, i18n("error.validation.should.not.be.null", i18n("var.name.file")));

    var inputStream = Try.of(() -> new FileInputStream(file)).get();
    var reader = Json.createReader(inputStream);
    var object = reader.readObject();

    reader.close();
    Try.run(inputStream::close);

    return object;
  }

  /**
   * The {@code readPrimitive} method returns the primitive field as a primitive wrapper.
   *
   * @param jsonString JSON as a string
   * @param fieldName  name of JSON field
   * @param type       class object of {@link T}
   * @param <T>        type of JSON field
   * @return primitive wrapper
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static <T> T readPrimitive(String jsonString, String fieldName, Class<T> type) {
    requireNonEmptyOrNull(jsonString, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.jsonString")));
    requireNonEmptyOrNull(fieldName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.fieldName")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    JsonNode jsonNode = Try.of(() -> OBJECT_MAPPER.readValue(jsonString, JsonNode.class)).get();
    return ReflectionUtils.toJavaBasicType(jsonNode.get(fieldName), type);
  }

  /**
   * The {@code contain} method checks is a JSON string included a field.
   * It returns {@code true} if JSON string is included the field otherwise
   * returns {@code false}.
   *
   * @param jsonString JSON as a string
   * @param fieldName  name of JSON field
   * @return {@code boolean}.
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static boolean contain(String jsonString, String fieldName) {
    requireNonEmptyOrNull(jsonString, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.jsonString")));
    requireNonEmptyOrNull(fieldName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.fieldName")));

    return Try.of(() -> OBJECT_MAPPER.readValue(jsonString, JsonNode.class)).get().has(fieldName);
  }

  /**
   * The {@code isConvertible} method checks a JSON string is able to convert to the java type.
   *
   * @param jsonString JSON as a string
   * @param type       java type
   * @return {@code boolean}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static boolean isConvertible(String jsonString, Class<?> type) {
    requireNonEmptyOrNull(jsonString, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.jsonString")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    return Try.of(() -> OBJECT_MAPPER.readValue(jsonString, type)).onFailure(e -> LOGGER.error(e.getMessage())).isSuccess();
  }

  /**
   * The {@code toStringList} method converts {@link JsonArray} to the {@link List}{@literal <}{@link String}{@literal >}.
   *
   * @param array {@link JsonArray}
   * @return {@link List}
   * @throws NullPointerException if {@code array} is {@code null}
   */
  public static List<String> toStringList(JsonArray array) {
    requireNonNull(array, i18n("error.validation.should.not.be.null", i18n("var.name.array")));

    return array.stream().map(e -> ((JsonString) e).getString()).collect(toList());
  }

  /**
   * The {@code toJsonString} method converts a java object to the JSON as a string.
   *
   * @param obj {@link Object}, java object
   * @return JSON as a string
   * @throws NullPointerException if {@code obj} is {@code null}
   */
  public static String toJsonString(Object obj) {
    requireNonNull(obj, i18n("error.validation.should.not.be.null", i18n("var.name.object")));


    return Try.of(() -> OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj)).get();
  }

  /**
   * The {@code toJsonString} method converts a java object to the JSON string with specific view.
   *
   * @param obj  {@link Object}, java object
   * @param view {@link com.fasterxml.jackson.annotation.JsonView}, JSON view
   * @return JSON as a string
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static String toJsonString(Object obj, Class<?> view) {
    requireNonNull(obj, i18n("error.validation.should.not.be.null", i18n("var.name.object")));
    requireNonNull(view, i18n("error.validation.should.not.be.null", i18n("var.name.view")));

    return Try.of(() -> OBJECT_MAPPER.writerWithView(view).writeValueAsString(obj)).get();
  }
}
