package de.ukeo.autototem.config;

import de.ukeo.autototem.AutoTotem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class Configuration {

    private FileConfiguration configuration;
    private final File file;

    public Configuration() {
        this.file = new File(AutoTotem.getInstance().getDataFolder().getPath(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                //File already exists
            }
        }

        this.configuration = YamlConfiguration.loadConfiguration(file);
        setupDefaults();
    }

    private void setupDefaults() {
        configuration.set("Settings.Prefix", "§8[§e§lAutoTotem§8] ");
        configuration.set("Settings.Permission.Use", true);
        configuration.set("Settings.Permission.PermissionName", "autototem.use");
        configuration.set("Settings.TimerForVanillaTotems", true);
        configuration.set("Settings.Timer.Use", true);
        configuration.set("Settings.Timer.Time", 20);
        configuration.set("Settings.Timer.Announce", true);
        configuration.set("Settings.Timer.AnnounceMessage", "%prefix% §7You have to wait §c§l%s% §7until you can use the totem again.");
        configuration.set("Settings.Timer.AnnounceWhenFinished", true);
        configuration.set("Settings.Timer.AnnounceWhenFinishedMessage", "%prefix% §7You are able to use your free totem again!");
        configuration.set("Settings.Timer.ResetOnDeath", true);
        configuration.set("Settings.Timer.ResetOnDeathSendMessage", true);
        configuration.set("Settings.Timer.ResetOnDeathMessage", "%prefix% §7Your totem timer got §areset§7!");
        saveConfiguration();

    }

    public String getString(String path) {
        return configuration.getString(path);
    }

    public Integer getInteger(String path) {
        return configuration.getInt(path);
    }

    public Boolean getBoolean(String path) {
        return configuration.getBoolean(path);
    }

    public void saveConfiguration() {
        try {
            configuration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void reloadConfiguration() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
