package com.jwbutler.rpg.ui;

import java.awt.event.MouseEvent;
import javax.annotation.Nonnull;

final class SwingUtils
{
    private SwingUtils() {}

    static boolean isRightButtonDown(@Nonnull MouseEvent event)
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

    static boolean isRightButton(@Nonnull MouseEvent event)
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

    static boolean isLeftButtonDown(@Nonnull MouseEvent event)
    {
        return (event.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) > 0;
    }

    static boolean isLeftButton(@Nonnull MouseEvent event)
    {
        return event.getButton() == MouseEvent.BUTTON1;
    }
}
