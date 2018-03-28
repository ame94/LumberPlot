package ca.ame94.lumberplot.util;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class WE {

    private static WorldEditPlugin wep = null;

    /**
     * Get the WorldEditPlugin object
     * @return duh.
     */
    public static WorldEditPlugin get() {
        return wep;
    }

    /**
     * Obtain the WorldEdit plugin reference
     */
    public static void Init() {
        wep = (WorldEditPlugin) PluginMgr.getPlugin().getServer().getPluginManager().getPlugin("WorldEdit");
        if (wep == null) {
            Logger.Severe("WorldEdit NOT found!");
        }
    }
}
