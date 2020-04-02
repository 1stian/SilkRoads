package com.realnaits.SilkRoads;

import com.realnaits.SilkRoads.commands.Srreload;
import com.realnaits.SilkRoads.tasks.Road;
import com.realnaits.SilkRoads.util.Config;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Silkroads extends JavaPlugin {

    public static Silkroads silkroads;
    public static boolean debug = Config.getBoolean("debug");

    @SuppressWarnings("ConstantConditions")
    public void onEnable(){
        silkroads = this;
        //Metrics
        int pluginId = 6975;
        Metrics metrics = new Metrics(this, pluginId);
        //Config
        saveDefaultConfig();
        reloadConfig();

        //Register command
        getCommand("srreload").setExecutor(new Srreload());

        //Register Events
        getServer().getPluginManager().registerEvents(new Road(), this);

        fillRoad();
    }

    public static void fillRoad(){
        List<String> roadBlocks = Config.getList("settings.RoadBlocks");
        for (String item : roadBlocks) {
            Material material = Material.matchMaterial(item);
            if (material == null){
                Bukkit.getLogger().warning(ChatColor.GREEN +"[SilkRoads]" + ChatColor.RED + " Couldn't match material listed in road blocks! "
                        + ChatColor.GOLD + item);
                return;
            }
            Road.roadMaterials.add(material);
        }

        List<String> belowBlocks = Config.getList("settings.BlockBelowRoad");
        for (String item : belowBlocks) {
            Material material = Material.matchMaterial(item);
            if (material == null){
                Bukkit.getLogger().warning(ChatColor.GREEN +"[SilkRoads]" + ChatColor.RED + " Couldn't match material listed in block below road! "
                        + ChatColor.GOLD + item);
                return;
            }
            Road.belowMaterials.add(material);
        }
    }
}
