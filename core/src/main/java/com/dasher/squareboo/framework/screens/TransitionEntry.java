package com.dasher.squareboo.framework.screens;

import com.dasher.squareboo.framework.maps.MapEntry;
import com.dasher.squareboo.framework.screens.enums.ITransitionEnum;

import de.eskalon.commons.screen.transition.ScreenTransition;

public class TransitionEntry<T extends ScreenTransition> extends MapEntry<ITransitionEnum, T> {
    public TransitionEntry(ITransitionEnum key, T value) {
        super(key, value);
    }
}
