package com.fabichan.WitherSoundControl;

import com.fabichan.WitherSoundControl.Commands.WitherSoundControlCommandHandler;
import com.fabichan.WitherSoundControl.Events.EntityExplosionEvent;
import com.fabichan.WitherSoundControl.Events.WorldEventListener;
import com.fabichan.WitherSoundControl.Metrics.Metrics;
import com.fabichan.WitherSoundControl.Update.Checker;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public class Main extends JavaPlugin{
    private static final String PLUGIN_VERSION = "1.2.0";
    public FileConfiguration languageFile;
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

    public boolean isConfigOutdated(FileConfiguration config) {
        String configVersion = config.getString("Version");
        return (configVersion == null || compareVersions(configVersion, PLUGIN_VERSION) < 0);
    }

    private int compareVersions(String version1, String version2) {
        String[] v1Components = version1.split("\\.");
        String[] v2Components = version2.split("\\.");

        for (int i = 0; i < Math.max(v1Components.length, v2Components.length); i++) {
            int v1 = (i < v1Components.length) ? Integer.parseInt(v1Components[i]) : 0;
            int v2 = (i < v2Components.length) ? Integer.parseInt(v2Components[i]) : 0;

            if (v1 < v2) {
                return -1;
            } else if (v1 > v2) {
                return 1;
            }
        }

        return 0;
    }

    private void UpdateConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        // Check if the configuration is outdated
        if (isConfigOutdated(config)) {
            getLogger().warning("Outdated configuration detected. Updating the configuration!");

            // Delete the old configuration file
            if (configFile.exists()) {
                configFile.delete();
            }

            // Create a new configuration file
            saveDefaultConfig();

            // Set the new configuration values
            int range = 32;
            boolean enable = true;
            String wsound = "ENTITY_WITHER_SPAWN";

            try {
                range = config.getInt("SoundRange");
            } catch (Exception ignored) {
            }
            try {
                enable = config.getBoolean("Enabled");
            } catch (Exception ignored) {
            }
            try {
                wsound = config.getString("SpawnSound");
            } catch (Exception ignored) {
            }

            config.set("Version", PLUGIN_VERSION);
            config.set("SoundRange", range);
            config.set("Enabled", enable);
            config.set("SpawnSound", wsound);

            saveConfig();

            getLogger().info("Configuration update finished. Continuing the boot process.");
        }
    }

    private void UpdateChecker() {
        new Checker(this, 111105).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("This is the latest Version");
            } else {
                getLogger().info("There is a new update available. New Version: "+ version + "  Grab it here: https://www.spigotmc.org/resources/withersoundcontrol.111105/");
            }
        });
    }

    private void deployLanguagefiles() {
        File configFile = new File(getDataFolder(), "messages.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()){
            saveResource("messages.yml", false);
        }
        if (isConfigOutdated(config)){
            if (configFile.exists()) {
                configFile.delete();
                saveResource("messages.yml", false);

            }
        }
    }

    private void loadLanguagefile() {
        File languageFile = new File(getDataFolder(), "messages.yml");
        this.languageFile = YamlConfiguration.loadConfiguration(languageFile);
    }


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        UpdateConfig();
        deployLanguagefiles();
        getLogger().info("---------------------------------------");
        getLogger().info("Starting WitherSoundControl");
        getLogger().info("Author: Fabi-Chan");
        getLogger().info("Version: "+ PLUGIN_VERSION);
        getLogger().info("---------------------------------------");
        EventRegistration();
        CommandRegistration();
        bStatsInit();
        UpdateChecker();
        loadLanguagefile();
        getLogger().info("Startup completed");
    }

    public String getMessage(String key) {
        String language = getConfig().getString("language");
        return languageFile.getString("messages." + language + "." + key);
    }


    @Override
    public void onDisable() {
        getLogger().info("Stopping WitherSoundControl");
        HandlerList.unregisterAll(this);
        getLogger().info("Bye!");

    }

}