package com.jwbutler.rpg.units;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

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
import com.jwbutler.rpg.units.commands.StayCommand;

import static com.google.common.base.Preconditions.checkArgument;

final class UnitImpl implements Unit
{
    @Nonnull
    private final GameController controller;
    @Nonnull
    private final UUID id;
    @Nonnull
    private final String name;
    private int life;
    private int maxLife;
    @Nonnull
    private final AnimatedSprite<Unit> sprite;
    @Nonnull
    private Activity activity;
    @Nonnull
    private Direction direction;
    private int frameNumber;
    @Nonnull
    private Command command;
    @CheckForNull
    private Command nextCommand;
    @Nonnull
    private Player player;
    @Nonnull
    private Level level;
    @Nonnull
    private Coordinates coordinates;
    @Nonnull
    private final Map<Slot, Equipment> slotToEquipment;

    UnitImpl(
        @Nonnull GameController controller,
        @Nonnull String name,
        int life,
        @Nonnull AnimatedSprite<Unit> sprite,
        @Nonnull Player player,
        @Nonnull Level level,
        @Nonnull Coordinates coordinates
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
        command = new StayCommand(controller);
        nextCommand = null;
        this.player = player;
        this.level = level;
        this.coordinates = coordinates;
        this.slotToEquipment = new EnumMap<>(Slot.class);
    }

    @Override
    @Nonnull
    public UUID getId()
    {
        return id;
    }

    @Override
    @Nonnull
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
    @Nonnull
    public Activity getActivity()
    {
        return activity;
    }

    @Override
    @Nonnull
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
    public void startActivity(@Nonnull Activity activity, @Nonnull Direction direction)
    {
        this.activity = activity;
        this.direction = direction;
        this.frameNumber = 0;
    }

    @Override
    @Nonnull
    public Command getCommand()
    {
        return command;
    }

    @Override
    public void setCommand(@Nonnull Command command)
    {
        this.command = command;
    }

    @CheckForNull
    @Override
    public Command getNextCommand()
    {
        return nextCommand;
    }

    @Override
    public void setNextCommand(@CheckForNull Command command)
    {
        nextCommand = command;
    }

    @Override
    @Nonnull
    public Command getLatestCommand()
    {
        return (nextCommand != null) ? nextCommand : command;
    }

    @Nonnull
    @Override
    public Player getPlayer()
    {
        return player;
    }

    @Override
    @Nonnull
    public Level getLevel()
    {
        return level;
    }

    @Nonnull
    @Override
    public Sprite<Unit> getSprite()
    {
        return sprite;
    }

    @Override
    public void setLevel(@Nonnull Level level)
    {
        this.level = level;
    }

    @Nonnull
    @Override
    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    @Override
    public void setCoordinates(@Nonnull Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }

    @Override
    public void addEquipment(@Nonnull Equipment equipment)
    {
        checkArgument(slotToEquipment.get(equipment.getSlot()) == null);
        slotToEquipment.put(equipment.getSlot(), equipment);
    }

    @Nonnull
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
            command.endActivity(this);
            command = (nextCommand != null) ? nextCommand : command;
            nextCommand = null;
            command.startNextActivity(this);
        }
    }

    private int _getMaxFrameNumber()
    {
        return sprite.getAnimation(this).frames().size() - 1;
    }
}
