package com.jwbutler.rpg.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Wrapper for Swing components, e.g. {@link JFrame}, which handles both rendering and input handling.
 */
public final class GameWindow
{
    /**
     * internal width, since the {@link JFrame}'s computed width includes insets
     */
    public static final int WIDTH = 640;
    /**
     * internal height, since the {@link JFrame}'s computed height includes insets
     */
    public static final int HEIGHT = 360;

    @Nonnull
    private final JFrame frame;
    @Nonnull
    private final JPanel panel;
    @Nonnull
    private final BufferedImage image;

    public GameWindow()
    {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
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
            2 * (WIDTH + insets.left + insets.right),
            2 * (HEIGHT + insets.top + insets.bottom)
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
}
