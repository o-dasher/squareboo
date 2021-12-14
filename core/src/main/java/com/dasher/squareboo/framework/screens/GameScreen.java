package com.dasher.squareboo.framework.screens;


import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;

public abstract class GameScreen<T extends ManagedGame<?, ?>> extends ManagedScreen {
    protected final T game;

    public GameScreen(T game) {
        this.game = game;
    }

    @Override
    protected void create() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
