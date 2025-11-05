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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
                line1_1.setColor(net.md_5.bungee.api.ChatColor.of("#bf9b30"));
                line1.addExtra(line1_1);

                TextComponent line2 = new TextComponent("Title: " + resident.getTitle() + "\n");

                TextComponent line3 = new TextComponent("Town Ranks: " + resident.getTownRanks() + "\n");
                TextComponent line4 = new TextComponent("Nation Ranks: " + resident.getNationRanks() + "\n\n");

                TextComponent line5 = new TextComponent("Nation: " + nation.getName() + "\n");
                line5.setColor(net.md_5.bungee.api.ChatColor.of("#e3b719"));
                TextComponent line6 = new TextComponent("Town: " + town.getName() + "\n\n");
                line6.setColor(net.md_5.bungee.api.ChatColor.of("#37e6dd"));

                TextComponent link = new TextComponent("Click here to visit Discord!");
                link.setColor(net.md_5.bungee.api.ChatColor.BLUE);
                link.setUnderlined(true);
                link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click to open Discord")));
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/scTKuWDgZ9"));

                LocalDateTime dateTimeNow = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
                String formattedDateTime = dateTimeNow.format(formatter);

                TextComponent line7 = new TextComponent("Issued by TownyResidency at: " + formattedDateTime);

                BaseComponent[] page = new BaseComponent[] {
                        line1, line2, line3, line4, line5, line6, link
                };

                BaseComponent[] page1 = new BaseComponent[] {
                        line7
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
