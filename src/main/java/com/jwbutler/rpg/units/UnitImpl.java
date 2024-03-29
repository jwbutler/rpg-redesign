package com.jwbutler.rpg.units;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.jwbutler.rpg.core.Game;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NonNull;

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

final class UnitImpl implements Unit
{
    @NonNull
    private final Game game;
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
    @Nullable
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
        @NonNull Game game,
        @NonNull String name,
        int life,
        @NonNull AnimatedSprite<Unit> sprite,
        @NonNull Player player,
        @NonNull Level level,
        @NonNull Coordinates coordinates
    )
    {
        this.game = game;
        this.id = UUID.randomUUID();
        this.name = name;
        this.life = life;
        this.maxLife = life;
        this.sprite = sprite;
        activity = Activity.STANDING;
        direction = Direction.SE;
        frameNumber = 0;
        command = null;
        nextCommand = null;
        this.player = player;
        this.level = level;
        this.coordinates = coordinates;
        this.slotToEquipment = new EnumMap<>(Slot.class);
    }

    @NonNull
    @Override
    public Game getGame()
    {
        return game;
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
    public void setCommand(@Nullable Command command)
    {
        this.command = command;
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
        if (isAnimationComplete())
        {
            activity.onComplete(this);
            command = (nextCommand != null) ? nextCommand : command;
            nextCommand = null;
            if (command != null)
            {
                var activityPair = command.getNextActivity(this);
                startActivity(activityPair.activity(), activityPair.direction());
            }
            else
            {
                startActivity(Activity.STANDING, getDirection());
            }
        }
    }
    
    @Override
    public boolean isAnimationComplete()
    {
        int maxFrameNumber = sprite.getAnimation(this).frames().size() - 1;
        return frameNumber > maxFrameNumber;
    }
}
