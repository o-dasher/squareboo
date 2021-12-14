package com.dasher.squareboo.screens;


import com.dasher.squareboo.SquareBoo;
import com.dasher.squareboo.framework.screens.WithTransitionGameScreen;

import de.eskalon.commons.screen.transition.ScreenTransition;

public abstract class BooScreen extends WithTransitionGameScreen<SquareBoo, ScreenTransition> {
    public BooScreen(SquareBoo game, ScreenTransition transition) {
        super(game, transition);
    }
}
