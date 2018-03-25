package ca.ame94.lumberplot;

import ca.ame94.lumberplot.listeners.LeafDecay;
import ca.ame94.lumberplot.listeners.PlayerBreakBlock;
import ca.ame94.lumberplot.listeners.PlayerPlaceBlock;
import ca.ame94.lumberplot.util.Config;
import ca.ame94.lumberplot.util.Logger;
import ca.ame94.lumberplot.util.PluginMgr;
import ca.ame94.lumberplot.util.WE;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {

    @Override
    public void onEnable() {
        Logger.Info("Starting up");
        PluginMgr.Init(this);
        Config.Init();
        WE.Init();

        PluginMgr.RegisterEvent(new PlayerBreakBlock());
        PluginMgr.RegisterEvent(new PlayerPlaceBlock());
        PluginMgr.RegisterEvent(new LeafDecay());
    }

    @Override
    public void onDisable() {
        Logger.Info("Shutting down");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args[0].equalsIgnoreCase("reload")) {
            Config.loadStoredPlots();
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            LumberPlot.listPlots((Player)sender);
        }

        if (sender instanceof Player) {
            if (args[0].equalsIgnoreCase("define")) {

                if (args.length != 2) {
                    sender.sendMessage("Usage: /lumberplot define <plotname>");
                    return true;
                }

                Selection sel = WE.get().getSelection(((Player) sender).getPlayer());
                if (sel != null) {
                    int length = sel.getLength();
                    int width = sel.getWidth();
                    int height = sel.getHeight();
                    sender.sendMessage("Using a selection of " + length + "x" + width + "x" + height + " (LWH)");
                    Config.storePlot(sel, args[1]);
                    LumberPlot.put(args[1], sel);
                } else {
                    sender.sendMessage("No WorldEdit selection made!");
                }
            }
        } else {
            sender.sendMessage("You can't use this from the console.");
            return true;
        }

        return false;
    }
}
