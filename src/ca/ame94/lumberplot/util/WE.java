package ca.ame94.lumberplot.util;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class WE {

    private static WorldEditPlugin wep = null;

    public static WorldEditPlugin get() {
        return wep;
    }

    public static void Init() {
        wep = (WorldEditPlugin) PluginMgr.getPlugin().getServer().getPluginManager().getPlugin("WorldEdit");
        if (wep == null) {
            Logger.Severe("WorldEdit NOT found!");
        }
    }
}
