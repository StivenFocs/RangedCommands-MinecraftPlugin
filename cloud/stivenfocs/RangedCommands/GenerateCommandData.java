package cloud.stivenfocs.RangedCommands;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class GenerateCommandData {

    String command;

    public GenerateCommandData(String command) {
        this.command = command;
    }

    public String getDenyMessage() {
        return Vars.getVars().dataConfig.getString(command + ".deny_message");
    }

    public Location getLocation() {
        String loc = Vars.getVars().dataConfig.getString(command + ".anchor").replaceAll(" ", "");
        String[] loc_ = loc.split(",");
        return new Location(Bukkit.getWorld(loc_[0]), Double.parseDouble(loc_[1]), Double.parseDouble(loc_[2]), Double.parseDouble(loc_[3]));
    }

    public Double getMinRange() {
        return Vars.getVars().dataConfig.getDouble(command + ".range");
    }

    public boolean justThisCommand() {
        return Vars.getVars().dataConfig.getBoolean(command + ".justThisCommand");
    }

}
