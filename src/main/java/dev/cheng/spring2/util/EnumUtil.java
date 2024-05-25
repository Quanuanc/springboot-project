package dev.cheng.spring2.util;

public class EnumUtil {
    public static <E extends Enum<E>> E getByOrdinal(Class<E> clazz, int ordinal) {
        E[] enumConstants = clazz.getEnumConstants();
        if (ordinal < 0 || ordinal >= enumConstants.length) {
            throw new IllegalArgumentException("ordinal is out of range");
        }
        return enumConstants[ordinal];
    }
}
