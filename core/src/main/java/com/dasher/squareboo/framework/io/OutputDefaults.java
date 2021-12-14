package com.dasher.squareboo.framework.io;


import com.dasher.squareboo.framework.objects.Strings;

public enum OutputDefaults {
    LOADED,
    LOADING;

    @Override
    public String toString() {
        return Strings.capitalize(super.toString());
    }
}
