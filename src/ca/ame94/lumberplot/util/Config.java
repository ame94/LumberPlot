package ca.ame94.lumberplot.util;

import ca.ame94.lumberplot.LumberPlot;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class Config {

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

    private static ConfigurationSection getPlotsConfig() {
        return PluginMgr.getPlugin().getConfig().getConfigurationSection("plots");
    }

    public static void loadStoredPlots() {
        ConfigurationSection plots = getPlotsConfig();
        if (plots == null) {
            Logger.Info("No plots defined.");
            return;
        }

        Set<String> keys = plots.getKeys(false);
        Logger.Info("Found " + keys.size() + " plots.");
        for (String entry : keys) {
            String worldName = plots.getString( entry + ".world");
            World world = PluginMgr.getPlugin().getServer().getWorld(worldName);


            Double x1 = plots.getDouble(entry + ".pos1.x");
            Double y1 = plots.getDouble(entry + ".pos1.y");
            Double z1 = plots.getDouble(entry + ".pos1.z");
            Double x2 = plots.getDouble(entry + ".pos2.x");
            Double y2 = plots.getDouble(entry + ".pos2.y");
            Double z2 = plots.getDouble(entry + ".pos2.z");

            Logger.Info("Plot " + entry + " in world " + worldName + ":");
            Logger.Info("  POS1" + new Location(world, x1, y1, z1).toString());
            Logger.Info("  POS2" + new Location(world, x2, y2, z2).toString());
            Logger.Info("");

            LumberPlot.put(entry, new CuboidSelection(world, new Location(world, x1, y1, z1), new Location(world, x2, y2, z2)));
        }
    }
}
