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
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {

            sender.sendMessage("LumberPlot help:");
            sender.sendMessage("/lumberplot list §aDisplays defined plots");
            sender.sendMessage("/lumberplot define <plotname> §aDefine a new plot");
            sender.sendMessage("/lumberplot delete <plotname> §aDeletes a plot");
            sender.sendMessage("/lumberplot reload §aReloads the config");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            LumberPlot.clear();
            Config.loadStoredPlots();
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            LumberPlot.listPlots((Player)sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("delete")) {
            if (args.length != 2) {
                sender.sendMessage("Usage: /lumberplot delete <plotname>");
                return true;
            }
            String plotName = args[1];
            Boolean cleared = LumberPlot.clear(plotName);
            if (cleared) {
                Config.clearPlot(plotName);
                sender.sendMessage("Plot removed.");
            } else {
                sender.sendMessage("Plot not found!");
            }
            return true;
        }

        if (sender instanceof Player) {
            if (args[0].equalsIgnoreCase("define")) {
                Player player = (Player)sender;

                if (args.length != 2) {
                    sender.sendMessage("Usage: /lumberplot define <plotname>");
                    return true;
                }

                String plotName = args[1];
                Selection sel = WE.get().getSelection(player);

                if (sel != null) {
                    String worldName = player.getWorld().getName();
                    int length = sel.getLength();
                    int width = sel.getWidth();
                    int height = sel.getHeight();
                    sender.sendMessage("Using a selection of " + length + "x" + width + "x" + height + " (LWH) for " + plotName + " in " + worldName);

                    Config.storePlot(sel, plotName);
                    LumberPlot.put(plotName, sel);
                    return true;
                } else {
                    sender.sendMessage("No WorldEdit selection made!");
                    return true;
                }
            }
        } else {
            sender.sendMessage("You can't use this from the console.");
            return true;
        }

        sender.sendMessage("Check §a/lumberplot help §ffor usage information.");

        return true;
    }
}
