package com.dasher.squareboo;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dasher.squareboo.assets.GameAssetClasses;
import com.dasher.squareboo.framework.assets.BoxBasedAssetManager;
import com.dasher.squareboo.framework.screens.ExtendedManagedGame;
import com.dasher.squareboo.framework.screens.ExtendedWithGameScreenManagedGame;
import com.dasher.squareboo.framework.screens.ScreenEnumsAnalyzer;
import com.dasher.squareboo.screens.BooScreen;
import com.dasher.squareboo.screens.enums.GameScreensKeys;
import com.dasher.squareboo.screens.enums.GameTransitionKeys;
import com.dasher.squareboo.screens.intro.IntroScreen;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;

import java.lang.reflect.InvocationTargetException;

import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlankTimedTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import lombok.SneakyThrows;


public class SquareBoo extends ExtendedWithGameScreenManagedGame<BooScreen, ScreenTransition, GameScreensKeys, GameTransitionKeys> {
    private GameAssetClasses assetClasses;

    private SpriteBatch spriteBatch;
    private BoxBasedAssetManager assetManager;

    public SquareBoo() {
        super(GameScreensKeys.class, GameTransitionKeys.class);
    }

    @SneakyThrows
    @Override
    public void create() {
        super.create();
        createLogger();
        createAssets();
        createDrawers();
        createScreens();
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

    private void createDrawers() {
        spriteBatch = new SpriteBatch();
    }

    private void createScreens() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        createScreen(IntroScreen.class, new BlendingTransition(spriteBatch, 0.25f));
        unpackEnumAnalyzer();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);

        if (assetManager.update()) {
            if (getScreenManager().getCurrentScreen() == null) {
                getScreenManager().pushScreen(screenEnumsAnalyzer.getEntry(GameScreensKeys.INTRO));
            }
        }

        super.render();
    }
}