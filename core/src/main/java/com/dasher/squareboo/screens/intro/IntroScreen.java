package com.dasher.squareboo.screens.intro;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dasher.squareboo.SquareBoo;
import com.dasher.squareboo.screens.BooScreen;



public class IntroScreen extends BooScreen {
    public IntroScreen(SquareBoo game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
    }
}
