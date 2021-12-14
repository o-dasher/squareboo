package com.dasher.squareboo.assets;

import com.dasher.squareboo.framework.assets.AssetClassesHolder;

public class GameAssetClasses extends AssetClassesHolder {
    public final Class<GameTextures> textures;

    public GameAssetClasses() {
        addClass(textures = GameTextures.class);
    }
}
