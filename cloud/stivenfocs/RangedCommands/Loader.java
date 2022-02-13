package cloud.stivenfocs.RangedCommands;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Loader extends JavaPlugin {

    public void onEnable() {
        Vars.plugin = this;
        Vars.getVars().reloadVars();
        getCommand("rangedcommands").setExecutor(new MainCommand(this));
        getCommand("rangedcommands").setTabCompleter(new MainCommand(this));
        Bukkit.getPluginManager().registerEvents(new Events(this), this);
    }

}
