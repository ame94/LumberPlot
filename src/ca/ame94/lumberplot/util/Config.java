package ca.ame94.lumberplot.util;

import ca.ame94.lumberplot.LumberPlot;
import ca.ame94.lumberplot.Plot;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class Config {

    /**
     * Create the plugin folder and blank config file
     */
    public static void Init() {
        JavaPlugin p = PluginMgr.getPlugin();

        if (!p.getDataFolder().exists()) {
            Logger.Info("Creating default configuration...");
            p.getConfig().options().copyDefaults(true);
            p.saveConfig();
        } else {
            loadStoredPlots();
        }
    }

    /**
     * Store a plot selection into the config
     * @param sel The WorldEdit selection
     * @param name The name of the plot entry
     */
    public static void storePlot(Selection sel, String name) {
        FileConfiguration c = PluginMgr.getPlugin().getConfig();

        c.set("plots." + name + ".world", sel.getWorld().getName());
        c.set("plots." + name + ".pos1.x", sel.getMaximumPoint().getBlockX());
        c.set("plots." + name + ".pos1.y", sel.getMaximumPoint().getBlockY());
        c.set("plots." + name + ".pos1.z", sel.getMaximumPoint().getBlockZ());
        c.set("plots." + name + ".pos2.x", sel.getMinimumPoint().getBlockX());
        c.set("plots." + name + ".pos2.y", sel.getMinimumPoint().getBlockY());
        c.set("plots." + name + ".pos2.z", sel.getMinimumPoint().getBlockZ());

        PluginMgr.getPlugin().saveConfig();
    }

    /**
     * Remove a plot from the config by name
     * @param name The plot to remove
     */
    public static void clearPlot(String name) {
        ConfigurationSection c = getPlotsConfig();
        c.set(name, null);
        PluginMgr.getPlugin().saveConfig();
    }

    /**
     * Get the plots config section
     * @return The config object for plots
     */
    private static ConfigurationSection getPlotsConfig() {
        return PluginMgr.getPlugin().getConfig().getConfigurationSection("plots");
    }

    /**
     * List the stored plots in the config at startup
     */
    public static void loadStoredPlots() {
        ConfigurationSection plots = getPlotsConfig();
        if (plots == null) {
            Logger.Info("Empty config.");
            return;
        }

        Set<String> keys = plots.getKeys(false);

        Logger.Info("Found " + keys.size() + " plots.");
        for (String entry : keys) {
            String worldName = plots.getString( entry + ".world");

            Double x1 = plots.getDouble(entry + ".pos1.x");
            Double y1 = plots.getDouble(entry + ".pos1.y");
            Double z1 = plots.getDouble(entry + ".pos1.z");
            Double x2 = plots.getDouble(entry + ".pos2.x");
            Double y2 = plots.getDouble(entry + ".pos2.y");
            Double z2 = plots.getDouble(entry + ".pos2.z");
            Vector pos1 = new Vector(x1, y1, z1);
            Vector pos2 = new Vector(x2, y2, z2);

            CuboidRegion cr = new CuboidRegion(pos1, pos2);
            Logger.Info("  " + entry + " in " + worldName + ": " + LumberPlot.myCuboidToString(cr));

            LumberPlot.put(entry, new Plot(pos1, pos2, worldName));
        }
    }
}
