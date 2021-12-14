package com.dasher.squareboo.framework.screens;

import com.github.czyzby.kiwi.log.Logger;

import java.lang.reflect.InvocationTargetException;

import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import text.formic.Stringf;

public class ExtendedManagedGame<S extends ManagedScreen, T extends ScreenTransition> extends ManagedGame<S, T> {
    protected Logger logger;

    public ExtendedManagedGame() {
        screenManager = new ExtendedScreenManager<>();
    }

    @Override
    public ExtendedScreenManager<S, T> getScreenManager() {
        return (ExtendedScreenManager<S, T>) super.getScreenManager();
    }
}
