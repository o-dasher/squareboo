package com.dasher.squareboo.framework.io;

import lombok.NonNull;
import text.formic.Stringf;

public class OutputTemplates {
    public static String createDefaultShow(OutputDefaults defaults, Class<?> klass, String suffix) {
        return createShowString(defaults.toString(), klass, suffix);
    }

    public static String createShowString(String prefix, @NonNull Class<?> klass, String suffix) {
        return Stringf.format("%s %s: %s", prefix, klass.getSimpleName(), suffix);
    }
}
