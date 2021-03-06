package com.dasher.squareboo.framework.screens;

import com.badlogic.gdx.utils.Array;
import com.dasher.squareboo.framework.io.OutputTemplates;
import com.dasher.squareboo.framework.screens.enums.IScreenEnum;
import com.dasher.squareboo.framework.screens.enums.ITransitionEnum;

import java.util.Arrays;
import java.util.function.Supplier;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import lombok.Getter;
import lombok.NonNull;


public class ScreenEnumsAnalyzer<S extends ManagedScreen, T extends ScreenTransition, ES extends IScreenEnum> {
    public final Array<Object> screenEnumValues = new Array<>();
    public final Array<Object> transitionEnumValues = new Array<>();

    @Getter
    private final Array<ScreenTransitionEntryBox<S, T>> entryBoxes = new Array<>(ScreenTransitionEntryBox.class);

    @Getter
    private final Array<ScreenTransitionValuesBox<S, T>> valuesBoxes = new Array<>(ScreenTransitionValuesBox.class);

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

    /**
     * Creates {@link ScreenTransitionEntryBox}s that are added to an internal array to be later
     * unpacked by a {@link ExtendedWithGameScreenManagedGame}
     * through the {@link ExtendedWithGameScreenManagedGame#unpackEnumAnalyzer()} method or other
     */
    public void build() {
        for (int i = 0; i < valuesBoxes.size; i++) {
            var sV = (IScreenEnum) screenEnumValues.get(i);
            var tV = (ITransitionEnum) transitionEnumValues.get(i);
            var vBox = valuesBoxes.get(i);
            var sEntry = new ScreenEntry<S>(sV, vBox.managedScreen);
            var tEntry = new TransitionEntry<T>(tV, vBox.transition);
            var screenBox = new ScreenTransitionEntryBox<>(sEntry, tEntry);
            entryBoxes.add(screenBox);
        }
    }

    public ScreenTransitionEntryBox<S, T> getEntry(ES screenEnum) {
        return Arrays.stream(entryBoxes.items)
                .filter((s) -> s.screenEntry.key == screenEnum)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        OutputTemplates.createNotFoundOn(getClass(), screenEnum.getClass())
                ));
    }

    private void loopEnum(@NonNull Class<?> anyEnum, @NonNull Array<Object> array) {
        Arrays.stream(anyEnum.getEnumConstants()).forEach(array::add);
    }
}
