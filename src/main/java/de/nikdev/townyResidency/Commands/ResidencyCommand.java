package de.nikdev.townyResidency.Commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class ResidencyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player p) {
            Resident resident = TownyAPI.getInstance().getResident(p);
            try {
                Nation nation = resident.getNation();
                Town town = resident.getTown();

                p.sendMessage(ChatColor.YELLOW + "Nation: " + nation + ChatColor.RESET + " | " + ChatColor.AQUA + "Town: " + town);
                ItemStack residencyPaper = new ItemStack(Material.WRITTEN_BOOK, 1);
                BookMeta bookMeta = (BookMeta) residencyPaper.getItemMeta();

                bookMeta.setTitle("Residency Paper");
                bookMeta.setAuthor("ThaurosiaCraft");
                bookMeta.setRarity(ItemRarity.EPIC);
                bookMeta.addPage(
                        "Residency Paper of: " + p.getDisplayName() + "\n\n" +
                                ChatColor.DARK_GREEN +"Nation: " +  nation.getName() + ChatColor.WHITE +"\n" +
                                ChatColor.DARK_AQUA +"Town: " + town.getName() + ChatColor.WHITE + "\n\n" +
                                ChatColor.BOLD + ChatColor.DARK_PURPLE + "Issued by TownyResidency, by NikDev26."
                );

                residencyPaper.setItemMeta(bookMeta);

                p.getInventory().addItem(residencyPaper);
            } catch (TownyException e) {
                throw new RuntimeException(e);
            }


        }

        return true;
    }
}
