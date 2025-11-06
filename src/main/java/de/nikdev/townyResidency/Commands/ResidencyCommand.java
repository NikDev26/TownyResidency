package de.nikdev.townyResidency.Commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.bukkit.Bukkit.getLogger;

public class ResidencyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player p) {
            Resident resident = TownyAPI.getInstance().getResident(p);
            try {
                assert resident != null;

                Nation nation;
                String nationName;
                if (resident.hasNation()) {
                    nation = resident.getNation();
                    nationName = nation.getName();
                }
                else {
                    getLogger().warning("Player doesn't belong to any Nation.");
                    getLogger().info("Setting NationName to 'No Nation'");

                    nationName = "No Nation";
                }

                Town town;
                String townName;
                if (resident.hasTown()) {
                    town = resident.getTown();
                    townName = town.getName();
                }
                else {
                    getLogger().warning("Player doesn't belong to any Town.");
                    getLogger().info("Setting TownName to 'No Town'");

                    townName = "No Town";
                }

                ItemStack residencyPaper = new ItemStack(Material.WRITTEN_BOOK, 1);
                BookMeta bookMeta = (BookMeta) residencyPaper.getItemMeta();
                if (bookMeta == null) {
                    getLogger().warning("bookMeta is null");
                    return false;
                }

                bookMeta.setTitle("Residency Paper");
                bookMeta.setAuthor("TownyResidency");
                bookMeta.setRarity(ItemRarity.EPIC);

                TextComponent line1 = new TextComponent("Residency Paper of: ");
                TextComponent line1_1 = new TextComponent(p.getName() + "\n\n");
                line1_1.setColor(net.md_5.bungee.api.ChatColor.of("#2E8924"));
                line1.addExtra(line1_1);

                String title = (resident.getTitle() != null && !resident.getTitle().isBlank())
                        ? resident.getTitle()
                        : ChatColor.RED + "No Title";
                TextComponent lineTitle = new TextComponent("Title: " + title + "\n\n");

                TextComponent lineNation = new TextComponent("Nation: " + nationName + "\n");
                lineNation.setColor(net.md_5.bungee.api.ChatColor.of("#e3b719"));

//                List<String> nationRanks = (resident.getNationRanks() != null) ? resident.getNationRanks() : List.of("No Ranks");
                TextComponent lineNationRanks = new TextComponent("Nation Ranks: " + resident.getNationRanks() + "\n");

                TextComponent lineTown = new TextComponent("Town: " + townName + "\n");
                lineTown.setColor(net.md_5.bungee.api.ChatColor.of("#37e6dd"));

//                List<String> townRanks = (resident.getNationRanks() != null) ? resident.getTownRanks() : List.of("No Ranks");
                TextComponent lineTownRanks = new TextComponent("Town Ranks: " + resident.getTownRanks() + "\n\n");

                LocalDateTime dateTimeNow = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
                String formattedDateTime = dateTimeNow.format(formatter);

                TextComponent lineDateTime = new TextComponent("Issued by TownyResidency at: " + formattedDateTime);

                BaseComponent[] page = new BaseComponent[] {
                        line1, lineTitle, lineNation, lineNationRanks, lineTown, lineTownRanks
                };

                BaseComponent[] page1 = new BaseComponent[] {
                        lineDateTime
                };

                bookMeta.spigot().addPage(page);
                bookMeta.spigot().addPage(page1);

                residencyPaper.setItemMeta(bookMeta);

                p.getInventory().addItem(residencyPaper);
            } catch (TownyException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }
}
