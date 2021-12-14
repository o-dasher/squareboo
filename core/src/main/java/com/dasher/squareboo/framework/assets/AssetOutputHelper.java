package com.dasher.squareboo.framework.assets;

import text.formic.Stringf;

class AssetOutputHelper {
    static void throwClassNotFound(Object signaler, Class<?> notFoundClass) throws ClassNotFoundException {
        throw new ClassNotFoundException(
                Stringf.format(
                        "Class: %s was not found on this %s",
                        notFoundClass.getSimpleName(),
                        signaler.getClass().getSimpleName()
                )
        );
    }
}
