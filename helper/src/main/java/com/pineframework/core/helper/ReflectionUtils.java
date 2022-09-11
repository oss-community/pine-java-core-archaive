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
import static com.pineframework.core.helper.validator.ArrayValidator.requireElement;
import static com.pineframework.core.helper.validator.DateTimeValidator.isDate;
import static com.pineframework.core.helper.validator.DateTimeValidator.isDateTime;
import static com.pineframework.core.helper.validator.DateTimeValidator.isTime;
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static com.pineframework.core.helper.validator.StringValidator.requireNonEmptyOrNull;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.stream;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;
import static net.jodah.typetools.TypeResolver.resolveRawClass;
import static org.reflections.scanners.Scanners.SubTypes;

import io.vavr.control.Try;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reflections.Reflections;

/**
 * The {@link ReflectionUtils} class provides utility functions to work with reflection mechanism.
 * <ul>
 *   <li>{@link #getClassLoader()}</li>
 *   <li>{@link #scanPackage(String...)}</li>
 *   <li>{@link #scanPackageByAnnotation(Class, String)}</li>
 *   <li>{@link #getField(Class, String)}</li>
 *   <li>{@link #getFields(Class[])}</li>
 *   <li>{@link #getDeepFields(Class[])}</li>
 *   <li>{@link #getFieldType(Class, Field)}</li>
 *   <li>{@link #getAnnotatedFields(Class, Class)}</li>
 *   <li>{@link #contain(Class, String)}</li>
 *   <li>{@link #extract(Class, int)}</li>
 *   <li>{@link #toJavaBasicType(Object, Class)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class ReflectionUtils {

  private ReflectionUtils() {
  }

  /**
   * The {@code getClassLoader} method returns {@link ClassLoader}.
   *
   * @return {@link ClassLoader}
   */
  public static ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  /**
   * The {@code scanPackage} method returns all classes under the
   * package as a {@link Set}.
   *
   * @param packageName package should be scanned
   * @return {@link Set}
   * @throws IllegalArgumentException if {@code packageName} is {@code null} or empty
   */
  private static Set<Class<?>> scanPackage(String packageName) {
    requireNonEmptyOrNull(packageName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.packageName")));

    var reflections = new Reflections(packageName, SubTypes.filterResultsBy(s -> true));

    return Try.of(() -> reflections.getAll(SubTypes))
        .getOrElse(HashSet::new)
        .stream()
        .map(file -> Try.of(() -> Class.forName(file)).get())
        .collect(toSet());
  }

  /**
   * The {@code scanPackage} method returns all classes under the
   * packages as a {@link Set}.
   *
   * @param packageNames packages should be scanned
   * @return {@link Set}
   * @throws IllegalArgumentException if {@code packageNames} is {@code null} or empty
   */
  public static Set<Class<?>> scanPackage(String... packageNames) {
    requireElement(packageNames);

    return stream(packageNames)
        .map(ReflectionUtils::scanPackage)
        .reduce((root, rest) -> {
          root.addAll(rest);
          return root;
        })
        .orElseGet(HashSet::new);
  }

  /**
   * The {@code scanPackageByAnnotation} method returns all decorated
   * classes with a specific {@link Annotation} that put under a
   * package.
   *
   * @param annotation  {@link Annotation}, to filter the classes
   * @param packageName packages should be scanned
   * @return {@link Set}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  private static Set<Class<?>> scanPackageByAnnotation(Class<? extends Annotation> annotation, String packageName) {
    requireNonEmptyOrNull(packageName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.packageName")));
    Reflections reflections = new Reflections(packageName);
    return reflections.getTypesAnnotatedWith(annotation);
  }

  /**
   * The {@code scanPackageByAnnotation} method returns all decorated
   * classes with a specific {@link Annotation} that put under a
   * packages.
   *
   * @param annotation   {@link Annotation}, to filter the classes
   * @param packageNames packages should be scanned
   * @return {@link Set}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static Set<Class<?>> scanPackageByAnnotation(Class<? extends Annotation> annotation, String... packageNames) {
    requireNonNull(annotation, i18n("error.validation.should.not.be.null", i18n("var.name.annotation")));
    requireElement(packageNames);

    return stream(packageNames)
        .map(packageName -> scanPackageByAnnotation(annotation, packageName))
        .reduce((root, rest) -> {
          root.addAll(rest);
          return root;
        })
        .orElse(new HashSet<>());
  }

  /**
   * The {@code getField} method is a recursive method to return the Optional
   * of {@link Field} if the class is included the field, otherwise it returns
   * {@code Optional.empty()}.
   *
   * @param type      class object of field owner
   * @param fieldName name of field
   * @return {@link Field}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static Optional<Field> getField(Class<?> type, String fieldName) {
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));
    requireNonEmptyOrNull(fieldName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.fieldName")));

    if (!contain(type, fieldName) && nonNull(type.getSuperclass())) {
      return getField(type.getSuperclass(), fieldName);
    }

    if (!contain(type, fieldName)) {
      return Optional.empty();
    }

    return Try.of(() -> type.getDeclaredField(fieldName)).toJavaOptional();

  }

  /**
   * The {@code getFields} method returns level-one fields of a class.
   *
   * @param type class object of fields owner
   * @return {@link List}
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  private static List<Field> getFields(Class<?> type) {
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));
    return stream(type.getDeclaredFields()).toList();
  }

  /**
   * The {@code getFields} method returns level-one fields of a classes.
   *
   * @param types class object of fields owners
   * @return {@link List}
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  public static List<Field> getFields(Class<?>... types) {
    requireElement(types);

    return stream(types)
        .map(ReflectionUtils::getFields)
        .reduce((root, rest) -> {
          root.addAll(rest);
          return root;
        })
        .orElseGet(ArrayList::new);
  }

  /**
   * The {@code getDeepFields} method returns fields belong to a class and
   * its super classes.
   *
   * @param type class object of fields owner
   * @return {@link List}
   * @throws IllegalArgumentException if {@code type} is {@code null}
   */
  private static List<Field> getDeepFields(Class<?> type) {
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    List<Field> fields = new ArrayList<>(stream(type.getDeclaredFields()).toList());

    while (nonNull(type.getSuperclass())) {
      type = type.getSuperclass();
      fields.addAll(stream(type.getDeclaredFields()).toList());
    }

    return fields;
  }

  /**
   * The {@code getDeepFields} method returns fields belong to the all classes.
   *
   * @param types class object of fields owners
   * @return {@link List}
   * @throws IllegalArgumentException if {@code types} is {@code null}
   */
  public static List<Field> getDeepFields(Class<?>... types) {
    requireElement(types);

    return Stream.of(types)
        .map(ReflectionUtils::getDeepFields)
        .reduce((root, rest) -> {
          root.addAll(rest);
          return root;
        })
        .orElseGet(ArrayList::new);
  }

  /**
   * The {@code getFieldType} method returns the type of field as a class object.
   *
   * @param owner class object of field owner
   * @param field {@link Field}
   * @return {@link Class}, class object
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static Class<?> getFieldType(Class<?> owner, Field field) {
    requireNonNull(owner, i18n("error.validation.should.not.be.null", i18n("var.name.owner")));
    requireNonNull(field, i18n("error.validation.should.not.be.null", i18n("var.name.field")));

    return field.getType() == Object.class ? resolveRawClass(field.getGenericType(), owner) : field.getType();
  }

  /**
   * The {@code getAnnotatedFields} method returns decorated fields by an annotation.
   *
   * @param type       class object of field owner
   * @param annotation {@link Annotation}, to use as a filter
   * @return {@link List}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static List<Field> getAnnotatedFields(Class<?> type, Class<? extends Annotation> annotation) {
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));
    requireNonNull(annotation, i18n("error.validation.should.not.be.null", i18n("var.name.annotation")));

    return getDeepFields(type)
        .stream()
        .filter(field -> field.isAnnotationPresent(annotation))
        .toList();
  }

  /**
   * The {@code contain} method returns {@code true} if the class is
   * included the field otherwise, it returns {@code false}.
   *
   * @param type      class object of field owner
   * @param fieldName name of field
   * @return {@link Boolean}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static boolean contain(Class<?> type, String fieldName) {
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));
    requireNonEmptyOrNull(fieldName, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.fieldName")));

    return Try.of(() -> type.getDeclaredField(fieldName)).isSuccess();
  }

  /**
   * The {@code extract} method returns type of generic class parameter in runtime.
   *
   * @param owner class object of field owner
   * @param index index number of class parameter
   * @return {@link Type}
   * @throws IllegalArgumentException if any parameter is {@code null} or empty
   */
  public static Type extract(Class<?> owner, int index) {
    requireNonNull(owner, i18n("error.validation.should.not.be.null", i18n("var.name.owner")));

    if (index < 0) {
      throw new IllegalArgumentException(i18n("error.validation.should.be.greaterThan", i18n("var.name.index"), 0));
    }

    Type[] types = ((ParameterizedType) owner.getGenericSuperclass()).getActualTypeArguments();

    if (index >= types.length) {
      throw new IllegalArgumentException(i18n("error.validation.should.be.lessThan", i18n("var.name.index"), types.length));
    }

    return types[index];
  }

  /**
   * The {@code toJavaBasicType} method converts a value with unknown
   * primitive type to a value with known primitive type.
   *
   * @param value value with unknown type
   * @param type  class object of expectation type
   * @param <T>   expectation type
   * @return wrapper of primitive value
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  @SuppressWarnings("unchecked")
  public static <T> T toJavaBasicType(Object value, Class<T> type) {
    requireNonNull(value, i18n("error.validation.should.not.be.null", i18n("var.name.value")));
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));

    if (value.getClass().equals(String.class)) {
      var valueAsString = String.valueOf(value);

      if (isDate(valueAsString).value()) {
        type = (Class<T>) LocalDate.class;
      } else if (isDateTime(valueAsString).value()) {
        type = (Class<T>) LocalDateTime.class;
      } else if (isTime(valueAsString).value()) {
        type = (Class<T>) LocalTime.class;
      } else if (valueAsString.equalsIgnoreCase("true") || valueAsString.equalsIgnoreCase("false")) {
        type = (Class<T>) Boolean.class;
      }
    }

    return JavaBasicTypeConverter.CONVERTERS.containsKey(type)
        ? (T) JavaBasicTypeConverter.CONVERTERS.get(type).apply(value)
        : null;

  }

  /**
   * The {@link JavaBasicTypeConverter} class is included data type converters.
   *
   * @author Saman Alishirishahrbabak
   * @version 1.0.0
   * @since 2022-01-01
   */
  private static final class JavaBasicTypeConverter {

    private static final Map<Class<?>, Function<Object, Object>> CONVERTERS = new HashMap<>();

    static {
      CONVERTERS.put(Integer.class, o -> Integer.valueOf(String.valueOf(o)));
      CONVERTERS.put(Long.class, o -> Long.valueOf(String.valueOf(o)));
      CONVERTERS.put(Float.class, o -> Float.valueOf(String.valueOf(o)));
      CONVERTERS.put(Double.class, o -> Double.valueOf(String.valueOf(o)));
      CONVERTERS.put(String.class, String::valueOf);
      CONVERTERS.put(Boolean.class, o -> Boolean.valueOf(String.valueOf(o)));
      CONVERTERS.put(LocalDateTime.class, o -> LocalDateTime.parse(String.valueOf(o), ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
      CONVERTERS.put(LocalDate.class, o -> LocalDate.parse(String.valueOf(o), ofPattern("yyyy-MM-dd")));
      CONVERTERS.put(LocalTime.class, o -> LocalTime.parse(String.valueOf(o), ofPattern("HH:mm:ss")));
    }
  }
}
