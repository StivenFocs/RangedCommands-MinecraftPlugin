package cloud.stivenfocs.RangedCommands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {

    Loader plugin;

    public MainCommand(Loader plugin) {
        this.plugin = plugin;
    }

    /////////////////////////////////

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (Vars.hasAdminPermissions(sender)) {
            if (args.length <= 0) {
                Vars.colorlist(Vars.help, sender);
            } else {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (Vars.getVars().reloadVars()) {
                        Vars.sendString(Vars.configuration_reloaded, sender);
                    } else {
                        Vars.sendString(Vars.an_error_occurred, sender);
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;

                        if (args.length > 1) {
                            try {
                                String label = "";
                                for(String arg : args) {
                                    if (label.length() > 0) {
                                        label = label + " ";
                                    }
                                    label = label + arg;
                                }
                                String[] command = label.split(" ", 2);

                                Vars.getVars().dataConfig.set(command[1] + ".deny_message", Vars.default_deny_message);
                                Vars.getVars().dataConfig.set(command[1] + ".range", 5);
                                Location loc = p.getLocation();
                                Vars.getVars().dataConfig.set(command[1] + ".anchor", loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ());
                                Vars.getVars().dataConfig.set(command[1] + ".justThisCommand", false);
                                Vars.getVars().saveDataConfiguration();
                                Vars.getVars().reloadDataConfiguration();

                                Vars.sendString(Vars.command_added, sender);
                            } catch (Exception ex) {
                                Vars.sendString(Vars.an_error_occurred, sender);
                                plugin.getLogger().severe("An exception occurred while trying to add a command");
                                ex.printStackTrace();
                            }
                        } else {
                            Vars.sendString(Vars.incomplete_command.replaceAll("%argument%", "command"), sender);
                        }
                    } else {
                        Vars.sendString(Vars.only_players, sender);
                    }
                    return true;
                }

                Vars.sendString(Vars.unknown_subcommand, sender);
            }
        } else {
            Vars.sendString(Vars.no_permissions, sender);
        }
        return false;
    }

    /////////////////////////////////

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> su = new ArrayList<>();
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("") || args[0].startsWith(" ")) {
                if (Vars.hasAdminPermissions(sender)) {
                    su.add("reload");
                    su.add("add");
                }
            } else {
                if (Vars.hasAdminPermissions(sender)) {
                    if ("reload".startsWith(args[0])) {
                        su.add("reload");
                    }
                    if ("add".startsWith(args[0])) {
                        su.add("add");
                    }
                }
            }
        }

        return su;
    }
}
