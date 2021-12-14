package com.dasher.squareboo.framework.assets;

import com.dasher.squareboo.framework.io.OutputTemplates;

import text.formic.Stringf;

class AssetOutputHelper {
    static void throwClassNotFound(Object signaler, Class<?> notFoundClass) throws ClassNotFoundException {
        throw new ClassNotFoundException(
                Stringf.format(
                        "Class: %s",
                        OutputTemplates.createNotFoundOn(signaler.getClass(), notFoundClass)
                )
        );
    }
}
