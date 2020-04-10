package com.darkender.plugins.simplesounds;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class SoundEventsListener implements Listener
{
    private final SimpleSounds simpleSounds;
    private final List<String> testCommandNames = new ArrayList<>();
    
    public SoundEventsListener(SimpleSounds simpleSounds)
    {
        this.simpleSounds = simpleSounds;
        
        // Get the plugin command to ignore the /simplesounds test command
        PluginCommand pluginCommand = simpleSounds.getCommand("simplesounds");
        testCommandNames.add(pluginCommand.getName());
        testCommandNames.addAll(pluginCommand.getAliases());
        int originalLength = testCommandNames.size();
        for(int i = 0; i < originalLength; i++)
        {
            testCommandNames.add(simpleSounds.getName().toLowerCase() + ":" + testCommandNames.get(i));
        }
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        SimpleSoundData sound = simpleSounds.getSound(SimpleSoundEvent.PLAYER_JOIN);
        if(sound.isEnabled())
        {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                sound.play(p);
            }
        }
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        SimpleSoundData sound = simpleSounds.getSound(SimpleSoundEvent.PLAYER_QUIT);
        if(sound.isEnabled())
        {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                sound.play(p);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        SimpleSoundData sound = simpleSounds.getSound(SimpleSoundEvent.PLAYER_CHAT);
        
        String upperMsg = event.getMessage().toUpperCase();
        if(sound.isEnabled())
        {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                if(upperMsg.contains(p.getName().toUpperCase()))
                {
                    simpleSounds.getSound(SimpleSoundEvent.CHAT_MENTION).play(p);
                }
                sound.play(p);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event)
    {
        SimpleSoundData sound = simpleSounds.getSound(SimpleSoundEvent.PLAYER_COMMAND);
        if(sound.isEnabled())
        {
            for(String commandName : testCommandNames)
            {
                if(event.getMessage().startsWith("/" + commandName + " test"))
                {
                    return;
                }
            }
            sound.play(event.getPlayer());
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if(event.getDamager().getType() == EntityType.ARROW &&
           event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE)
        {
            Arrow arrow = (Arrow) event.getDamager();
            if(!(arrow.getShooter() instanceof Player))
            {
                return;
            }
            
            SimpleSoundData sound = simpleSounds.getSound(SimpleSoundEvent.ARROW_HIT_MOB);
            if(sound.isEnabled())
            {
                sound.play((Player) arrow.getShooter());
            }
        }
    }
}
