package com.fabichan.WitherSoundControl;

import com.fabichan.WitherSoundControl.Commands.WitherSoundControlCommandHandler;
import com.fabichan.WitherSoundControl.Events.EntityExplosionEvent;
import com.fabichan.WitherSoundControl.Events.WorldEventListener;
import com.fabichan.WitherSoundControl.Metrics.Metrics;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;


public class Main extends JavaPlugin{

    private void EventRegistration() {
        getLogger().info("Registering Listener");
        new WorldEventListener(this);
        getServer().getPluginManager().registerEvents(new EntityExplosionEvent(this), this);
        getLogger().info("Listener registration completed");
    }


    private void CommandRegistration() {
        getLogger().info("Registering Commands");
        PluginCommand WitherSoundPluginCommand = getCommand("withersoundcontrol");
        if (WitherSoundPluginCommand != null) {
            WitherSoundControlCommandHandler witherSoundControlCommandHandler = new WitherSoundControlCommandHandler(this);
            WitherSoundPluginCommand.setExecutor(witherSoundControlCommandHandler);
            WitherSoundPluginCommand.setTabCompleter(witherSoundControlCommandHandler);
        }
        getLogger().info("Command registration completed");
    }

    private void bStatsInit() {
        getLogger().info("Initializing Plugin bStats Metrics");
        Metrics m = new Metrics(this, 19036);
        getLogger().info("Metrics init completed");
    }

    @Override
    public void onLoad() {
        getLogger().info("Hello Minecraft!");
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getLogger().info("---------------------------------------");
        getLogger().info("Starting WitherSoundControl");
        getLogger().info("Author: Fabi-Chan");
        getLogger().info("Version: 1.0");
        getLogger().info("---------------------------------------");
        EventRegistration();
        CommandRegistration();
        bStatsInit();
        getLogger().info("Startup completed");
    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping WitherSoundControl");
        HandlerList.unregisterAll(this);
        getLogger().info("Bye!");

    }

}