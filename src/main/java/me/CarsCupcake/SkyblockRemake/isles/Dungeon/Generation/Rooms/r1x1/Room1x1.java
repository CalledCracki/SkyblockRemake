package me.CarsCupcake.SkyblockRemake.isles.Dungeon.Generation.Rooms.r1x1;

import me.CarsCupcake.SkyblockRemake.isles.Dungeon.Generation.Direction;
import me.CarsCupcake.SkyblockRemake.isles.Dungeon.Generation.IRoom;
import me.CarsCupcake.SkyblockRemake.isles.Dungeon.Generation.Location2d;

import java.util.Set;

public abstract class Room1x1 extends IRoom {
    @Override
    public Set<Location2d> getNextLocations(Location2d base, int rotation) {
        return Set.of(Direction.Up.move(base.clone()), Direction.Down.move(base.clone()), Direction.Left.move(base.clone()), Direction.Right.move(base.clone()));
    }
}