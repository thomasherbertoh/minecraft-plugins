# About `minecraft-plugins`

This is a collection of Minecraft plugins that I've written to add quality-of-life features or to experiment.

## Plugins included

- `PowerfulMobs`: Increases the difficulty by replacing certain mobs with harder-to-kill counterparts
- `FallingBlocks`: Makes blocks within a customisable distance of the player(s) be affected by gravity (not recommended for serious survival play)
- `FallingBlocksNoise`: Implements the same functionality as `FallingBlocks`, but uses perlin noise to decide the depth and elevation at which to apply gravity (more lightweight than `FallingBlocks` and more realistic)
- `TreeFeller`: When the bottom log of a tree is destroyed with a wooden axe, the whole tree along with any leaves that would have decayed naturally will be destroyed. Currently very naive and barely working. Will destroy literally any pillar of logs without a second thought. Works best on small, well-isolated trees.
- `Manhunt`: Simple implementation of the plugin used by YouTuber `Dream` in his Minecraft manhunt videos
- `AntiPVP`: Discourages PvP in an unconventional way

## Plugins yet to be uploaded

- `FallingBlocksAsync`: Implements the same functionality as `FallingBlocks`, but tries to offload some of the work onto another thread to improve performance
- `BetterCompass`: Allows players to make their compass point to a location or player of their choice
- `MinecraftSudoku`: Allows the player to play sudoku in Minecraft (checker and solver included)
- `AntiRain`: Stops it from raining on the server

## Plugins in progress

- `AutoSleep`: Allows one player to sleep on behalf of all the other players
- `HealthDisplay`: Shows the player's current health above their head
