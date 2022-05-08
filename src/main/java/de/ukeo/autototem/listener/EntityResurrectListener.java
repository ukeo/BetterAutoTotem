package de.ukeo.autototem.listener;

import de.ukeo.autototem.api.TotemPlayer;
import de.ukeo.autototem.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

public class EntityResurrectListener implements Listener {

    @EventHandler
    public void onPlayerDeath(EntityResurrectEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            TotemPlayer totemPlayer = Utils.PLAYER_TOTEM_CACHE.get(player);
            if (totemPlayer == null) return;
            totemPlayer.checkPlayer(event);
        }
    }

}
