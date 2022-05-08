package de.ukeo.autototem.listener;

import de.ukeo.autototem.api.TotemPlayer;
import de.ukeo.autototem.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            TotemPlayer totemPlayer = Utils.PLAYER_TOTEM_CACHE.get(player);
            if (totemPlayer == null) return;
            if (Utils.USE_TIMER_ANNOUNCE) {
                if (!totemPlayer.isAbleToDie()) {
                    if (player.getHealth() <= 10) {
                        player.sendMessage(Utils.ANNOUNCE_MESSAGE.replaceAll("%s%", String.valueOf(totemPlayer.getTimer())));
                    }
                }
            }
        }
    }

}
