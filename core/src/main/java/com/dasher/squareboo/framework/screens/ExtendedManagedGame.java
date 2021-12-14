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

    /**
     * This method only works if said class has only one constructor
     * @param tClass The class of the screen to be created
     * @param initArgs args for building the class
     * @return A new instance of that class
     */
    @SuppressWarnings("unchecked")
    public <I extends S> I createScreen(Class<I> tClass, Object... initArgs) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        logger.debug(Stringf.format("Trying to instantiate: %s", tClass.getSimpleName()));

        var constructor = tClass.getConstructors()[0];
        I screen = null;

        for (
                var sClass = tClass.getSuperclass();
                sClass != null;
                sClass = sClass.getSuperclass()
        ) {
            if (sClass.equals(GameScreen.class)) {
                screen = (I) constructor.newInstance(this);
            } else if (sClass.equals(WithTransitionGameScreen.class)) {
                screen = (I) constructor.newInstance(this);
            }
        }

        for (var arg: initArgs) {
            if (screen instanceof WithTransitionGameScreen) {
                if (arg instanceof ScreenTransition) {
                    ((WithTransitionGameScreen<?, T>) screen).transition = (T) arg;
                }
            }
        }

        if (screen == null) throw new IllegalArgumentException();
        logger.debug(Stringf.format("Instantiated screen of class: %s", screen.getClass().getSimpleName()));
        return screen;
    }
}
