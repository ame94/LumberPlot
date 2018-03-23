package ca.ame94.lumberplot;

import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Set;

public class LumberPlot {

    private static HashMap<String, Selection> plots = new HashMap<>();

    public static Selection get(String name) {
        if (plots.containsKey(name)) {
            return plots.get(name);
        } else {
            return null;
        }
    }

    public static void put(String name, Selection sel) {
        plots.put(name, sel);
    }

    public static void listPlots(Player player) {

        player.sendMessage("Defined plots:");
        Set<String> keys = plots.keySet();
        for (String entry : keys) {
            player.sendMessage("  " + entry);
        }
    }

    public static boolean isLocationUnderProtection(Location loc) {
        // For each plot
        for (String entry : plots.keySet()) {
            // If plot's world the same as loc's world
            if (get(entry).getWorld().equals(loc.getWorld())) {
                // If location is inside WE selection
                if (get(entry).contains(loc)) {
                    return true;
                }
            }
        }
        return false;
    }
}
