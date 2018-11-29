package ca.ame94.lumberplot;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;

/**
 * Plots contain a CuboidRegion and a worldname (along with the raw pos1 and pos2 points) that define
 * a region they protect
 */
public class Plot {

    private BlockVector3 pos1, pos2;
    private String world;
    private CuboidRegion cuboid;

    /**
     * The constructor to define a new plot with two 3d vectors and a world name string
     * @param pos1 Pos1
     * @param pos2 Pos2
     * @param world The world name this cuboid belongs to
     */
    public Plot(BlockVector3 pos1, BlockVector3 pos2, String world) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = world;
        cuboid = new CuboidRegion(pos1, pos2);


    }

    /**
     * A front-end to WorldEdit's contains() method for CuboidRegion
     * @param pos The position in question
     * @return true if point is within cuboid
     */
    public boolean contains(BlockVector3 pos) {
        if (cuboid.contains(pos)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the raw cuboid
     * @return The CuboidRegion object
     */
    public CuboidRegion getCuboid() {
        return cuboid;
    }

    public BlockVector3 getPos1() {
        return pos1;
    }

    public BlockVector3 getPos2() {
        return pos2;
    }

    public String getWorld() {
        return world;
    }
}
