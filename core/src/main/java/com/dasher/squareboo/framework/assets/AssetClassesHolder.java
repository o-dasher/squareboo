package com.dasher.squareboo.framework.assets;


import com.badlogic.gdx.utils.Array;

public class AssetClassesHolder {
    protected final Array<Class<? extends AssetBox<?>>> assetClasses = new Array<>();

    protected void addClass(Class<? extends AssetBox<?>> klass) {
        assetClasses.add(klass);
    }
}
