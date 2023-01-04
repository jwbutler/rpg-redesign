package com.jwbutler.rpg.ui.swing;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.ui.KeyEvent;
import com.jwbutler.rpg.ui.MouseEvent;

final class SwingEventConverters
{
    private SwingEventConverters() {}

    @CheckForNull
    static KeyEvent convertKeyEvent(@Nonnull java.awt.event.KeyEvent event)
    {
        var key = switch (event.getKeyCode())
        {
            case java.awt.event.KeyEvent.VK_A -> KeyEvent.Key.A;
            case java.awt.event.KeyEvent.VK_B -> KeyEvent.Key.B;
            case java.awt.event.KeyEvent.VK_C -> KeyEvent.Key.C;
            case java.awt.event.KeyEvent.VK_D -> KeyEvent.Key.D;
            case java.awt.event.KeyEvent.VK_E -> KeyEvent.Key.E;
            case java.awt.event.KeyEvent.VK_F -> KeyEvent.Key.F;
            case java.awt.event.KeyEvent.VK_G -> KeyEvent.Key.G;
            case java.awt.event.KeyEvent.VK_H -> KeyEvent.Key.H;
            case java.awt.event.KeyEvent.VK_I -> KeyEvent.Key.I;
            case java.awt.event.KeyEvent.VK_J -> KeyEvent.Key.J;
            case java.awt.event.KeyEvent.VK_K -> KeyEvent.Key.K;
            case java.awt.event.KeyEvent.VK_L -> KeyEvent.Key.L;
            case java.awt.event.KeyEvent.VK_M -> KeyEvent.Key.M;
            case java.awt.event.KeyEvent.VK_N -> KeyEvent.Key.N;
            case java.awt.event.KeyEvent.VK_O -> KeyEvent.Key.O;
            case java.awt.event.KeyEvent.VK_P -> KeyEvent.Key.P;
            case java.awt.event.KeyEvent.VK_Q -> KeyEvent.Key.Q;
            case java.awt.event.KeyEvent.VK_R -> KeyEvent.Key.R;
            case java.awt.event.KeyEvent.VK_S -> KeyEvent.Key.S;
            case java.awt.event.KeyEvent.VK_T -> KeyEvent.Key.T;
            case java.awt.event.KeyEvent.VK_U -> KeyEvent.Key.U;
            case java.awt.event.KeyEvent.VK_V -> KeyEvent.Key.V;
            case java.awt.event.KeyEvent.VK_W -> KeyEvent.Key.W;
            case java.awt.event.KeyEvent.VK_X -> KeyEvent.Key.X;
            case java.awt.event.KeyEvent.VK_Y -> KeyEvent.Key.Y;
            case java.awt.event.KeyEvent.VK_Z -> KeyEvent.Key.Z;
            case java.awt.event.KeyEvent.VK_0 -> KeyEvent.Key._0;
            case java.awt.event.KeyEvent.VK_1 -> KeyEvent.Key._1;
            case java.awt.event.KeyEvent.VK_2 -> KeyEvent.Key._2;
            case java.awt.event.KeyEvent.VK_3 -> KeyEvent.Key._3;
            case java.awt.event.KeyEvent.VK_4 -> KeyEvent.Key._4;
            case java.awt.event.KeyEvent.VK_5 -> KeyEvent.Key._5;
            case java.awt.event.KeyEvent.VK_6 -> KeyEvent.Key._6;
            case java.awt.event.KeyEvent.VK_7 -> KeyEvent.Key._7;
            case java.awt.event.KeyEvent.VK_8 -> KeyEvent.Key._8;
            case java.awt.event.KeyEvent.VK_9 -> KeyEvent.Key._9;
            case java.awt.event.KeyEvent.VK_UP -> KeyEvent.Key.UP;
            case java.awt.event.KeyEvent.VK_DOWN -> KeyEvent.Key.DOWN;
            case java.awt.event.KeyEvent.VK_LEFT -> KeyEvent.Key.LEFT;
            case java.awt.event.KeyEvent.VK_RIGHT -> KeyEvent.Key.RIGHT;
            case java.awt.event.KeyEvent.VK_ENTER -> KeyEvent.Key.ENTER;
            case java.awt.event.KeyEvent.VK_SPACE -> KeyEvent.Key.SPACE;
            case default -> null;
        };

        if (key == null)
        {
            return null;
        }

        var modifiers = EnumSet.noneOf(KeyEvent.Modifier.class);
        if ((event.getModifiersEx() & java.awt.event.KeyEvent.ALT_DOWN_MASK) > 0)
        {
            modifiers.add(KeyEvent.Modifier.ALT);
        }
        if ((event.getModifiersEx() & java.awt.event.KeyEvent.SHIFT_DOWN_MASK) > 0)
        {
            modifiers.add(KeyEvent.Modifier.SHIFT);
        }
        if ((event.getModifiersEx() & java.awt.event.KeyEvent.CTRL_DOWN_MASK) > 0)
        {
            modifiers.add(KeyEvent.Modifier.CTRL);
        }

        return new KeyEvent(key, modifiers);
    }

    @Nonnull
    static MouseEvent convertMouseEvent(@Nonnull java.awt.event.MouseEvent event)
    {
        final MouseEvent.Button button;
        if (SwingUtils.isRightButton(event))
        {
            button = MouseEvent.Button.RIGHT;
        }
        else if (SwingUtils.isLeftButton(event))
        {
            button = MouseEvent.Button.LEFT;
        }
        else
        {
            button = null;
        }

        Set<MouseEvent.Button> heldButtons = new HashSet<>();
        if (SwingUtils.isLeftButtonDown(event))
        {
            heldButtons.add(MouseEvent.Button.LEFT);
        }
        if (SwingUtils.isRightButtonDown(event))
        {
            heldButtons.add(MouseEvent.Button.RIGHT);
        }

        return new MouseEvent(
            button,
            heldButtons,
            new Pixel(event.getX(), event.getY())
        );
    }
}
