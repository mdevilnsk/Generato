/*
 * Copyright 2018 Dmitriy Mitrofanov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package ru.plamit;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dmitriy Mitrofanov on 28.01.2018.
 * <p>
 * Model generator methods generator
 */

public class Generator {

    public static <T> void generateFunctions(Class<T> className) {
        List<Method> methodList = Arrays.asList(className.getMethods());
        List<String> publicSetMethods = new ArrayList<>();
        String generatedMetod = "";

        generatedMetod += ("public static " + className.getSimpleName() + " generate" + className.getSimpleName() + "(int id){" + System.lineSeparator());
        generatedMetod += ("\t" + className.getSimpleName() + " " + className.getSimpleName().toLowerCase() + " = new " + className.getSimpleName() + "();" + System.lineSeparator());

        for (Method method : methodList) {
            if (method.getName().contains("set")) {
                for (int i = 0; i < method.getParameterTypes().length; i++) {
                    //set strings
                    if (method.getParameterTypes()[i].getSimpleName().equals("String")) {
                        generatedMetod += ("\t" +
                                className.getSimpleName().toLowerCase()
                                + "."
                                + method.getName()
                                + "(\""
                                + method.getName().toLowerCase().replace("set", "")
                                + "\"+id);" + System.lineSeparator());
                    } else
                        //set boolean
                        if (method.getParameterTypes()[i].getSimpleName().equals("boolean") || method.getParameterTypes()[i].getSimpleName().equals("Boolean")) {
                            generatedMetod += ("\t" +
                                    className.getSimpleName().toLowerCase()
                                    + "."
                                    + method.getName()
                                    + "(id%2==0);" + System.lineSeparator());
                        } else //set ints
                            if (method.getParameterTypes()[i].getSimpleName().equals("int") || method.getParameterTypes()[i].getSimpleName().equals("Integer")) {
                                generatedMetod += ("\t" +
                                        className.getSimpleName().toLowerCase()
                                        + "."
                                        + method.getName()
                                        + "(id);" + System.lineSeparator());
                            } else
                                //set longs
                                if (method.getParameterTypes()[i].getSimpleName().equals("long") || method.getParameterTypes()[i].getSimpleName().equals("Long")) {
                                    generatedMetod += ("\t" +
                                            className.getSimpleName().toLowerCase()
                                            + "."
                                            + method.getName()
                                            + "((long)id);" + System.lineSeparator());
                                } else
                                    //set floats
                                    if (method.getParameterTypes()[i].getSimpleName().equals("float") || method.getParameterTypes()[i].getSimpleName().equals("Float")) {
                                        generatedMetod += ("\t" +
                                                className.getSimpleName().toLowerCase()
                                                + "."
                                                + method.getName()
                                                + "((float)id);" + System.lineSeparator());
                                    } else if (method.getParameterTypes()[i].getSimpleName().startsWith("List")) {

                                        ParameterizedType stringListType = (ParameterizedType) method.getParameters()[0].getParameterizedType();
                                        Class<?> listClass = (Class<?>) stringListType.getActualTypeArguments()[0];
                                        generateFunctions(listClass);
                                        generateList(listClass);

                                        generatedMetod += ("\t" +
                                                className.getSimpleName().toLowerCase()
                                                + "."
                                                + method.getName()
                                                //TODO: think about num
                                                + "(generateList" + listClass.getSimpleName() + "(1));" + System.lineSeparator());

                                    } else {
                                        generatedMetod += ("\t" +
                                                className.getSimpleName().toLowerCase()
                                                + "."
                                                + method.getName()
                                                + "(generate" + method.getParameterTypes()[i].getSimpleName() + "(id));" + System.lineSeparator());
                                        generateFunctions(method.getParameterTypes()[i]);
                                    }
                }
            }
        }
        generatedMetod += ("\treturn " + className.getSimpleName().toLowerCase() + ";" + System.lineSeparator());
        generatedMetod += ("}" + System.lineSeparator());

        System.out.print(generatedMetod);
    }

    public static <T> void generateList(Class<T> className) {
        String output = "";
        output += ("public static List<" + className.getSimpleName() + "> generateList" + className.getSimpleName() + "(int num){" + System.lineSeparator());
        output += ("\t" + "List<" + className.getSimpleName() + ">" + " " + className.getSimpleName().toLowerCase() + " = new ArrayList<>();" + System.lineSeparator());
        output += ("\tfor (int i = 0; i < num; i++){" + System.lineSeparator());
        output += ("\t\t" + className.getSimpleName().toLowerCase() + ".add(generate" + className.getSimpleName() + "(i));" + System.lineSeparator());
        output += ("\t}" + System.lineSeparator());
        output += ("\treturn " + className.getSimpleName().toLowerCase() + ";" + System.lineSeparator());
        output += ("}" + System.lineSeparator());
        System.out.println(output);
    }
}