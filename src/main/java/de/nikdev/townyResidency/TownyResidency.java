package de.nikdev.townyResidency;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TownyResidency extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("My First Plugin has started. Hi!");
        getServer().getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        System.out.println("A Player has joined the server.");
        String playerName = event.getPlayer().getName();
        event.setJoinMessage("Welcome to the Pit " + playerName + ".");
    }

    @EventHandler
    public void onPlayerLeaveBed(PlayerBedLeaveEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("You left the Bed karuz.");
        player.setHealth(0);
    }

//    @Override
//    public void onDisable() {
//        // Plugin shutdown logic
//        System.out.println("My First Plugin has stopped. Bye!");
//    }
}
