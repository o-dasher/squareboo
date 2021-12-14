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

    public BoxBasedAssetManager(AssetClassesHolder assetClassesHolder, @NonNull AssetBox<?>... assetBoxes) {
        this.assetClassesHolder = assetClassesHolder;
        Arrays.stream(assetBoxes).forEach((box) -> this.assetBoxes.put(box.getClass(), box));
    }

    public BoxBasedAssetManager(AssetClassesHolder assetClassesHolder) {
        this(assetClassesHolder, ((Supplier<AssetBox<?>[]>) () -> {
            var logger = LoggerService.forClass(BoxBasedAssetManager.class);
            var assetBoxes = new Array<AssetBox<?>>(AssetBox.class);
            for (var klass: assetClassesHolder.assetClasses) {
                try {
                    var assetBox = klass.getConstructor().newInstance();
                    logger.debug(
                            Stringf.format(
                                    "Instantiated %s for the %s",
                                    assetBox.getClass().getSimpleName(),
                                    BoxBasedAssetManager.class.getSimpleName()
                            )
                    );
                    assetBoxes.add(assetBox);
                } catch (
                        InstantiationException | IllegalAccessException
                                | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            return assetBoxes.toArray();
        }).get());
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
        if (!assetClassesHolder.assetClasses.contains(klass, false))
            throwClassNotFoundInThis(klass);

        for (var assetBox : assetBoxes.values())
            if (assetBox.getClass() == klass)
                return (A) assetBox;

        throwClassNotFoundInThis(klass);

        return null;
    }

    public void loadBoxes() {
        assetBoxes.values().forEach(this::loadBox);
    }

    @Override
    public void throwClassNotFoundInThis(Class<?> klass) throws ClassNotFoundException {
        AssetOutputHelper.throwClassNotFound(this, klass);
    }
}
