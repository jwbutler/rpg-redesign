package com.jwbutler.rpg.ui;

import java.awt.event.MouseEvent;

import org.jspecify.annotations.NonNull;

final class InputUtils
{
    private InputUtils() {}

    static boolean isRightButtonDown(@NonNull MouseEvent event)
    {
        if ((event.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) > 0)
        {
            return true;
        }
        if (event.isControlDown() && (MouseEvent.BUTTON1_DOWN_MASK > 0))
        {
            return true;
        }
        return false;
    }

    static boolean isRightButton(@NonNull MouseEvent event)
    {
        if (event.getButton() == MouseEvent.BUTTON3)
        {
            return true;
        }
        if (event.isControlDown() && event.getButton() == MouseEvent.BUTTON1)
        {
            return true;
        }
        return false;
    }

    static boolean isLeftButtonDown(@NonNull MouseEvent event)
    {
        return (event.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) > 0;
    }

    static boolean isLeftButton(@NonNull MouseEvent event)
    {
        return event.getButton() == MouseEvent.BUTTON1;
    }
}
