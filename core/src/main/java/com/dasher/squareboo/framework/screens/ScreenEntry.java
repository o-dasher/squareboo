package com.dasher.squareboo.framework.screens;

import com.dasher.squareboo.framework.maps.MapEntry;
import com.dasher.squareboo.framework.screens.enums.IScreenEnum;

import de.eskalon.commons.screen.ManagedScreen;

public class ScreenEntry<S extends ManagedScreen> extends MapEntry<IScreenEnum, S> {
    public ScreenEntry(IScreenEnum key, S value) {
        super(key, value);
    }
}
