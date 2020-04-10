package com.darkender.plugins.simplesounds;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SimpleSoundData
{
    private Sound sound = null;
    private float pitch = 0.0f;
    private float volume = 0.0f;
    private boolean enabled = false;
    
    public SimpleSoundData()
    {
    
    }
    
    public SimpleSoundData(Sound sound, float pitch, float volume, boolean enabled)
    {
        this.sound = sound;
        this.pitch = pitch;
        this.volume = volume;
        this.enabled = enabled;
    }
    
    /**
     * Plays the sound to the specified player but only if enabled
     * @param player The player to play the sound to
     */
    public void play(Player player)
    {
        if(!enabled)
        {
            return;
        }
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
    
    public Sound getSound()
    {
        return sound;
    }
    
    public float getPitch()
    {
        return pitch;
    }
    
    public float getVolume()
    {
        return volume;
    }
    
    public boolean isEnabled()
    {
        return enabled;
    }
}
