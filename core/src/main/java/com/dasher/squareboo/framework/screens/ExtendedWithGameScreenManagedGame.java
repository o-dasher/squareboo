package com.dasher.squareboo.framework.screens;


import com.dasher.squareboo.framework.screens.enums.IScreenEnum;
import com.dasher.squareboo.framework.screens.enums.ITransitionEnum;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import lombok.Getter;
import lombok.Setter;
import text.formic.Stringf;

public abstract class ExtendedWithGameScreenManagedGame
        <S extends WithTransitionGameScreen<?, T>,
                T extends ScreenTransition,
                ES extends IScreenEnum,
                ET extends ITransitionEnum>
        extends ExtendedManagedGame<S, T>  {

    protected ScreenEnumsAnalyzer<S, T, ES> screenEnumsAnalyzer;

    public ExtendedWithGameScreenManagedGame(
            Class<ES> screenEnumClass, Class<ET> transitionEnumClass
    ) {
        super();
        screenEnumsAnalyzer = new ScreenEnumsAnalyzer<>(screenEnumClass, transitionEnumClass);
    }

    @SuppressWarnings("unchecked")
    protected void addScreen(S screen) {
        screenEnumsAnalyzer.addScreenTransitionValueBox(
                (ScreenTransitionValuesBox<S, T>) screen.getValueBox()
        );
        logger.debug(
                Stringf.format(
                        "Added %s for the %s",
                        screen.getClass().getSimpleName(),
                        screenEnumsAnalyzer.getClass().getSimpleName()
                )
        );
    }

    protected void pushScreen(ES screeEnum) {
        getScreenManager().pushScreen(screenEnumsAnalyzer.getEntry(screeEnum));
    }

    protected void unpackEnumAnalyzer() {
        screenEnumsAnalyzer.build();
        var entries = screenEnumsAnalyzer.entryBoxes;
        for (var entry: entries) {
            getScreenManager().addBox(entry);
            logger.debug(Stringf.format("Unpacked: %s", entry.toString()));
        }
    }
}
