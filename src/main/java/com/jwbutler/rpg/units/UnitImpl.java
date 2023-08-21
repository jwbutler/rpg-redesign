package com.jwbutler.rpg.units;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.equipment.Equipment;
import com.jwbutler.rpg.equipment.Slot;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.sprites.AnimatedSprite;
import com.jwbutler.rpg.sprites.Sprite;
import com.jwbutler.rpg.units.commands.Command;

import static com.jwbutler.rpg.util.Preconditions.checkArgument;
import static com.jwbutler.rpg.units.commands.Command.defaultCommand;

final class UnitImpl implements Unit
{
    @NonNull
    private final GameController controller;
    @NonNull
    private final UUID id;
    @NonNull
    private final String name;
    private int life;
    private int maxLife;
    @NonNull
    private final AnimatedSprite<Unit> sprite;
    @NonNull
    private Activity activity;
    @NonNull
    private Direction direction;
    private int frameNumber;
    @NonNull
    private Command command;
    @Nullable
    private Command nextCommand;
    @NonNull
    private Player player;
    @NonNull
    private Level level;
    @NonNull
    private Coordinates coordinates;
    @NonNull
    private final Map<Slot, Equipment> slotToEquipment;

    UnitImpl(
        @NonNull GameController controller,
        @NonNull String name,
        int life,
        @NonNull AnimatedSprite<Unit> sprite,
        @NonNull Player player,
        @NonNull Level level,
        @NonNull Coordinates coordinates
    )
    {
        this.controller = controller;
        this.id = UUID.randomUUID();
        this.name = name;
        this.life = life;
        this.maxLife = life;
        this.sprite = sprite;
        activity = Activity.STANDING;
        direction = Direction.SE;
        frameNumber = 0;
        command = defaultCommand();
        nextCommand = null;
        this.player = player;
        this.level = level;
        this.coordinates = coordinates;
        this.slotToEquipment = new EnumMap<>(Slot.class);
    }

    @NonNull
    @Override
    public GameController getController()
    {
        return controller;
    }

    @Override
    @NonNull
    public UUID getId()
    {
        return id;
    }

    @Override
    @NonNull
    public String getName()
    {
        return name;
    }

    @Override
    public int getLife()
    {
        return life;
    }

    @Override
    public int getMaxLife()
    {
        return maxLife;
    }

    @Override
    @NonNull
    public Activity getActivity()
    {
        return activity;
    }

    @Override
    @NonNull
    public Direction getDirection()
    {
        return direction;
    }

    @Override
    public int getFrameNumber()
    {
        return frameNumber;
    }

    @Override
    public void startActivity(@NonNull Activity activity, @NonNull Direction direction)
    {
        this.activity = activity;
        this.direction = direction;
        this.frameNumber = 0;
    }

    @Override
    @NonNull
    public Command getCommand()
    {
        return command;
    }

    @Override
    public void setCommand(@NonNull Command command)
    {
        this.command = command;
    }

    @Nullable
    @Override
    public Command getNextCommand()
    {
        return nextCommand;
    }

    @Override
    public void setNextCommand(@Nullable Command command)
    {
        nextCommand = command;
    }

    @Override
    @NonNull
    public Command getLatestCommand()
    {
        return (nextCommand != null) ? nextCommand : command;
    }

    @NonNull
    @Override
    public Player getPlayer()
    {
        return player;
    }

    @Override
    @NonNull
    public Level getLevel()
    {
        return level;
    }

    @NonNull
    @Override
    public Sprite<Unit> getSprite()
    {
        return sprite;
    }

    @Override
    public void setLevel(@NonNull Level level)
    {
        this.level = level;
    }

    @NonNull
    @Override
    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    @Override
    public void setCoordinates(@NonNull Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }

    @Override
    public void addEquipment(@NonNull Equipment equipment)
    {
        checkArgument(slotToEquipment.get(equipment.getSlot()) == null);
        slotToEquipment.put(equipment.getSlot(), equipment);
    }

    @NonNull
    @Override
    public Set<Equipment> getEquipment()
    {
        return new HashSet<>(slotToEquipment.values());
    }

    @Override
    public int getAttackDamage()
    {
        return slotToEquipment.values()
            .stream()
            .mapToInt(Equipment::getDamage)
            .sum()
            + 10;
    }

    @Override
    public void takeDamage(int amount)
    {
        life = Math.max(life - amount, 0);
    }

    @Override
    public void update()
    {
        frameNumber++;
        if (frameNumber > _getMaxFrameNumber())
        {
            activity.onComplete(this);
            command = (nextCommand != null) ? nextCommand : command;
            nextCommand = null;
            var activityPair = command.getNextActivity(this);
            startActivity(activityPair.activity(), activityPair.direction());
        }
    }

    private int _getMaxFrameNumber()
    {
        return sprite.getAnimation(this).frames().size() - 1;
    }
}
