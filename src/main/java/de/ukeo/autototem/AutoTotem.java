package de.ukeo.autototem;

import de.ukeo.autototem.commands.ReloadCommand;
import de.ukeo.autototem.config.Configuration;
import de.ukeo.autototem.listener.*;
import de.ukeo.autototem.util.Metrics;
import de.ukeo.autototem.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoTotem extends JavaPlugin {

    private static AutoTotem instance;

    private Configuration configuration;
    private Metrics metrics;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        loadCore();
        loadConfigurationValues();
        registerListener();
        registerCommands();
    }

    @Override
    public void onDisable() {
        shutdownCore();
    }

    private void loadCore() {
        configuration = new Configuration();
        metrics = new Metrics(this, Utils.PLUGIN_ID);
    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EntityDamageListener(), instance);
        pluginManager.registerEvents(new EntityResurrectListener(), instance);
        pluginManager.registerEvents(new PlayerDeathListener(), instance);
        pluginManager.registerEvents(new PlayerJoinListener(), instance);
        pluginManager.registerEvents(new PlayerQuitListener(), instance);
    }

    private void registerCommands() {
        getCommand("batreload").setExecutor(new ReloadCommand());
    }

    private void shutdownCore() {
        Utils.PLAYER_TOTEM_CACHE.clear();
    }

    public void loadConfigurationValues() {
        Utils.PREFIX = configuration.getString("Settings.Prefix");
        Utils.USE_PERMISSION = configuration.getBoolean("Settings.Permission.Use");
        Utils.PERMISSION = configuration.getString("Settings.Permission.PermissionName");
        Utils.TIMER_FOR_VANILLA = configuration.getBoolean("Settings.TimerForVanillaTotems");
        Utils.USE_TIMER = configuration.getBoolean("Settings.Timer.Use");
        Utils.TIMER = configuration.getInteger("Settings.Timer.Time");
        Utils.USE_TIMER_ANNOUNCE = configuration.getBoolean("Settings.Timer.Announce");
        Utils.ANNOUNCE_MESSAGE = configuration.getString("Settings.Timer.AnnounceMessage").replaceAll("%prefix%", Utils.PREFIX);
        Utils.ANNOUNCE_WHEN_FINISHED = configuration.getBoolean("Settings.Timer.AnnounceWhenFinished");
        Utils.ANNOUNCE_FINISHED_MESSAGE = configuration.getString("Settings.Timer.AnnounceWhenFinishedMessage").replaceAll("%prefix%", Utils.PREFIX);
        Utils.USE_RESET = configuration.getBoolean("Settings.Timer.ResetOnDeath");
        Utils.SEND_MESSAGE = configuration.getBoolean("Settings.Timer.ResetOnDeathSendMessage");
        Utils.RESET_MESSAGE = configuration.getString("Settings.Timer.ResetOnDeathMessage").replaceAll("%prefix%", Utils.PREFIX);
    }

    public void reloadConfiguration() {
        configuration.reloadConfiguration();
        loadConfigurationValues();
    }

    public static AutoTotem getInstance() {
        return instance;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}
