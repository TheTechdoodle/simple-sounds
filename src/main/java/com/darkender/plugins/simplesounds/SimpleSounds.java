package com.darkender.plugins.simplesounds;

import com.darkender.plugins.simplesounds.commands.SimpleSoundCommand;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class SimpleSounds extends JavaPlugin
{
    private final HashMap<SimpleSoundEvent, SimpleSoundData> sounds = new HashMap<>();
    private final String[] requiredKeys = {"enabled", "name", "pitch", "volume"};
    
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new SoundEventsListener(this), this);
        SimpleSoundCommand ssCmd = new SimpleSoundCommand(this);
        getCommand("simplesounds").setExecutor(ssCmd);
        getCommand("simplesounds").setTabCompleter(ssCmd);
        reload(getServer().getConsoleSender());
    }
    
    public void reload(CommandSender sender)
    {
        saveDefaultConfig();
        reloadConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        sounds.clear();
        for(SimpleSoundEvent event : SimpleSoundEvent.values())
        {
            if(getConfig().contains(event.name().toLowerCase()))
            {
                boolean valid = true;
                for(String key : requiredKeys)
                {
                    if(!getConfig().contains(event.name().toLowerCase() + "." + key))
                    {
                        sender.sendMessage(ChatColor.RED + "Missing key \"" + key + "\" for " + event.name().toLowerCase());
                        valid = false;
                    }
                }
                if(!valid)
                {
                    sounds.put(event, new SimpleSoundData());
                    continue;
                }
    
                Sound selectedSound;
                try
                {
                    selectedSound = Sound.valueOf(getConfig().getString(event.name().toLowerCase() + ".name"));
                }
                catch(IllegalArgumentException e)
                {
                    sender.sendMessage(ChatColor.RED + "Invalid sound name \"" +
                            getConfig().getString(event.name().toLowerCase() + ".name") + "\""
                            + " for " + event.name().toLowerCase());
                    sounds.put(event, new SimpleSoundData());
                    continue;
                }
    
                sounds.put(event, new SimpleSoundData(selectedSound,
                        (float) getConfig().getDouble(event.name().toLowerCase() + ".pitch"),
                        (float) getConfig().getDouble(event.name().toLowerCase() + ".pitch"),
                        getConfig().getBoolean(event.name().toLowerCase() + ".enabled")));
            }
            else
            {
                sounds.put(event, new SimpleSoundData());
            }
        }
        sender.sendMessage(ChatColor.GREEN + "SimpleSounds reloaded!");
    }
    
    public SimpleSoundData getSound(SimpleSoundEvent event)
    {
        return sounds.get(event);
    }
}
