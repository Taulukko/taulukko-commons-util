/**
 * 
 */
/**
 * @author gandbranco
 *
 */
module taulukko.commons.util {
	exports com.taulukko.commons;
	exports com.taulukko.commons.util;
	exports com.taulukko.commons.util.exception;
	exports com.taulukko.commons.util.metrics;
	exports com.taulukko.commons.util.test;
	exports com.taulukko.commons.util.script;
	exports com.taulukko.commons.util.xml;
	exports com.taulukko.commons.util.web;  
	exports com.taulukko.commons.util.tools; 
	exports com.taulukko.commons.util.config;
	exports com.taulukko.commons.util.web.controler.flood; 
	exports com.taulukko.commons.util.data;
	exports com.taulukko.commons.util.lang;
	exports com.taulukko.commons.util.text; 
	exports com.taulukko.commons.util.reflection;
	exports com.taulukko.commons.util.struct;
	exports com.taulukko.commons.util.data.keys;
	exports com.taulukko.commons.util.event;
	exports com.taulukko.commons.util.lang.datetime;
	exports com.taulukko.commons.util.thread;
	exports com.taulukko.commons.util.web.beans; 
	exports com.taulukko.commons.util.crypt;
	exports com.taulukko.commons.util.io;
	exports com.taulukko.commons.util.game;
	exports com.taulukko.commons.util.config.serialization; 
	
 
	requires org.apache.commons.codec;
	requires java.desktop; 
	requires java.management;
	requires java.naming;
	requires java.sql;
	requires javax.servlet.api;
	requires commons.dbutils;
	requires commons.validator;
	requires org.apache.commons.io;
	requires log4j;
	requires log4j.api;
	requires commons.fileupload;
	requires org.apache.commons.lang3;
	requires junit;
}