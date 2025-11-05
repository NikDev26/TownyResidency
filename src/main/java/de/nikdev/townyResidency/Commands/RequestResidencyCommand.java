package de.nikdev.townyResidency.Commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.awt.*;
import java.util.Date;

public class RequestResidencyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player p) {
            if (args.length == 0) {
                p.sendMessage("You need to specify a player.");
            }
            else {
                String playerName = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playerName);

                if (target == null) {
                    p.sendMessage("This Player either doesn't exist or is offline.");
                } else {
                    Resident resident = TownyAPI.getInstance().getResident(target);
                    try {
                        Nation nation = resident.getNation();
                        Town town = resident.getTown();

                        p.sendMessage(ChatColor.YELLOW + "Nation: " + nation + ChatColor.RESET + " | " + ChatColor.AQUA + "Town: " + town);
                        ItemStack residencyPaper = new ItemStack(Material.WRITTEN_BOOK, 1);
                        BookMeta bookMeta = (BookMeta) residencyPaper.getItemMeta();

                        bookMeta.setTitle("Residency Paper");
                        bookMeta.setAuthor("ThaurosiaCraft");
                        bookMeta.setRarity(ItemRarity.EPIC);
                        net.md_5.bungee.api.chat.TextComponent line1 = new net.md_5.bungee.api.chat.TextComponent("Residency Paper of: ");
                        net.md_5.bungee.api.chat.TextComponent line1_1 = new net.md_5.bungee.api.chat.TextComponent(p.getName() + "\n\n");
                        line1_1.setColor(net.md_5.bungee.api.ChatColor.of("#bf9b30"));
                        line1.addExtra(line1_1);

                        net.md_5.bungee.api.chat.TextComponent line2 = new net.md_5.bungee.api.chat.TextComponent("Title: " + resident.getTitle() + "\n");
                        net.md_5.bungee.api.chat.TextComponent line3 = new net.md_5.bungee.api.chat.TextComponent("Town Rank: " + resident.getTownRanks() + "\n");
                        net.md_5.bungee.api.chat.TextComponent line4 = new net.md_5.bungee.api.chat.TextComponent("Nation Rank: " + resident.getNationRanks() + "\n\n");

                        net.md_5.bungee.api.chat.TextComponent line5 = new net.md_5.bungee.api.chat.TextComponent("Nation: " + nation.getName() + "\n");
                        line5.setColor(net.md_5.bungee.api.ChatColor.of("#e3b719"));
                        net.md_5.bungee.api.chat.TextComponent line6 = new net.md_5.bungee.api.chat.TextComponent("Town: " + town.getName() + "\n\n");
                        line6.setColor(net.md_5.bungee.api.ChatColor.of("#37e6dd"));

                        net.md_5.bungee.api.chat.TextComponent link = new TextComponent("Click here to visit Discord!");
                        link.setColor(net.md_5.bungee.api.ChatColor.BLUE);
                        link.setUnderlined(true);
                        link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click to open Discord")));
                        link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/scTKuWDgZ9"));


                        BaseComponent[] page = new BaseComponent[] {
                                line1, line2, line3, line4, line5, line6, link
                        };

                        bookMeta.spigot().addPage(page);

                        residencyPaper.setItemMeta(bookMeta);

                        p.getInventory().addItem(residencyPaper);
                    } catch (TownyException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return true;
    }
}
