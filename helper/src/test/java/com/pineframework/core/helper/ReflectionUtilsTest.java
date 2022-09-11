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
import static com.pineframework.core.helper.ReflectionUtils.contain;
import static com.pineframework.core.helper.ReflectionUtils.extract;
import static com.pineframework.core.helper.ReflectionUtils.getAnnotatedFields;
import static com.pineframework.core.helper.ReflectionUtils.getClassLoader;
import static com.pineframework.core.helper.ReflectionUtils.getDeepFields;
import static com.pineframework.core.helper.ReflectionUtils.getField;
import static com.pineframework.core.helper.ReflectionUtils.getFieldType;
import static com.pineframework.core.helper.ReflectionUtils.getFields;
import static com.pineframework.core.helper.ReflectionUtils.scanPackage;
import static com.pineframework.core.helper.ReflectionUtils.scanPackageByAnnotation;
import static com.pineframework.core.helper.ReflectionUtils.toJavaBasicType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pineframework.core.helper.noneemptypackage.TestReflection;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * {@link ReflectionUtilsTest} class provides unit tests for {@link ReflectionUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see ReflectionUtils
 * @since 2022-01-01
 */
@SuppressWarnings({"ConstantConditions", "unused"})
@DisplayName("Reflection Utils Tests")
class ReflectionUtilsTest extends AbstractUtilsTest {

  @Test
  @DisplayName("getting class loader")
  void getClassLoader_IfCallFunction_ShouldReturnClassLoader() {
    //Given:nothing

    //When
    var result = getClassLoader();

    // Then
    assertNotNull(result);
  }

  @Test
  @DisplayName("scan classes under packages if package names is null")
  void scanPackage_IfPackageNamesIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    String[] givenPackageNames = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> scanPackage(givenPackageNames));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("scan classes under packages if package names is empty")
  void scanPackage_IfPackageNamesIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenPackageNames = new String[] {};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> scanPackage(givenPackageNames));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("scan classes under packages if package names includes null package")
  void scanPackage_IfPackageNamesAreNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenPackageNames = new String[] {null};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> scanPackage(givenPackageNames));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.packageName")), message);
  }

  @Test
  @DisplayName("scan classes under packages if package names  includes empty package")
  void scanPackage_IfPackageNamesAreEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenPackageNames = new String[] {""};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> scanPackage(givenPackageNames));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.packageName")), message);
  }

  @Test
  @DisplayName("scan classes under empty packages")
  void scanPackage_IfPackageIsEmpty_ShouldReturnEmptySet() {
    //Given
    var givenPackageNames = new String[] {"com.pineframework.core.helper.emptypackage"};

    //When
    var result = scanPackage(givenPackageNames);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("scan classes under non empty packages")
  void scanPackage_IfPackageIsNotEmpty_ShouldReturnAllClassesUnderPackage() {
    //Given
    var givenPackageNames = new String[] {"com.pineframework.core.helper.noneemptypackage"};

    //Expectation
    var expectedClasses = new Class[] {Object.class, TestReflection.class};

    //When
    var result = scanPackage(givenPackageNames);

    //Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertThat(result).containsOnly(expectedClasses);
  }

  @Test
  @DisplayName("scan classes under invalid packages")
  void scanPackage_IfPackageIsInvalid_ShouldReturnEmptySet() {
    //Given
    var givenPackageNames = new String[] {"invalid package"};

    //When
    var result = scanPackage(givenPackageNames);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("scan decorated classes under packages if annotation parameter is null")
  void scanPackageByAnnotation_IfAnnotationIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Class<? extends Annotation> givenAnnotation = null;
    var givenPackageNames = new String[] {"any packages"};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> scanPackageByAnnotation(givenAnnotation, givenPackageNames));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.annotation")), message);
  }

  @Test
  @DisplayName("scan decorated classes with a specific annotation under packages if package names is null")
  void scanPackageByAnnotation_IfPackageNamesIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenAnnotation = AnyAnnotation.class;
    String[] givenPackageNames = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> scanPackageByAnnotation(givenAnnotation, givenPackageNames));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("scan decorated classes with a specific annotation under packages if package names is empty")
  void scanPackageByAnnotation_IfPackageNamesIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenAnnotation = AnyAnnotation.class;
    var givenPackageNames = new String[] {};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> scanPackageByAnnotation(givenAnnotation, givenPackageNames));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("scan decorated classes with a specific annotation under packages if package names includes null")
  void scanPackageByAnnotation_IfPackageNamesIncludesNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenAnnotation = AnyAnnotation.class;
    var givenPackageNames = new String[] {null};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> scanPackageByAnnotation(givenAnnotation, givenPackageNames));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.packageName")), message);
  }

  @Test
  @DisplayName("scan decorated classes with a specific annotation under packages if package names includes empty")
  void scanPackageByAnnotation_IfPackageNamesIncludesEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenAnnotation = AnyAnnotation.class;
    var givenPackageNames = new String[] {""};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> scanPackageByAnnotation(givenAnnotation, givenPackageNames));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.packageName")), message);
  }

  @Test
  @DisplayName("scan decorated classes with a specific annotation under an empty package")
  void scanPackageByAnnotation_IfPackageIsEmpty_ShouldReturnEmptySet() {
    //Given
    var givenAnnotation = AnyAnnotation.class;
    String givenPackageNames = "com.pineframework.core.helper.emptypackage";

    //When
    var result = scanPackageByAnnotation(givenAnnotation, givenPackageNames);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("scan decorated classes with a specific annotation under a package")
  void scanPackageByAnnotation_IfSuperClassIsAnnotated_ShouldReturnSuperClassAndSubClasses() {
    //Given
    var givenAnnotation = TestAnnotationForAbstractClass.class;
    String givenPackageNames = "com.pineframework.core.helper";

    //Expectation
    var expectedClasses = new Class[] {TestAbstractClass.class, TestClass.class};

    //When
    var result = scanPackageByAnnotation(givenAnnotation, givenPackageNames);

    //Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertThat(result).containsOnly(expectedClasses);
  }

  @Test
  @DisplayName("scan decorated classes with a specific annotation under a package")
  void scanPackageByAnnotation_IfSubClassIsAnnotated_ShouldReturnOnlySubClasses() {
    //Given
    var givenAnnotation = TestAnnotationForConcreteClass.class;
    String givenPackageNames = "com.pineframework.core.helper";

    //Expectation
    var expectedClasses = new Class[] {TestClass.class};

    //When
    var result = scanPackageByAnnotation(givenAnnotation, givenPackageNames);

    //Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertThat(result).containsOnly(expectedClasses);
  }

  @Test
  @DisplayName("getting field of a class if type is null")
  void getField_IfTypeIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Class<?> givenType = null;
    var givenFieldName = "any filed";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> getField(givenType, givenFieldName));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.type")), message);
  }

  @Test
  @DisplayName("getting field of a class if field name is empty")
  void getField_IfFieldNameIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenType = AnyClass.class;
    var givenFieldName = "";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> getField(givenType, givenFieldName));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.fieldName")), message);
  }

  @Test
  @DisplayName("getting field of a class if field name is null")
  void getField_IfFieldNameIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenType = AnyClass.class;
    String givenFieldName = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> getField(givenType, givenFieldName));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.fieldName")), message);
  }

  @Test
  @DisplayName("getting field of a class if field is exist")
  void getField_IfFieldIsExist_ShouldReturnField() {
    //Given
    var givenType = AnyClass.class;
    var givenFieldName = "field";

    //Expectation
    var expectedFieldName = "field";

    //When
    var result = getField(givenType, givenFieldName);

    //Then
    assertNotNull(result);
    result.ifPresent(field -> assertEquals(expectedFieldName, field.getName()));
  }

  @Test
  @DisplayName("getting field of a class if field is not exist")
  void getField_IfFieldIsNotExist_ShouldReturnField() {
    //Given
    var givenType = AnyClass.class;
    var givenFieldName = "invalid field name";

    //When
    var result = getField(givenType, givenFieldName);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("filter fields of a class if types is null")
  void getFields_IfTypesIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Class<?> givenTypes = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> getFields(givenTypes));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.type")), message);
  }

  @Test
  @DisplayName("getting fields of a class and its super classes if types is null")
  void getDeepFields_IfTypesIsNul_ShouldThrowIllegalArgumentException() {
    //Given
    Class<?>[] givenTypes = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> getDeepFields(givenTypes));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("getting fields of a class and its super classes if types is empty")
  void getDeepFields_IfTypeIsNul_ShouldThrowIllegalArgumentException() {
    //Given
    var givenTypes = new Class[] {};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> getDeepFields(givenTypes));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("getting fields of many classes and their super classes")
  void getDeepFields_IfTypesAreValid_ShouldReturnFilteredFields() {
    //Given
    var givenTypes = new Class[] {TestClass.class, AnyClass.class};

    //Expectation
    var expectedFields = new String[] {"t1", "t2", "filteredConcreteField", "a1", "a2", "filteredAbstractField", "field"};

    //When
    var fields = getDeepFields(givenTypes);

    //Then
    assertThat(expectedFields).containsOnly(fields.stream().map(Field::getName).toArray(String[]::new));
  }

  @Test
  @DisplayName("getting field type")
  void getFieldType_IfFieldIsExist_ShouldReturnType() {
    //Given
    var givenType = TestClass.class;
    var givenField = getField(givenType, "a1").orElseThrow();

    //Expectation
    var expectedFieldType = Serializable.class;

    //When
    var fieldType = getFieldType(givenType, givenField);

    //Then
    assertEquals(expectedFieldType, fieldType);
  }

  @Test
  @DisplayName("getting field type if the field is null")
  void getFieldType_IfFieldIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Field givenField = null;
    var givenOwner = AnyClass.class;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> getFieldType(givenOwner, givenField));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.field")), message);
  }

  @Test
  @DisplayName("getting field type if the owner is null")
  void getFieldType_IfOwnerIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Class<?> givenOwner = null;
    var givenField = getField(AnyClass.class, "field").orElseThrow();

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> getFieldType(givenOwner, givenField));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.owner")), message);
  }

  @Test
  @DisplayName("getting decorated fields by annotation if type is null")
  void getAnnotatedFields_IfTypeIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Class<?> givenType = null;
    var givenAnnotation = AnyAnnotation.class;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> getAnnotatedFields(givenType, givenAnnotation));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.type")), message);
  }

  @Test
  @DisplayName("getting decorated fields by annotation if the annotation is null")
  void getAnnotatedFields_IfAnnotationIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenType = AnyClass.class;
    Class<? extends Annotation> givenAnnotation = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> getAnnotatedFields(givenType, givenAnnotation));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.annotation")), message);
  }

  @Test
  @DisplayName("getting decorated fields by annotation")
  void getAnnotatedFields_IfFindFields_ShouldReturnNonEmptyList() {
    //Given
    var givenType = TestClass.class;
    var givenAnnotation = Group1.class;

    //Expectation
    var expectedFields = new String[] {"t1", "a1"};

    //When
    var fields = getAnnotatedFields(givenType, givenAnnotation);

    //Then
    assertNotNull(fields);
    assertFalse(fields.isEmpty());
    assertArrayEquals(expectedFields, fields.stream().map(Field::getName).toArray(String[]::new));
  }

  @Test
  @DisplayName("checking a class is contained a field if type is null")
  void contain_IfTypeIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Class<?> givenType = null;
    var givenFieldName = "any filed";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> contain(givenType, givenFieldName));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.type")), message);
  }

  @Test
  @DisplayName("checking a class is contained a field if field name is null")
  void contain_IfFieldNameIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenType = AnyClass.class;
    String givenFieldName = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> contain(givenType, givenFieldName));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.fieldName")), message);
  }

  @Test
  @DisplayName("checking a class is contained a field if field name is empty")
  void contain_IfFieldNameIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    Class<?> givenType = AnyClass.class;
    var givenFieldName = "";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> contain(givenType, givenFieldName));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.fieldName")), message);
  }

  @Test
  @DisplayName("checking a class is contained a field if field name is empty")
  void contain_IfClassContainsField_ShouldReturnTrue() {
    //Given
    var givenType = AnyClass.class;
    var givenFieldName = "field";

    //When
    var result = contain(givenType, givenFieldName);

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking a class is contained a field if field name is empty")
  void contain_IfClassContainsField_ShouldReturnFalse() {
    //Given
    var givenType = AnyClass.class;
    var givenFieldName = "absent field";

    //When
    var result = contain(givenType, givenFieldName);

    // Then
    assertFalse(result);
  }

  @Test
  @DisplayName("extracting type of class parameter if owner is null")
  void extract_IfTypeIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Class<?> givenOwner = null;
    var givenIndex = 0;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> extract(givenOwner, givenIndex));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.owner")), message);
  }

  @Test
  @DisplayName("extracting type of class parameter if index of parameter is less than zero")
  void extract_IfIndexIsLessThanZero_ShouldThrowIllegalArgumentException() {
    //Given
    var givenOwner = AnyClass.class;
    var givenIndex = -1;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> extract(givenOwner, givenIndex));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.be.greaterThan", i18n("var.name.index"), 0), message);
  }

  @Test
  @DisplayName("extracting type of class parameter if index of parameter is more than parameter number")
  void extract_IfIndexIsMoreThanParameterNumber_ShouldThrowIllegalArgumentException() {
    //Given
    var givenOwner = TestClass.class;
    var givenIndex = 1;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> extract(givenOwner, givenIndex));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.be.lessThan", i18n("var.name.index"), 1), message);
  }

  @Test
  @DisplayName("extracting type of class parameter if index of parameter is valid")
  void extract_IfThereIsClassParam_ShouldReturnTypeClassParam() {
    //Given
    var givenType = TestClass.class;
    var givenFieldName = 0;

    //Expectation
    var expectedTypeName = "java.lang.String";

    //When
    var result = extract(givenType, givenFieldName);

    // Then
    assertNotNull(result);
    assertEquals(expectedTypeName, result.getTypeName());
  }

  @Test
  @DisplayName("converting value with unknown type to java basic type")
  void toJavaBasicType_IfValueIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Object givenValue = null;
    var givenType = AnyClass.class;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> toJavaBasicType(givenValue, givenType));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.value")), message);
  }

  @Test
  @DisplayName("converting value with unknown type to java basic type")
  void toJavaBasicType_IfTypeIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenValue = "any value";
    Class<?> givenType = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(IllegalArgumentException.class, () -> toJavaBasicType(givenValue, givenType));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.type")), message);
  }

  @ParameterizedTest(name = "[{index}]: given {0} convert to {1}")
  @ArgumentsSource(PrimitiveArgumentsProvider.class)
  @DisplayName("converting value with unknown type to java basic type")
  void toJavaBasicType_IfConvert_ShouldReturnPrimitiveType(Object givenValue, Class<?> givenType, Object expectedValue) {

    //When
    var result = toJavaBasicType(givenValue, givenType);

    //Then
    assertEquals(expectedValue, result);
  }

  @Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @Inherited
  @Documented
  @interface TestAnnotation {
  }

  @Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @Inherited
  @Documented
  @interface AnyAnnotation {
  }

  @Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @Inherited
  @Documented
  @interface Group1 {
  }

  @Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @Inherited
  @Documented
  @interface Group2 {
  }

  @Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @Inherited
  @Documented
  @interface TestAnnotationForAbstractClass {
  }

  @Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
  @Retention(RetentionPolicy.RUNTIME)
  @Inherited
  @Documented
  @interface TestAnnotationForConcreteClass {
  }

  @AnyAnnotation
  @TestAnnotationForAbstractClass
  static class TestAbstractClass<T extends Serializable> {
    @AnyAnnotation
    @Group1
    protected T a1;

    @AnyAnnotation
    @Group2
    protected Object a2;

    @AnyAnnotation
    protected Object filteredAbstractField;
  }

  @AnyAnnotation
  @TestAnnotation
  @TestAnnotationForConcreteClass
  static class TestClass extends TestAbstractClass<String> {
    @AnyAnnotation
    @TestAnnotation
    protected Object filteredConcreteField;
    @AnyAnnotation
    @TestAnnotation
    @Group1
    private Object t1;
    @AnyAnnotation
    @TestAnnotation
    @Group2
    private Object t2;
  }

  @AnyAnnotation
  static class AnyClass {
    @AnyAnnotation
    protected Object field;
  }

  static class PrimitiveArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of("1", Integer.class, 1),
          Arguments.of("1990-01-01", LocalDate.class, LocalDate.of(1990, 1, 1)),
          Arguments.of("09:15:45", LocalTime.class, LocalTime.of(9, 15, 45)),
          Arguments.of("1990-01-01T09:15:45", LocalDateTime.class, LocalDateTime.of(1990, 1, 1, 9, 15, 45)),
          Arguments.of("true", Boolean.class, true),
          Arguments.of("false", Boolean.class, false),
          Arguments.of("string", String.class, "string"));
    }
  }
}
