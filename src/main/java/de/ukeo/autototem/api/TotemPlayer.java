package de.ukeo.autototem.api;

import de.ukeo.autototem.AutoTotem;
import de.ukeo.autototem.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityResurrectEvent;

public final class TotemPlayer {

    private final Player player;
    private int timer = 20;
    private boolean ableToDie = true;
    private int scheduler;
    private boolean running = false;

    public TotemPlayer(Player player) {
        this.player = player;
    }

    public void checkPlayer(EntityResurrectEvent event) {
        if (ableToDie) {
            if (Utils.hasPlayerTotem(player)) {
                Utils.useTotem(player);
                event.setCancelled(false);
                if (Utils.USE_TIMER) {
                    if (!running) {
                        runTimer();
                    } else {
                        if (Utils.USE_RESET) {
                            resetTimer();
                            if (Utils.SEND_MESSAGE) {
                                player.sendMessage(Utils.RESET_MESSAGE);
                            }
                        }
                    }
                }
            }
        } else {
            if (Utils.TIMER_FOR_VANILLA) {
                if (player.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING) ||
                player.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING)) {
                    event.setCancelled(true);
                }
            } else {
                if (player.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING) ||
                        player.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING)) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                }
            }
            if (Utils.USE_RESET) {
                resetTimer();
                if (Utils.SEND_MESSAGE) {
                    player.sendMessage(Utils.RESET_MESSAGE);
                }
            }
        }
    }

    public void runTimer() {
        System.out.println("Run Timer called");
        if (!running) {
            running = true;
            //Setting time to current configs default
            timer = Utils.TIMER;
            ableToDie = false;
            scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(AutoTotem.getInstance(), () -> {
                timer--;
                if (timer <= 0) {
                    System.out.println("Timer reset natural");
                    Bukkit.getScheduler().cancelTask(scheduler);
                    ableToDie = true;
                    running = false;
                    timer = Utils.TIMER;
                    if (Utils.ANNOUNCE_WHEN_FINISHED) {
                        player.sendMessage(Utils.ANNOUNCE_FINISHED_MESSAGE);
                    }
                }
            }, 20L, 20L);
        }
    }

    public void resetTimer() {
        Bukkit.getScheduler().cancelTask(scheduler);
        running = false;
        ableToDie = true;
        System.out.println("Timer reset by reset function");
    }

    public Player getPlayer() {
        return player;
    }

    public int getTimer() {
        return timer;
    }

    public boolean isAbleToDie() {
        return ableToDie;
    }

    public boolean isRunning() {
        return running;
    }

    public void setAbleToDie(boolean ableToDie) {
        this.ableToDie = ableToDie;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
