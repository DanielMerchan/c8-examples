package com.merchan.camunda.helloworld.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

public class HelloWorldProperties {

    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_FILE = "application.properties";
    private static final Logger LOG = LogManager.getLogger(HelloWorldProperties.class);

    // Static block to load the properties file once when the class is loaded
    static {
        try (InputStream input = HelloWorldProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException(MessageFormat.format("Properties file {0} not found in the classpath", PROPERTIES));
            }
            // Load the properties from the input stream
            PROPERTIES.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load properties file", ex);
        }
    }

    // Method to get a property value by key
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    // Method to get a property value by key with a default value
    public static String getProperty(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    // Method to reload the properties file if needed
    public static void reloadProperties() {
        try (InputStream input = HelloWorldProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException(MessageFormat.format("Properties file {0} not found in the classpath", PROPERTIES));
            }
            PROPERTIES.clear();
            PROPERTIES.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to reload properties file", ex);
        }
    }
}