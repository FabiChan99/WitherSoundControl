package com.fabichan.WitherSoundControl.Commands;

import com.fabichan.WitherSoundControl.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WitherSoundControlCommandHandler implements CommandExecutor, TabExecutor{
    private final Main plugin;

    public WitherSoundControlCommandHandler(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(ChatColor.GRAY + "WitherSoundControl " + ChatColor.DARK_GRAY
                    + ChatColor.RESET + ChatColor.DARK_GRAY + "v" + plugin.getDescription().getVersion());


            commandSender.sendMessage(ChatColor.GRAY + "/withersoundcontrol " + ChatColor.DARK_GRAY + "-"
                    + ChatColor.RESET + " Plugin information");
            if (commandSender.hasPermission("wsc.reload")) {
                commandSender.sendMessage(ChatColor.GRAY + "/withersoundcontrol reload " + ChatColor.DARK_GRAY + "-"
                        + ChatColor.RESET + " Plugin reload");
            }
            commandSender.sendMessage(ChatColor.DARK_GRAY + "Author: " + ChatColor.GRAY + "Fabi-Chan");
        } else if (args[0].equals("reload")) {
            if (commandSender.hasPermission("wsc.reload")) {
                plugin.reloadConfig();
                commandSender.sendMessage(ChatColor.GRAY + "WitherSoundControl" + ChatColor.DARK_GRAY + " => "
                        + ChatColor.RESET + "Reloaded Configuration");
            }
            else {
                commandSender.sendMessage(ChatColor.GRAY + "WitherSoundControl" + ChatColor.DARK_GRAY + " => " + ChatColor.RESET
                        + "No Permissions");
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command,
                                      @NotNull String string, @NotNull String [] args) {
        if (commandSender.hasPermission("wsc.reload")) {
            return Collections.singletonList("reload");
        }

        return new ArrayList<>();
    }

}