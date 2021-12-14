package com.dasher.squareboo.framework.screens;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dasher.squareboo.framework.io.OutputTemplates;
import com.dasher.squareboo.framework.screens.enums.IScreenEnum;
import com.dasher.squareboo.framework.screens.enums.ITransitionEnum;

import java.util.Arrays;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;


public class ScreenEnumsAnalyzer<S extends ManagedScreen, T extends ScreenTransition, ES extends IScreenEnum> {
    protected final ObjectMap<Class<? extends S>, ScreenTransitionEntryBox<S, T>> entryBoxes = new ObjectMap<>();
    private final Array<ScreenTransitionValuesBox<S, T>> valuesBoxes = new Array<>();

    public final Array<Object> screenEnumValues = new Array<>();
    public final Array<Object> transitionEnumValues = new Array<>();

    @SuppressWarnings("unchecked")
    public ScreenEnumsAnalyzer(
            Class<? extends IScreenEnum> screenEnum,
            Class<? extends ITransitionEnum> transitionEnum,
            ScreenTransitionValuesBox<?, ?>... screenTransitionValuesBoxes
    ) {
        Arrays.stream(screenTransitionValuesBoxes).forEach((v) ->
                addScreenTransitionValueBox((ScreenTransitionValuesBox<S, T>) v)
        );
        loopEnum(screenEnum, screenEnumValues);
        loopEnum(transitionEnum, transitionEnumValues);
        if (screenEnumValues.size != transitionEnumValues.size) {
            throw new IllegalStateException(
                    "screenEnumValues size doesn't match transitionEnumValues size"
            );
        }
    }

    public void addScreenTransitionValueBox(ScreenTransitionValuesBox<S, T> valueBox) {
        valuesBoxes.add(valueBox);
    }

    @SuppressWarnings("unchecked")
    public void build() {
        for (int i = 0; i < valuesBoxes.size; i++) {
            var sV = (IScreenEnum) screenEnumValues.get(i);
            var tV = (ITransitionEnum) transitionEnumValues.get(i);
            var vBox = valuesBoxes.get(i);
            var sEntry = new ScreenEntry<S>(sV, vBox.managedScreen);
            var tEntry = new TransitionEntry<T>(tV, vBox.transition);
            var screenBox = new ScreenTransitionEntryBox<>(sEntry, tEntry);
            entryBoxes.put((Class<S>) screenBox.screenEntry.value.getClass(), screenBox);
        }
    }

    public ScreenTransitionEntryBox<S, T> getEntry(ES screenEnum) {
        for (var entry: entryBoxes.values()) {
            if (entry.screenEntry.key == screenEnum) {
                return entry;
            }
        }
        throw new IllegalArgumentException(
                OutputTemplates.createNotFoundOn(getClass(), screenEnum.getClass())
        );
    }

    private void loopEnum(Class<?> anyEnum, Array<Object> array) {
        Arrays.stream(anyEnum.getEnumConstants()).forEach(array::add);
    }
}
