package com.jwbutler.rpg.core;

import java.util.Set;

import com.jwbutler.rpg.geometry.Camera;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * With an eye toward multiplayer support - this is a container for player-specific data
 */
public interface Session_Warpath
{
    enum SessionState
    {
        TITLE_SCREEN,
        GAME
    }

    @NonNull
    Player getPlayer();

    @NonNull Level getCurrentLevel();

    void setCurrentLevel(@NonNull Level currentLevel);
    
    @NonNull
    SessionState getState();
    
    void setState(@NonNull SessionState state);
    
    @NonNull
    Camera getCamera();

    @Nullable
    Pixel getSelectionStart();

    void setSelectionStart(@Nullable Pixel pixel);

    @Nullable
    Pixel getSelectionEnd();

    void setSelectionEnd(@Nullable Pixel pixel);
    
    @NonNull
    Set<Unit> getSelectedUnits();

    void setSelectedUnits(@NonNull Set<Unit> units);
    
    @Nullable
    Coordinates getMouseCoordinates();

    void setMouseCoordinates(@Nullable Coordinates mouseCoordinates);
    
    @NonNull
    static Session_Warpath create(@NonNull Player player, @NonNull Camera camera)
    {
        return new SessionImpl_Warpath(player, camera);
    }
}
