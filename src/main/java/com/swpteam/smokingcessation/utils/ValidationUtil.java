package com.swpteam.smokingcessation.utils;

import java.lang.reflect.Field;

public final class ValidationUtil {

    public static boolean isFieldExist(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                try {
                    Field superField = superClass.getDeclaredField(fieldName);
                    return true;
                } catch (NoSuchFieldException ex) {
                    return false;
                }
            }
            return false;
        }
    }

    private ValidationUtil() {}
}
