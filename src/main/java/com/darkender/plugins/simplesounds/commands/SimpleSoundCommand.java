package com.darkender.plugins.simplesounds.commands;

import com.darkender.plugins.simplesounds.SimpleSoundEvent;
import com.darkender.plugins.simplesounds.SimpleSounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleSoundCommand implements CommandExecutor, TabCompleter
{
    private SimpleSounds simpleSounds;
    
    public SimpleSoundCommand(SimpleSounds simpleSounds)
    {
        this.simpleSounds = simpleSounds;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(args.length == 0)
        {
            sender.sendMessage(ChatColor.GREEN + "SimpleSounds v" + simpleSounds.getDescription().getVersion() + " "
                                + ChatColor.GOLD + " by Techdoodle");
            sender.sendMessage(ChatColor.GOLD + simpleSounds.getDescription().getDescription());
            sender.sendMessage(ChatColor.AQUA + "Use \"/simplesounds reload\" or \"/simplesounds test <event>\"");
            return true;
        }
        
        if(args[0].equalsIgnoreCase("reload"))
        {
            if(!sender.hasPermission("simplesounds.cmd.reload"))
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return true;
            }
            
            simpleSounds.reload(sender);
            return true;
        }
        else if(args[0].equalsIgnoreCase("test"))
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
                return true;
            }
            
            if(!sender.hasPermission("simplesounds.cmd.test"))
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return true;
            }
            
            if(args.length == 2)
            {
                SimpleSoundEvent event;
                try
                {
                    event = SimpleSoundEvent.valueOf(args[1].toUpperCase());
                }
                catch(IllegalArgumentException e)
                {
                    sender.sendMessage(ChatColor.RED + "Event \"" + args[1] + "\" not found!");
                    return true;
                }
                simpleSounds.getSound(event).play((Player) sender);
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Event name not provided!");
            }
            return true;
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Unknown argument!");
            return false;
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        if(args.length < 2)
        {
            return Arrays.asList(new String[]{"reload", "test"});
        }
        else if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("test"))
            {
                List<String> eventsList = new ArrayList<>();
                for(SimpleSoundEvent event : SimpleSoundEvent.values())
                {
                    eventsList.add(event.name().toLowerCase());
                }
                return eventsList;
            }
        }
        
        return new ArrayList<String>();
    }
}
