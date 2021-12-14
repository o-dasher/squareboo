package com.dasher.squareboo.framework.screens;

import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.transition.ScreenTransition;
import lombok.Getter;
import lombok.Setter;

public abstract class WithTransitionGameScreen<T extends ManagedGame<?, ?>, C extends ScreenTransition> extends GameScreen<T> {
    @Getter @Setter
    protected C transition;

    public WithTransitionGameScreen(T game) {
        super(game);
    }

    public WithTransitionGameScreen(T game, C transition) {
        super(game);
        this.transition = transition;
    }

    public ScreenTransitionValuesBox<WithTransitionGameScreen<T, C>, C> getValueBox() {
        return new ScreenTransitionValuesBox<>(this, transition);
    }
}
