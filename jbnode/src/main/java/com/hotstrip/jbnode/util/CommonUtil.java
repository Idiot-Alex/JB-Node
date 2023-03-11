package com.hotstrip.jbnode.util;

public final class CommonUtil {
    public static String formatTimeMillis(long size) {
        String[] units = new String[] { "ms", "s", "m", "h", "d" };
        int index = 0;
        double value = size;

        while (value > 1024 && index < units.length - 1) {
            if (index == 0) {
                value = value / 1000;
            } else if (index == 3) {
                value = value / 24;
            } else {
                value = value / 60;
            }
            index++;
        }

        return String.format("%.2f %s", value, units[index]);
    }

}
