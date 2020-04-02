package com.realnaits.SilkRoads.commands;

import com.realnaits.SilkRoads.Silkroads;
import com.realnaits.SilkRoads.util.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Srreload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("srreload")){
            Config.reloadConfig();
            Silkroads.fillRoad();
            commandSender.sendMessage("Config has been reloaded.");
            return true;
        }
        return false;
    }
}
