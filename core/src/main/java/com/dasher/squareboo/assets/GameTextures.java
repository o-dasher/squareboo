package com.dasher.squareboo.assets;

import com.badlogic.gdx.graphics.Texture;
import com.dasher.squareboo.framework.assets.AssetBox;


public class GameTextures extends AssetBox<Texture> {
    public GameTextures() {
        super(GenericAssetPaths.textures, Texture.class);
    }
}
