package com.xu.geo;

/**
 * 工具类
 * Util
 * @author xuwei
 */
public abstract class Util {

    public static double wrapNum(double x, int[] range, boolean includeMax) {
        double max = range[1];
        double min = range[0];
        double d = max - min;
        if (x == max && includeMax) {
            return x;
        } else {
            return ((x - min) % d + d) % d + min;
        }
    }

    
}