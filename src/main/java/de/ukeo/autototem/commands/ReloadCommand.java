package de.ukeo.autototem.commands;

import de.ukeo.autototem.AutoTotem;
import de.ukeo.autototem.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("autototem.reload")) {
                AutoTotem.getInstance().reloadConfiguration();
                player.sendMessage(Utils.PREFIX + "§aSuccessfully §7reloaded config!");
            } else {
                player.sendMessage(Utils.PREFIX + "§cYou're not allowed to run this command :(");
            }
        } else {
            AutoTotem.getInstance().reloadConfiguration();
            Bukkit.getConsoleSender().sendMessage(Utils.PREFIX + "§aSuccessfully §7reloaded config!");
        }
        return true;
    }
}
