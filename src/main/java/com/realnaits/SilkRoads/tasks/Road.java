package com.realnaits.SilkRoads.tasks;

import com.realnaits.SilkRoads.util.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Road implements Listener {

    public static List<Material> roadMaterials = new ArrayList<>();
    public static List<Material> belowMaterials = new ArrayList<>();

    private List<UUID> playersOnRoad = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        //Grabbing values
        double speed = (float) Config.getDouble("settings.Walk.Speed");
        boolean speedEnabled = Config.getBoolean("settings.Walk.Enabled");

        //Getting blocks below player
        Location road = player.getLocation().subtract(0,1,0);
        Location below = player.getLocation().subtract(0,2,0);

        //Getting blocks
        Block roadBlock = road.getBlock();
        Block belowBlock = below.getBlock();

        //Checking if blocks are valid
        if (roadMaterials.contains(roadBlock.getType()) && belowMaterials.contains(belowBlock.getType())){
            if (speedEnabled && !player.isSprinting()){
                player.setWalkSpeed((float)speed);
                if (!playersOnRoad.contains(player.getUniqueId())){
                    playersOnRoad.add(player.getUniqueId());
                }
            }

        }else{
            player.setWalkSpeed(0.2f);
            playersOnRoad.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event){
        if (playersOnRoad.contains(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event){
        boolean starv = Config.getBoolean("settings.Starv");
        if (event.getEntity().getType() == (EntityType.PLAYER) && starv){
            Player player = (Player) event.getEntity();
            if (playersOnRoad.contains(player.getUniqueId()) && !player.isSprinting()){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlock(BlockFormEvent event){
        Block block = event.getBlock();

        //Getting blocks below player
        Location road = block.getLocation().subtract(0,1,0);
        Location below = block.getLocation().subtract(0,2,0);

        //Getting blocks
        Block roadBlock = road.getBlock();
        Block belowBlock = below.getBlock();

        //Checking if blocks are valid
        if (Config.getBoolean("settings.Snow")){
            if (roadMaterials.contains(roadBlock.getType()) && belowMaterials.contains(belowBlock.getType())) {
                event.setCancelled(true);
            }
        }
    }
}
