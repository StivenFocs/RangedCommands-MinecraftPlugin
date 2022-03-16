package cloud.stivenfocs.RangedCommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Events implements Listener {

    Loader plugin;

    public Events(Loader plugin) {
        this.plugin = plugin;
    }

    /////////////////////////////////

    @EventHandler(priority = EventPriority.HIGH)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        String command_name = event.getMessage().replaceAll("/", "");

        if (!Vars.hasExceptPermissions(p)) {
            for (String configuration_command : Vars.getVars().dataConfig.getKeys(false)) {
                GenerateCommandData command = new GenerateCommandData(configuration_command);

                boolean Continue = false;
                if (command.justThisCommand()) {
                    if (command_name.equalsIgnoreCase(configuration_command.toLowerCase())) Continue = true;
                } else {
                    if (command_name.toLowerCase().startsWith(configuration_command.toLowerCase())) Continue = true;
                }

                if (Continue) {
                    for (Entity entity : Bukkit.getWorld(p.getLocation().getWorld().getName()).getNearbyEntities(command.getLocation(), command.getMinRange(), command.getMinRange(), command.getMinRange())) {
                        if (entity instanceof Player) {
                            Player p_ = (Player) entity;

                            if (p_.getUniqueId().equals(p.getUniqueId())) {
                                return;
                            }
                        }
                    }

                    event.setCancelled(true);
                    Vars.sendString(command.getDenyMessage(), p);
                }
            }
        }
    }

}
