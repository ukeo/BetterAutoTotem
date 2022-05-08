package de.ukeo.autototem.util;

import de.ukeo.autototem.AutoTotem;
import de.ukeo.autototem.api.TotemPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;

public class Utils {

    public static final int PLUGIN_ID = 15151;

    public static String PREFIX = "[BAT] ";
    public static String PERMISSION = "autototem.use";
    public static String ANNOUNCE_MESSAGE = "%prefix% §7You have to wait §c§l%s% §7until you can use the totem again.";
    public static String ANNOUNCE_FINISHED_MESSAGE = "%prefix% §7You are able to use your free totem again!";
    public static String RESET_MESSAGE = "%prefix% §7Your totem timer got §areset§7!";

    public static boolean USE_PERMISSION = true;
    public static boolean USE_TIMER = true;
    public static boolean USE_TIMER_ANNOUNCE = true;
    public static boolean ANNOUNCE_WHEN_FINISHED = true;
    public static boolean USE_RESET = true;
    public static boolean SEND_MESSAGE = true;
    public static boolean TIMER_FOR_VANILLA = true;

    public static int TIMER = 20;

    public static final HashMap<Player, TotemPlayer> PLAYER_TOTEM_CACHE = new HashMap<>();

    public static void useTotem(final Player player) {
        ItemStack offHandItems;
        if (!player.getInventory().getItemInOffHand().getType().equals(Material.AIR)) {
            offHandItems = player.getInventory().getItemInOffHand();
        } else {
            offHandItems = null;
        }

        int index = -1;
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            if (player.getInventory().getItem(i) != null) {
                if (Objects.requireNonNull(player.getInventory().getItem(i)).getType().equals(Material.TOTEM_OF_UNDYING)) {
                    if (index == -1) {
                        index = i;
                    }
                }
            }
        }

        if (index == -1) {
            player.sendMessage("§cThere was an error while executing function useTotem in Utils. Please contact the developer of this resource!");
            return;
        }

        ItemStack totem = player.getInventory().getItem(index);
        player.getInventory().setItemInOffHand(totem);
        player.getInventory().removeItem(new ItemStack(Material.TOTEM_OF_UNDYING));

        Bukkit.getScheduler().scheduleSyncDelayedTask(AutoTotem.getInstance(), () -> {
            if (offHandItems != null) {
                player.getInventory().setItemInOffHand(offHandItems);
            } else {
                player.getInventory().setItemInOffHand(null);
            }
        }, 1L);

    }

    public static boolean hasPlayerTotem(final Player player) {
        boolean result = false;

        int amount = 0;
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            if (player.getInventory().getItem(i) != null) {
                if (Objects.requireNonNull(player.getInventory().getItem(i)).getType().equals(Material.TOTEM_OF_UNDYING)) {
                    amount++;
                }
            }
        }

        if (amount >= 1) {
            result = true;
        }

        return result;
    }

}
