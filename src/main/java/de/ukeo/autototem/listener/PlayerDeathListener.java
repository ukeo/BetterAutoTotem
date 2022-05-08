package de.ukeo.autototem.listener;

import de.ukeo.autototem.api.TotemPlayer;
import de.ukeo.autototem.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        TotemPlayer totemPlayer = Utils.PLAYER_TOTEM_CACHE.get(player);
        if (totemPlayer == null) return;
        if (!totemPlayer.isAbleToDie()) {
            if (Utils.USE_RESET) {
                player.sendMessage(Utils.RESET_MESSAGE);
            }
        }
    }

}
