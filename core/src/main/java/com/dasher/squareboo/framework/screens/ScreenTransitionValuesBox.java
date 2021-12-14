package com.dasher.squareboo.framework.screens;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import lombok.Data;

@Data
public class ScreenTransitionValuesBox<S extends ManagedScreen, T extends ScreenTransition> {
    public final S managedScreen;
    public final T transition;
}
