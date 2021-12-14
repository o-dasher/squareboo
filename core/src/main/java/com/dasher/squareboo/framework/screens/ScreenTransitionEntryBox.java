package com.dasher.squareboo.framework.screens;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import lombok.Data;

@Data
public class ScreenTransitionEntryBox<S extends ManagedScreen, T extends ScreenTransition> {
    public final ScreenEntry<S> screenEntry;
    public final TransitionEntry<T> transitionEntry;
}
