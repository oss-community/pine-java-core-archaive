open module core.java.helper {
  requires javaee.web.api;
  requires java.sql;
  requires com.oracle.database.jdbc;
  requires com.h2database;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.databind;
  requires io.vavr;
  requires com.ibm.icu;
  requires org.slf4j;
  requires org.reflections;
  requires net.jodah.typetools;
  requires java.base;

  exports com.pineframework.core.helper;
  exports com.pineframework.core.helper.validator;
}
