package com.jwbutler.rpg.ui;

import java.util.Set;
import javax.annotation.Nonnull;

public record KeyEvent
(
    @Nonnull Key key,
    @Nonnull Set<Modifier> modifiers
)
{
    public enum Key
    {
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J,
        K,
        L,
        M,
        N,
        O,
        P,
        Q,
        R,
        S,
        T,
        U,
        V,
        W,
        X,
        Y,
        Z,
        _0,
        _1,
        _2,
        _3,
        _4,
        _5,
        _6,
        _7,
        _8,
        _9,
        UP,
        DOWN,
        LEFT,
        RIGHT,
        ENTER,
        SPACE
    }

    public enum Modifier
    {
        SHIFT,
        ALT,
        CTRL
    }
}
