/**
 * 
 */
/**
 * @author bsutton
 *
 */
module acceloapi
{
	exports au.com.noojee.acceloapi;
	exports au.com.noojee.acceloapi.entities.meta;
	exports au.com.noojee.acceloapi.util;
	exports au.com.noojee.acceloapi.dao.gson;
	exports au.com.noojee.acceloapi.cache;
	exports au.com.noojee.acceloapi.entities.generator;
	exports au.com.noojee.acceloapi.dao;
	exports au.com.noojee.acceloapi.filter;
	exports au.com.noojee.acceloapi.entities;
	exports au.com.noojee.acceloapi.entities.meta.fieldTypes;
	exports au.com.noojee.acceloapi.entities.types;

	requires com.github.spotbugs.annotations;
	requires com.google.common;
	requires commons.lang3;
	requires gson;
	requires java.desktop;
	requires transitive joda.money;
	requires jsr305;
	requires log4j.api;
	requires reflections;
}