package com.dasher.squareboo.framework.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dasher.squareboo.framework.io.OutputDefaults;
import com.dasher.squareboo.framework.io.OutputTemplates;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Supplier;

import lombok.NonNull;
import lombok.SneakyThrows;
import text.formic.Stringf;

public class BoxBasedAssetManager extends AssetManager implements IThrowsClassNotInInstance {
    protected final Logger logger = LoggerService.forClass(getClass());

    protected final ObjectMap<Class<?>, AssetBox<?>> assetBoxes = new ObjectMap<>();
    protected AssetClassesHolder assetClassesHolder;

    /**
     * @param assetClassesHolder The box which contains all asset boxes that this
     *                           {@link BoxBasedAssetManager} can use
     * @param assetBoxes Instances of {@link AssetBox} to be used on
     *                  this {@link BoxBasedAssetManager} their classes must be included
     *                  on this instance {@link AssetClassesHolder}'s otherwise
     *                  {@link ClassNotFoundException} will be thrown
     * @throws ClassNotFoundException thrown when a {@link AssetBox} class
     * isn't present on this instance {@link AssetClassesHolder}'s
     */
    public BoxBasedAssetManager(AssetClassesHolder assetClassesHolder, @NonNull AssetBox<?>... assetBoxes) throws ClassNotFoundException {
        this.assetClassesHolder = assetClassesHolder;
        for (AssetBox<?> assetBox : assetBoxes) {
            addBox(assetBox);
        }
    }

    @SuppressWarnings("unchecked")
    private void addBox(AssetBox<?> assetBox) throws ClassNotFoundException {
        var assetClass = assetBox.getClass();
        verifyIfAssetClassExists((Class<? extends AssetBox<?>>) assetClass);
        assetBoxes.put(assetClass, assetBox);
    }

    private void loadBox(@NonNull AssetBox<?> assetBox) {
        logger.debug(
                OutputTemplates.createDefaultShow(
                        OutputDefaults.LOADING,
                        assetBox.getClass(),
                        assetBox.dir
                )
        );
        assetBox.assetDescriptors.forEach(descriptor -> {
            load(descriptor);
            logger.debug(
                    OutputTemplates.createDefaultShow(
                            OutputDefaults.LOADED,
                            descriptor.type,
                            descriptor.fileName
                    )
            );
        });
        assetBox.assetKids.forEach(this::loadBox);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <A extends AssetBox<?>> A get(Class<A> klass) {
        verifyIfAssetClassExists(klass);

        for (var assetBox : assetBoxes.values())
            if (assetBox.getClass() == klass)
                return (A) assetBox;

        throwClassNotFoundInThis(klass);

        return null;
    }

    public void verifyIfAssetClassExists(Class<? extends AssetBox<?>> klass) throws ClassNotFoundException {
        if (!assetClassesHolder.assetClasses.contains(klass, false))
            throwClassNotFoundInThis(klass);
    }

    public void loadBoxes() {
        assetBoxes.values().forEach(this::loadBox);
    }

    @Override
    public void throwClassNotFoundInThis(Class<?> klass) throws ClassNotFoundException {
        AssetOutputHelper.throwClassNotFound(this, klass);
    }
}
