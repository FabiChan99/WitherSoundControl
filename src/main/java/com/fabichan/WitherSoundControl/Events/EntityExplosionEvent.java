package com.fabichan.WitherSoundControl.Events;


import com.fabichan.WitherSoundControl.Main;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;


public class EntityExplosionEvent implements Listener{

    private final Main plugin;

    public EntityExplosionEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWitherExplode(EntityExplodeEvent event) {
        if (event.getEntity().getType() == EntityType.WITHER && plugin.getConfig().getBoolean("Enabled")) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (event.getLocation().getWorld() == player.getWorld()) {
                    if (event.getEntity().getLocation().distance(player.getLocation()) <= plugin.getConfig().getInt("SoundRange")) {
                        player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1F, 1F);
                    }
                }
            }
        }
    }

}