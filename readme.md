SimpleSounds is a simple plugin to add configurable sounds to common events.

Supported events are:
 - Player join
 - Player quit
 - Player command
 - Player chat
 - Player death
 - Chat mention (when a chat message contains your username)
 - Arrow hit mob

Commands:
 - /ss reload\
   Reloads the config\
   Permission: simplesounds.cmd.reload
 - /ss test [event]\
   Plays the sound in the config for the specified event\
   Permission: simplesounds.cmd.test

Permissions:
 - simplesounds.cmd\
   Base command for SimpleSounds commands
 - simplesounds.cmd.reload\
   Permission to reload the config
 - simplesounds.cmd.test\
   Permission to test a sound

Config:
```yaml
player_join:
  enabled: true
  name: BLOCK_NOTE_BLOCK_PLING
  pitch: 1.0
  volume: 1.0
player_quit:
  enabled: true
  name: BLOCK_NOTE_BLOCK_BASS
  pitch: 1.0
  volume: 1.0
player_command:
  enabled: true
  name: ENTITY_ITEM_PICKUP
  pitch: 1.5
  volume: 1.0
player_chat:
  enabled: true
  name: ENTITY_ITEM_PICKUP
  pitch: 1.0
  volume: 0.5
chat_mention:
  enabled: true
  name: BLOCK_NOTE_BLOCK_BIT
  pitch: 1.0
  volume: 1.0
arrow_hit_mob:
  enabled: true
  name: ENTITY_ARROW_HIT_PLAYER
  pitch: 1.0
  volume: 1.0
player_death:
  enabled: true
  name: ENTITY_VILLAGER_DEATH
  pitch: 1.0
  volume: 1.0
```