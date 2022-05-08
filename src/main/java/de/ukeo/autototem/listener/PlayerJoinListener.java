package de.ukeo.autototem.listener;

import de.ukeo.autototem.api.TotemPlayer;
import de.ukeo.autototem.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!Utils.USE_PERMISSION || player.hasPermission(Utils.PERMISSION)) {
            if (!Utils.PLAYER_TOTEM_CACHE.containsKey(player)) {
                TotemPlayer totemPlayer = new TotemPlayer(player);
                Utils.PLAYER_TOTEM_CACHE.put(player, totemPlayer);
            }
        }
    }

}
