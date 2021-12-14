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
        <S extends ManagedScreen, T extends ScreenTransition,
                ES extends IScreenEnum, ET extends ITransitionEnum>
        extends ExtendedManagedGame<S, T>  {
    protected ScreenEnumsAnalyzer<S, T, ES> screenEnumsAnalyzer;

    public ExtendedWithGameScreenManagedGame(
            Class<ES> screenEnumClass, Class<ET> transitionEnumClass
    ) {
        super();
        screenEnumsAnalyzer = new ScreenEnumsAnalyzer<>(screenEnumClass, transitionEnumClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <I extends S> I createScreen(Class<I> tClass, Object... initArgs) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        I screen = super.createScreen(tClass, initArgs);
        if (!(screen instanceof WithTransitionGameScreen)) {
            throw new IllegalArgumentException(
                    Stringf.format(
                            "%s only accepts screens of class: %s",
                            getClass().getSimpleName(),
                            WithTransitionGameScreen.class.getSimpleName()
                    )
            );
        }

        var valueBox = (ScreenTransitionValuesBox<S, T>) ((WithTransitionGameScreen<?, T>) screen)
                .getValueBox();

        screenEnumsAnalyzer.addScreenTransitionValueBox(valueBox);

        logger.debug(
                Stringf.format(
                        "Added %s for the %s",
                        screen.getClass().getSimpleName(),
                        screenEnumsAnalyzer.getClass().getSimpleName()
                )
        );
        return screen;
    }

    protected void unpackEnumAnalyzer() {
        screenEnumsAnalyzer.build();
        var entries = screenEnumsAnalyzer.entryBoxes.values();
        for (var entry: entries) {
            getScreenManager().addBox(entry);
            logger.debug(Stringf.format("Unpacked: %s", entry.toString()));
        }
    }
}
