package de.nikdev.townyResidency;

import com.palmergames.bukkit.towny.TownyAPI;
import de.nikdev.townyResidency.Commands.RequestResidencyCommand;
import de.nikdev.townyResidency.Commands.ResidencyCommand;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getLogger;

public final class TownyResidency extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Hello from TownyResidency. Looking if Towny is installed...");
        if (getServer().getPluginManager().getPlugin("Towny") == null) {
            getLogger().severe("Towny not found, please download Towny to use this plugin! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        TownyAPI api = TownyAPI.getInstance();
        getLogger().info("Towny found! Loaded version: " + api);

        getCommand("residency").setExecutor(new ResidencyCommand());
        getCommand("request_residency").setExecutor(new RequestResidencyCommand());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin is shutting down, good night...");
    }
}
