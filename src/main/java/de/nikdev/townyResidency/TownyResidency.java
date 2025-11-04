package de.nikdev.townyResidency;

import com.palmergames.bukkit.towny.TownyAPI;
import de.nikdev.townyResidency.Commands.ResidencyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class TownyResidency extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (getServer().getPluginManager().getPlugin("Towny") == null) {
            getLogger().severe("Towny not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        TownyAPI api = TownyAPI.getInstance();
        getLogger().info("Towny found! Loaded version: " + api);

        getCommand("residency").setExecutor(new ResidencyCommand());
    }


    //    @Override
//    public void onDisable() {
//        // Plugin shutdown logic
//        System.out.println("My First Plugin has stopped. Bye!");
//    }
}
