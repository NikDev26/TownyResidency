package de.nikdev.townyResidency.Commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
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

public class ResidencyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player p) {
            Resident resident = TownyAPI.getInstance().getResident(p);
            try {
                Nation nation = resident.getNation();
                Town town = resident.getTown();

                ItemStack residencyPaper = new ItemStack(Material.WRITTEN_BOOK, 1);
                BookMeta bookMeta = (BookMeta) residencyPaper.getItemMeta();

                bookMeta.setTitle("Residency Paper");
                bookMeta.setAuthor("ThaurosiaCraft");
                bookMeta.setRarity(ItemRarity.EPIC);
                TextComponent line1 = new TextComponent("Residency Paper of: ");
                TextComponent line1_1 = new TextComponent(p.getName() + "\n\n");
                line1_1.setColor(net.md_5.bungee.api.ChatColor.of("#2E8924"));
                line1.addExtra(line1_1);

                TextComponent lineTitle = new TextComponent("Title: " + resident.getTitle() + "\n\n");

                TextComponent lineNation = new TextComponent("Nation: " + nation.getName() + "\n");
                lineNation.setColor(net.md_5.bungee.api.ChatColor.of("#e3b719"));
                TextComponent lineNationRanks = new TextComponent("Nation Ranks: " + resident.getNationRanks() + "\n");

                TextComponent lineTown = new TextComponent("Town: " + town.getName() + "\n");
                lineTown.setColor(net.md_5.bungee.api.ChatColor.of("#37e6dd"));
                TextComponent lineTownRanks = new TextComponent("Town Ranks: " + resident.getTownRanks() + "\n\n");

                TextComponent link = new TextComponent("Click here to visit Discord!");
                link.setColor(net.md_5.bungee.api.ChatColor.BLUE);
                link.setUnderlined(true);
                link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click to open Discord")));
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/scTKuWDgZ9"));

                LocalDateTime dateTimeNow = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
                String formattedDateTime = dateTimeNow.format(formatter);

                TextComponent lineDateTime = new TextComponent("Issued by TownyResidency at: " + formattedDateTime);

                BaseComponent[] page = new BaseComponent[] {
                        line1, lineTitle, lineNation, lineNationRanks, lineTown, lineTownRanks, link
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
