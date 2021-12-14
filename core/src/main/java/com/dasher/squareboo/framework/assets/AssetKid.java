package com.dasher.squareboo.framework.assets;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.NonNull;

public abstract class AssetKid<T, P extends AssetBox<T>> extends AssetBox<T> {
    public AssetKid(@NonNull P parent, @NonNull Path subDir) {
        super(Paths.get(parent.dir, subDir.toString()), parent.assetType);
    }
}