package com.dasher.squareboo.framework.assets;


import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.utils.Array;
import com.dasher.squareboo.framework.io.FileExtension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import lombok.NonNull;

public abstract class AssetBox<T> {
    protected final String dir;
    protected final Class<T> assetType;
    protected final AssetLoaderParameters<T> assetLoaderParameters;

    protected final Array<AssetDescriptor<T>> assetDescriptors = new Array<>();
    protected final Array<AssetKid<T, AssetBox<T>>> assetKids = new Array<>();

    public AssetBox(
            @NonNull Path dir,
            @NonNull Class<T> assetType,
            @Nullable AssetLoaderParameters<T> assetLoaderParameters
    ) {
        this.dir = dir.toString();
        this.assetType = assetType;
        this.assetLoaderParameters = assetLoaderParameters;
    }

    public AssetBox(
            @NonNull Path dir,
            @NonNull Class<T> assetType
    ) {
        this(dir, assetType, null);
    }

    public AssetBox(
            @NonNull IGenericPathIdentifier[] genericAssetPaths,
            @NonNull Class<T> assetType,
            @Nullable AssetLoaderParameters<T> assetLoaderParameters
    ) {
        this(Paths.get(genericAssetPaths[0].toString(), ((Supplier<String[]>) () -> {
            if (genericAssetPaths.length <= 1)
                return new String[]{};
            var left = new Array<String>(String.class);
            for (int i = 1; i < genericAssetPaths.length; i++) {
                left.add(genericAssetPaths[i].toString());
            }
            return left.toArray();
        }).get()), assetType, assetLoaderParameters);
    }

    public AssetBox(
            @NonNull IGenericPathIdentifier[] genericAssetPaths,
            @NonNull Class<T> assetType
    ) {
        this(genericAssetPaths, assetType, null);
    }

    public AssetBox(
            @NonNull IGenericPathIdentifier genericAssetPath,
            @NonNull Class<T> assetType,
            @Nullable AssetLoaderParameters<T> assetLoaderParameters
    ) {
        this(new IGenericPathIdentifier[]{ genericAssetPath }, assetType, assetLoaderParameters);
    }

    public AssetBox(
            @NonNull IGenericPathIdentifier genericAssetPath,
            @NonNull Class<T> assetType
    ) {
        this(genericAssetPath, assetType, null);
    }


    protected AssetDescriptor<T> createDescriptor(String fileName, @NonNull FileExtension extension) {
        var realFileName = Paths.get(dir, fileName + extension.toString()).toString();
        var descriptor = new AssetDescriptor<T>(realFileName, assetType, assetLoaderParameters);
        assetDescriptors.add(descriptor);
        return descriptor;
    }

    protected AssetDescriptor<T> createDescriptor(String fileName) {
        return createDescriptor(fileName, FileExtension.NONE);
    }

    protected void addChild(AssetKid<T, AssetBox<T>> childPack) {
        assetKids.add(childPack);
    }
}