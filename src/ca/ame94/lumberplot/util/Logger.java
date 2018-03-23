package ca.ame94.lumberplot.util;

import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Logger {

    public static void Info(String msg) {
        Bukkit.getLogger().log(Level.INFO, "[LumberPlot] " + msg);
    }

    public static void Warning(String msg) {
        Bukkit.getLogger().log(Level.WARNING, "[LumberPlot] §e" + msg);
    }

    public static void Severe(String msg) {
        Bukkit.getLogger().log(Level.SEVERE, "[LumberPlot] §c" + msg);
    }

    public static void BroadcastAndLog(String msg) {
        PluginMgr.getPlugin().getServer().broadcastMessage(msg);
        Info(msg);
    }

}
