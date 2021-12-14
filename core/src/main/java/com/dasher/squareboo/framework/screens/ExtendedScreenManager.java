package com.dasher.squareboo.framework.screens;


import com.dasher.squareboo.framework.screens.enums.IScreenEnum;
import com.dasher.squareboo.framework.screens.enums.ITransitionEnum;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.ScreenManager;
import de.eskalon.commons.screen.transition.ScreenTransition;

public class ExtendedScreenManager<S extends ManagedScreen, T extends ScreenTransition> extends ScreenManager<S, T> {
    public void pushScreen(IScreenEnum screen, ITransitionEnum transition, Object... params) {
        super.pushScreen(screen.toString(), transition.toString(), params);
    }

    public void pushScreen(ScreenTransitionEntryBox<S, T> box, Object... params) {
        pushScreen(box.screenEntry.key, box.transitionEntry.key, params);
    }

    public void addScreen(IScreenEnum screenKey, S screen) {
        super.addScreen(screenKey.toString(), screen);
    }

    public void addScreen(ScreenTransitionEntryBox<S, T> box) {
        addScreen(box.screenEntry.key, box.screenEntry.value);
    }

    public void addScreenTransition(ITransitionEnum transitionKey, T transition) {
        super.addScreenTransition(transitionKey.toString(), transition);
    }

    public void addScreenTransition(ScreenTransitionEntryBox<S, T> box) {
        addScreenTransition(box.transitionEntry.key, box.transitionEntry.value);
    }

    public void addBox(ScreenTransitionEntryBox<S, T> box) {
        addScreen(box);
        addScreenTransition(box);
    }
}
