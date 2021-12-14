package com.dasher.squareboo;


import com.dasher.squareboo.assets.GameAssetClasses;
import com.dasher.squareboo.framework.assets.BoxBasedAssetManager;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;

import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import lombok.SneakyThrows;


public class SquareBoo extends ManagedGame<ManagedScreen, ScreenTransition> {
    private Logger logger;

    private GameAssetClasses assetClasses;
    private BoxBasedAssetManager assetManager;

    @SneakyThrows
    @Override
    public void create() {
        super.create();
        createLogger();
        createAssets();
    }

    private void createLogger() {
        logger = LoggerService.forClass(getClass());
        LoggerService.debug(true);

    }

    private void createAssets() {
        assetClasses = new GameAssetClasses();
        assetManager = new BoxBasedAssetManager(assetClasses);
        assetManager.loadBoxes();

    }

    @Override
    public void render() {
        super.render();
        if (assetManager.update()) {
            if (getScreenManager().getCurrentScreen() == null) {

            }
        }
    }
}