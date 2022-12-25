package com.jwbutler.rpg.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_WIDTH;

/**
 * Wrapper for Swing components, e.g. {@link JFrame}, which handles both rendering and input handling.
 */
public final class GameWindow
{
    @Nonnull
    private final JFrame frame;
    @Nonnull
    private final JPanel panel;
    @Nonnull
    private final BufferedImage image;

    private final double ZOOM_RATIO = 2.0;

    public GameWindow()
    {
        image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        panel = new JPanel()
        {
            @Override
            protected void paintComponent(@Nonnull Graphics graphics)
            {
                super.paintComponent(graphics);
                graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            }
        };
        frame = new JFrame();
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //frame.setUndecorated(true);

        frame.setVisible(true);
        var insets = frame.getInsets();
        frame.setSize(
            (int) Math.round(GAME_WIDTH * ZOOM_RATIO) + insets.left + insets.right,
            (int) Math.round(GAME_HEIGHT * ZOOM_RATIO) + insets.top + insets.bottom
        );
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // https://stackoverflow.com/a/144950
        {
            var dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
            frame.setLocation(x, y);
        }
    }

    public void render(@Nonnull Consumer<Graphics2D> consumer)
    {
        consumer.accept((Graphics2D) image.getGraphics());
        panel.repaint();
    }

    public void addKeyboardListener(@Nonnull Consumer<KeyEvent> keyListener)
    {
        frame.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(@Nonnull KeyEvent e)
            {
                keyListener.accept(e);
            }
        });
    }

    public void addMouseListener(@Nonnull Consumer<MouseEvent> mouseListener)
    {
        frame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(@Nonnull MouseEvent e)
            {
                // First, subtract the frame's insets;
                // then, scale it down to the original pixels
                e.translatePoint(-frame.getInsets().left, -frame.getInsets().top);
                e.translatePoint(
                    (int) (e.getX() * (-1.0 / ZOOM_RATIO)),
                    (int) (e.getY() * (-1.0 / ZOOM_RATIO))
                );
                mouseListener.accept(e);
            }
        });
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
