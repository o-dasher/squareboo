package com.dasher.squareboo.framework.io;

import com.github.czyzby.kiwi.util.common.Strings;

public enum FileExtension {
    NONE(Strings.EMPTY_STRING);

    private final String name;

    FileExtension() {
        this.name = super.toString();
    }

    FileExtension(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
