package com.dasher.squareboo.framework.objects;

import java.util.Locale;

import lombok.NonNull;

public class Strings {
    @NonNull
    public static String capitalize(@NonNull String s) {
        var f = s.substring(0, 1).toUpperCase(Locale.ROOT);
        var r = s.substring(1);
        return f + r;
    }
}
