package com.jwbutler.rpg.ui.swing;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.ui.Graphics;
import com.jwbutler.rpg.ui.KeyEvent;
import com.jwbutler.rpg.ui.MouseEvent;

import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_WIDTH;
import static com.jwbutler.rpg.ui.swing.SwingEventConverters.convertKeyEvent;
import static com.jwbutler.rpg.ui.swing.SwingEventConverters.convertMouseEvent;

/**
 * Wrapper for Swing components, e.g. {@link JFrame}, which handles both rendering and input handling.
 */
public final class SwingGameWindow implements GameWindow
{
    @Nonnull
    private final JFrame frame;
    @Nonnull
    private final JPanel panel;
    @Nonnull
    private final BufferedImage image;

    private final double ZOOM_RATIO = 2.0;

    public SwingGameWindow()
    {
        image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        panel = new JPanel()
        {
            @Override
            protected void paintComponent(@Nonnull java.awt.Graphics graphics)
            {
                super.paintComponent(graphics);
                graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            }
        };
        frame = new JFrame();
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("RPG 2023");

        frame.setVisible(true);
        var insets = frame.getInsets();
        frame.setSize(
            (int) Math.round(GAME_WIDTH * ZOOM_RATIO) + insets.left + insets.right,
            (int) Math.round(GAME_HEIGHT * ZOOM_RATIO) + insets.top + insets.bottom
        );

        // https://stackoverflow.com/a/144950
        {
            var dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
            frame.setLocation(x, y);
        }
    }

    @Override
    public void render(@Nonnull Consumer<Graphics> consumer)
    {
        consumer.accept(_convertGraphics((Graphics2D) image.getGraphics()));
        panel.repaint();
    }

    @Override
    public void addKeyboardListener(@Nonnull Consumer<KeyEvent> keyListener)
    {
        frame.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(@Nonnull java.awt.event.KeyEvent e)
            {
                var keyEvent = convertKeyEvent(e);
                if (keyEvent != null)
                {
                    keyListener.accept(keyEvent);
                }
            }
        });
    }

    @Override
    public void addMouseDownListener(@Nonnull Consumer<MouseEvent> handler)
    {
        frame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(@Nonnull java.awt.event.MouseEvent e)
            {
                _fixMousePosition(e);
                handler.accept(convertMouseEvent(e));
            }
        });
    }

    @Override
    public void addMouseUpListener(@Nonnull Consumer<MouseEvent> handler)
    {
        frame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(@Nonnull java.awt.event.MouseEvent e)
            {
                _fixMousePosition(e);
                handler.accept(convertMouseEvent(e));
            }
        });
    }

    @Override
    public void addMouseMoveListener(@Nonnull Consumer<MouseEvent> handler)
    {
        frame.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(@Nonnull java.awt.event.MouseEvent e)
            {
                _fixMousePosition(e);
                handler.accept(convertMouseEvent(e));
            }

            @Override
            public void mouseDragged(@Nonnull java.awt.event.MouseEvent e)
            {
                _fixMousePosition(e);
                handler.accept(convertMouseEvent(e));
            }
        });
    }

    @Override
    public void toggleMaximized()
    {
        // https://stackoverflow.com/a/13721612
        var wasMaximized = (frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) > 0;
        frame.dispose();
        frame.setUndecorated(!wasMaximized);
        frame.setExtendedState(frame.getExtendedState() ^ JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private void _fixMousePosition(@Nonnull java.awt.event.MouseEvent e)
    {
        var zoomRatio = 1.0 * (frame.getWidth() - frame.getInsets().left - frame.getInsets().right) / GAME_WIDTH;
        // First, subtract the frame's insets;
        // then, scale it down to the original pixels
        e.translatePoint(
            -frame.getInsets().left,
            -frame.getInsets().top
        );
        e.translatePoint(
            -e.getX() + (int) (e.getX() * (1.0 / zoomRatio)),
            -e.getY() + (int) (e.getY() * (1.0 / zoomRatio))
        );
    }

    @Nonnull
    private static Graphics _convertGraphics(@Nonnull Graphics2D graphics)
    {
        return new SwingGraphics(graphics);
    }
}
