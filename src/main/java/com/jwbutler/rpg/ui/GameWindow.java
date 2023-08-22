package com.jwbutler.rpg.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_WIDTH;

/**
 * Wrapper for Swing components, e.g. {@link JFrame}, which handles both rendering and input handling.
 */
public final class GameWindow
{
    @NonNull
    private final JFrame frame;
    @NonNull
    private final JPanel panel;
    @NonNull
    private final BufferedImage image;

    private static final double ZOOM_RATIO = 4.0;

    public GameWindow()
    {
        image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        panel = new JPanel()
        {
            @Override
            protected void paintComponent(@NonNull Graphics graphics)
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

    public void render(@NonNull Consumer<Graphics2D> consumer)
    {
        consumer.accept((Graphics2D) image.getGraphics());
        panel.repaint();
    }

    public void addKeyboardListener(@NonNull Consumer<KeyEvent> keyListener)
    {
        frame.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(@NonNull KeyEvent e)
            {
                keyListener.accept(e);
            }
        });
    }

    public void addMouseDownListener(@NonNull Consumer<MouseEvent> handler)
    {
        frame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(@NonNull MouseEvent e)
            {
                _fixMousePosition(e);
                handler.accept(e);
            }
        });
    }

    public void addMouseUpListener(@NonNull Consumer<MouseEvent> handler)
    {
        frame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(@NonNull MouseEvent e)
            {
                _fixMousePosition(e);
                handler.accept(e);
            }
        });
    }

    public void addMouseMoveListener(@NonNull Consumer<MouseEvent> handler)
    {
        frame.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(@NonNull MouseEvent e)
            {
                _fixMousePosition(e);
                handler.accept(e);
            }

            @Override
            public void mouseDragged(@NonNull MouseEvent e)
            {
                _fixMousePosition(e);
                handler.accept(e);
            }
        });
    }

    private void _fixMousePosition(@NonNull MouseEvent e)
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

    public void toggleMaximized()
    {
        // https://stackoverflow.com/a/13721612
        var wasMaximized = (frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) > 0;
        frame.dispose();
        frame.setUndecorated(!wasMaximized);
        frame.setExtendedState(frame.getExtendedState() ^ JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
