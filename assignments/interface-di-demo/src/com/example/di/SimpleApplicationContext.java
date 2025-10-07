package com.example.di;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Lightweight XML-based container that supports a subset of Spring's bean configuration
 * semantics: bean creation plus setter injection with simple value and ref properties.
 */
public class SimpleApplicationContext {

    private final Map<String, Object> beanRegistry = new HashMap<>();
    private final Map<String, Element> beanDefinitions = new HashMap<>();

    public SimpleApplicationContext(String xmlPath) {
        try {
            loadDefinitions(xmlPath);
            instantiateBeans();
            wireProperties();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize application context", e);
        }
    }

    public <T> T getBean(String id, Class<T> type) {
        Object bean = beanRegistry.get(id);
        if (bean == null) {
            throw new IllegalArgumentException("No bean with id '" + id + "' registered.");
        }
        return type.cast(bean);
    }

    private void loadDefinitions(String xmlPath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlPath));
        Element root = document.getDocumentElement();

        NodeList beans = root.getElementsByTagName("bean");
        for (int i = 0; i < beans.getLength(); i++) {
            Node node = beans.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element beanElement = (Element) node;
                String id = beanElement.getAttribute("id");
                beanDefinitions.put(id, beanElement);
            }
        }
    }

    private void instantiateBeans() throws Exception {
        for (Map.Entry<String, Element> entry : beanDefinitions.entrySet()) {
            String id = entry.getKey();
            Element beanElement = entry.getValue();
            String className = beanElement.getAttribute("class");
            Class<?> beanClass = Class.forName(className);
            Object instance = beanClass.getDeclaredConstructor().newInstance();
            beanRegistry.put(id, instance);
        }
    }

    private void wireProperties() throws Exception {
        for (Map.Entry<String, Element> entry : beanDefinitions.entrySet()) {
            Object bean = beanRegistry.get(entry.getKey());
            Element beanElement = entry.getValue();
            NodeList properties = beanElement.getElementsByTagName("property");
            for (int i = 0; i < properties.getLength(); i++) {
                Node node = properties.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                Element propertyElement = (Element) node;
                String name = propertyElement.getAttribute("name");
                String value = propertyElement.getAttribute("value");
                String ref = propertyElement.getAttribute("ref");
                Method setter = findSetter(bean.getClass(), name);
                if (setter == null) {
                    throw new IllegalStateException("No setter found for property '" + name + "' on bean "
                            + bean.getClass().getName());
                }

                Class<?> paramType = setter.getParameterTypes()[0];
                Object argument;
                if (!ref.isEmpty()) {
                    argument = beanRegistry.get(ref);
                    if (argument == null) {
                        throw new IllegalStateException("Referenced bean '" + ref + "' not found.");
                    }
                } else if (!value.isEmpty()) {
                    argument = convertValue(paramType, value);
                } else {
                    throw new IllegalStateException("Property '" + name + "' must declare a value or ref.");
                }
                setter.invoke(bean, argument);
            }
        }
    }

    private Method findSetter(Class<?> beanClass, String propertyName) {
        String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        for (Method method : beanClass.getMethods()) {
            if (method.getName().equals(methodName) && method.getParameterCount() == 1) {
                return method;
            }
        }
        return null;
    }

    private Object convertValue(Class<?> targetType, String value) {
        if (targetType.equals(String.class)) {
            return value;
        }
        if (targetType.equals(int.class) || targetType.equals(Integer.class)) {
            return Integer.parseInt(value);
        }
        if (targetType.equals(boolean.class) || targetType.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        }
        throw new IllegalArgumentException("Unsupported value type: " + targetType.getName());
    }
}
