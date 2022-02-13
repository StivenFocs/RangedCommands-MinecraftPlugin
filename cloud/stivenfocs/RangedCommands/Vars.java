package cloud.stivenfocs.RangedCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Vars {

    public static Loader plugin;
    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    ////////////////////////////

    public File dataFile = new File(plugin.getDataFolder() + "/data.yml");
    public FileConfiguration dataConfig;

    ////////////////////////////

    static Vars vars;
    public static Vars getVars() {
        if (vars == null) {
            vars = new Vars();
        }

        return vars;
    }

    ////////////////////////////

    public static String command_added = "";
    public static String configuration_reloaded = "";
    public static String an_error_occurred = "";
    public static String no_permissions = "";
    public static String unknown_subcommand = "";
    public static String incomplete_command = "";
    public static String default_deny_message = "";
    public static String only_players = "";
    public static List<String> help = new ArrayList<>();

    ////////////////////////////

    public boolean reloadVars() {
        try {
            plugin.reloadConfig();

            getConfig().options().header("Developed with LOV by StivenFocs");
            getConfig().options().copyDefaults(true);

            getConfig().addDefault("messages.command_added", "&aCommand added");
            getConfig().addDefault("messages.configuration_reloaded", "&aConfiguration successfully reloaded");
            getConfig().addDefault("messages.an_error_occurred", "&cAn error occurred while doing this task...");
            getConfig().addDefault("messages.no_permissions", "&cYou're not permitted to do this.");
            getConfig().addDefault("messages.unknown_subcommand", "&cUnknown subcommand.");
            getConfig().addDefault("messages.incomplete_command", "&cIncomplete command, %argument% is required.");
            getConfig().addDefault("messages.default_deny_message", "&cYou can't use that command now");
            getConfig().addDefault("messages.only_players", "&cOnly players can execute this command");
            List<String> new_help = new ArrayList<>();
            new_help.add("&8&m*==========================*");
            new_help.add("&7* &e&lRangedCommads");
            new_help.add("");
            new_help.add("&7* /rcmds reload &8&m|&7 Reload the whole configuration");
            new_help.add("&7* /rcmds add <command> &8&m|&7 add a ranged command");
            new_help.add("");
            new_help.add("&8&m*==========================*");
            getConfig().addDefault("messages.help", new_help);

            plugin.saveConfig();
            plugin.reloadConfig();
            reloadDataConfiguration();

            command_added = getConfig().getString("messages.command_added", "&aCommand added");
            configuration_reloaded = getConfig().getString("messages.configuration_reloaded", "&aConfiguration successfully reloaded");
            an_error_occurred = getConfig().getString("messages.an_error_occurred", "&cAn error occurred while doing this task...");
            no_permissions = getConfig().getString("messages.no_permissions", "&cYou're not permitted to do this.");
            unknown_subcommand = getConfig().getString("messages.unknown_subcommand", "&cUnknown subcommand.");
            incomplete_command = getConfig().getString("messages.incomplete_command", "&cIncomplete command, %argument% is required.");
            default_deny_message = getConfig().getString("messages.default_deny_message", "&cYou can't use that command now");
            only_players = getConfig().getString("messages.only_players", "&cOnly players can execute this command");
            help = getConfig().getStringList("messages.help");

            plugin.getLogger().info("Reloaded the whole configuration");
            return true;
        } catch (Exception ex) {
            plugin.getLogger().severe("An error occurred while trying to reload the whole configuration of the plugin...Disabling the plugin");
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
            return false;
        }
    }

    public void reloadDataConfiguration() {
        try {
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
        } catch (Exception ex) {
            plugin.getLogger().severe("An error occurred while trying to get and/or create the data configuration file");
            ex.printStackTrace();
            return;
        }

        try {
            dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        } catch (Exception ex) {
            plugin.getLogger().severe("An error occurred while trying to reload the data configuration file");
            ex.printStackTrace();
        }
    }

    public void saveDataConfiguration() {
        try {
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
        } catch (Exception ex) {
            plugin.getLogger().severe("An error occurred while trying to get and/or create the data configuration file");
            ex.printStackTrace();
            return;
        }

        try {
            dataConfig.save(dataFile);
        } catch (Exception ex) {
            plugin.getLogger().severe("An error occurred while trying to save into the data configuration file");
            ex.printStackTrace();
        }
    }

    ////////////////////////////

    public static void sendString(String text, CommandSender sender) {
        if (plugin.getConfig().getString(text) != null) {
            text = plugin.getConfig().getString(text);
        }

        if (text.length() > 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', text.replaceAll("%version%", plugin.getDescription().getVersion()).replaceAll("%player%", sender.getName())));
        }
    }

    public static void colorlist(List<String> uncolored_list, CommandSender sender) {
        for(String line : uncolored_list) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line.replaceAll("%version%", plugin.getDescription().getVersion()).replaceAll("%player%", sender.getName())));
        }
    }

    public static boolean hasAdminPermissions(CommandSender sender) {
        return sender.hasPermission("rangedcommands.admin") || sender.hasPermission("rangedcommands.*");
    }

    public static boolean hasExceptPermissions(CommandSender sender) {
        return sender.hasPermission("rangedcommands.except") || sender.hasPermission("rangedcommands.*");
    }

}