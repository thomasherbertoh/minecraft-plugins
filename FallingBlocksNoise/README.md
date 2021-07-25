## Version Details

Minecraft version: 1.16.5\
Plugin version: 1.0

## Plugin Details

This plugin applies gravity to the blocks around you using perlin noise to determine to what vertical extent to do so. Whilst this `FallingBlocks` plugin is much more performant than the original version, it is still not recommended to use this plugin on a serious survival world as it is not just laggy but also buggy due to the way Minecraft manages `FallingBlock` entities.

## Plugin Commands

- `getGravity`: Tells you whether or not the plugin is currently active
- `enableGravity`: Enables the plugin
- `disableGravity`: Disables the plugin
- `getRange`: Tells you the current horizontal range the plugin is working on
- `setRange`: When passed an integer, sets the horizontal range to the given number of blocks (values above 32 NOT recommended)
