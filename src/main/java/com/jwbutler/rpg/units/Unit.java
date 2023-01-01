package com.jwbutler.rpg.units;

import java.util.Set;
import java.util.UUID;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.equipment.Equipment;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.sprites.Sprite;
import com.jwbutler.rpg.units.commands.Command;

public interface Unit
{
    @Nonnull
    GameController getController();

    @Nonnull
    UUID getId();

    @Nonnull
    String getName();

    int getLife();
    int getMaxLife();

    @Nonnull
    Activity getActivity();
    @Nonnull
    Direction getDirection();
    int getFrameNumber();

    void startActivity(@Nonnull Activity activity, @Nonnull Direction direction);

    @Nonnull
    Command getCommand();
    void setCommand(@Nonnull Command command);
    @CheckForNull
    Command getNextCommand();
    void setNextCommand(@CheckForNull Command command);

    /**
     * @return "next command" if it exists, else "current command"
     */
    @Nonnull
    Command getLatestCommand();

    @Nonnull
    Player getPlayer();

    @Nonnull
    Level getLevel();

    @Nonnull
    Sprite<Unit> getSprite();

    void setLevel(@Nonnull Level level);

    @Nonnull
    Coordinates getCoordinates();
    void setCoordinates(@Nonnull Coordinates coordinates);

    void addEquipment(@Nonnull Equipment equipment);
    @Nonnull
    Set<Equipment> getEquipment();

    int getAttackDamage();
    void takeDamage(int amount);

    void update();
}
