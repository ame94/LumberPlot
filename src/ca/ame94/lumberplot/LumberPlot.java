package ca.ame94.lumberplot;

import ca.ame94.lumberplot.util.Logger;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Set;

/**
 * The heart of the plugin. This keeps track of all the plots for various worlds
 */
public class LumberPlot {

    private static HashMap<String, Plot> plots = new HashMap<>();

    /**
     * Trash the current plots hashmap
     */
    public static void clear() {
        plots = new HashMap<>();
    }

    /**
     * Get a plot for name
     * @param name The name of the plot
     * @retur The plot object
     */
    public static Plot get(String name) {
        if (plots.containsKey(name)) {
            return plots.get(name);
        } else {
            return null;
        }
    }

    /**
     * Compose a plot object from a WorldEdit selection and place it into the hashmap
     * @param name The name of the new plot
     * @param sel The WorldEdit selection
     */
    public static void put(String name, Selection sel) {
        Vector pos1 = new Vector(sel.getMinimumPoint().getBlockX(), sel.getMinimumPoint().getBlockY(), sel.getMinimumPoint().getBlockZ());
        Vector pos2 = new Vector(sel.getMaximumPoint().getBlockX(), sel.getMaximumPoint().getBlockY(), sel.getMaximumPoint().getBlockZ());
        String world = sel.getWorld().getName();
        plots.put(name, new Plot(pos1, pos2, world));
    }

    /**
     * Take a composed Plot object and put it in the hash
     * @param name The plot name
     * @param plot The plot object
     */
    public static void put(String name, Plot plot) {
        plots.put(name, plot);
    }

    /**
     * Remove a plot by name
     * @param name The name of the plot to remove
     * @return true on success
     */
    public static boolean clear(String name) {
        boolean found = false;
        Set<String> keys = plots.keySet();
        for (String entry : keys) {
            if (name.equalsIgnoreCase(entry)) {
                plots.put(entry, null); // mark for deletion; deleting hash object here causes ConcurrentModificationException
                found = true;
            }
        }
        return found;
    }

    public static String myCuboidToString(CuboidRegion cr) {
        Vector p1 = cr.getMinimumPoint();
        Vector p2 = cr.getMaximumPoint();

        String p1Str = "(" + p1.getBlockX() + ", " + p1.getBlockY() + ", " + p1.getBlockZ() + ")";
        String p2Str = "(" + p2.getBlockX() + ", " + p2.getBlockY() + ", " + p2.getBlockZ() + ")";
        return p1Str + " - " + p2Str;
    }

    /**
     * Get a list of all the currently defined plots
     * @param player Which player to send the information to
     */
    public static void listPlots(Player player) {

        player.sendMessage("Defined plots:");
        Set<String> keys = plots.keySet();
        for (String entry : keys) {
            if (plots.get(entry) != null) {
                Plot plot = plots.get(entry);
                player.sendMessage("  " + entry + " §a" + myCuboidToString(plot.getCuboid())+ " §fin §a" + plot.getWorld());
            }
        }
    }

    /**
     * Determine if a location is within the protection of a plot for the world it is in.
     * @param loc The block location
     * @return true if block is protected
     */
    public static boolean isLocationUnderProtection(Location loc) {
        // For each plot
        for (String entry : plots.keySet()) {
            Plot plot = plots.get(entry);
            if (plot != null) {
                // If plot's world the same as loc's world
                String plotWorld = plot.getWorld();
                String playerWorld = loc.getWorld().getName();
                if (playerWorld.equalsIgnoreCase(plotWorld)) {
                    // If location is inside WE selection
                    Vector vec = new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                    if (plot.contains(vec)) {
                        return true;
                    }
                }
            } else {
                plots.remove(entry); // marked for deletion, now remove from hash
            }
        }
        return false;
    }
}
