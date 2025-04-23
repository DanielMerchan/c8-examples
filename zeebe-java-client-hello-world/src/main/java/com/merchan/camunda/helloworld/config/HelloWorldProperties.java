package com.merchan.camunda.helloworld.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HelloWorldProperties {

    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_FILE = "application.properties";
    private static final Logger LOG = LogManager.getLogger(HelloWorldProperties.class);

    // Static block to load the properties file once when the class is loaded
    static {
        reloadProperties();
    }

    /**
     * Get a particular property from the loaded properties file
     * @param key - Key of the property
     * @return String - Null if the property is not found
     */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * Get a particular property from the loaded properties file
     * @param key - Key of the property
     * @param defaultValue - Default value in the case of the property is not found
     * @return String
     */
    public static String getProperty(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    /**
     * Reload the properties
     */
    public static void reloadProperties() {
        try (InputStream input = HelloWorldProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                var message = String.format("Properties file %s not found", PROPERTIES_FILE);
                LOG.error(message);
                throw new RuntimeException(message);
            }
            // Load the properties from the input stream
            PROPERTIES.load(input);
        } catch (IOException ex) {
            var message = "Failed to load properties file: " + PROPERTIES_FILE;
            LOG.error(message, ex);
            throw new RuntimeException("Failed to load properties file", ex);
        }
    }
}