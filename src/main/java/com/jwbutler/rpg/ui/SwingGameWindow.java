package com.jwbutler.rpg.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jwbutler.rpg.geometry.Pixel;

import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_HEIGHT;
import static com.jwbutler.rpg.geometry.GeometryConstants.GAME_WIDTH;

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
    public void render(@Nonnull Consumer<Graphics2D> consumer)
    {
        consumer.accept((Graphics2D) image.getGraphics());
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
                keyListener.accept(_convertKeyEvent(e));
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
                handler.accept(_convertMouseEvent(e));
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
                handler.accept(_convertMouseEvent(e));
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
                handler.accept(_convertMouseEvent(e));
            }

            @Override
            public void mouseDragged(@Nonnull java.awt.event.MouseEvent e)
            {
                _fixMousePosition(e);
                handler.accept(_convertMouseEvent(e));
            }
        });
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

    @Nonnull
    private static MouseEvent _convertMouseEvent(@Nonnull java.awt.event.MouseEvent event)
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

    @CheckForNull
    private static KeyEvent _convertKeyEvent(@Nonnull java.awt.event.KeyEvent event)
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
}
