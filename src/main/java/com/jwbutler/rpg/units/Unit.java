package com.jwbutler.rpg.units;

import java.util.Set;
import java.util.UUID;

import com.jwbutler.rpg.core.Game;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.equipment.Equipment;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.sprites.Sprite;
import com.jwbutler.rpg.units.commands.Command;

public interface Unit
{
    @NonNull Game getGame();

    @NonNull
    UUID getId();

    @NonNull
    String getName();

    int getLife();

    int getMaxLife();

    @NonNull
    Activity getActivity();

    @NonNull
    Direction getDirection();

    int getFrameNumber();

    void startActivity(@NonNull Activity activity, @NonNull Direction direction);

    @NonNull
    Command getCommand();

    void setCommand(@NonNull Command command);

    @Nullable
    Command getNextCommand();

    void setNextCommand(@Nullable Command command);

    /**
     * @return "next command" if it exists, else "current command"
     */
    @NonNull
    Command getLatestCommand();

    @NonNull
    Player getPlayer();

    @NonNull
    Level getLevel();

    @NonNull
    Sprite<Unit> getSprite();

    void setLevel(@NonNull Level level);

    @NonNull
    Coordinates getCoordinates();

    void setCoordinates(@NonNull Coordinates coordinates);

    void addEquipment(@NonNull Equipment equipment);

    @NonNull
    Set<Equipment> getEquipment();

    int getAttackDamage();

    void takeDamage(int amount);

    void update();
}
