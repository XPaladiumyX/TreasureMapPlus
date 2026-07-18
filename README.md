# TreasureMapsPlus - Paper 26.1.2 (SkyXNetwork Fork)

> **This is not the official plugin.** This is a modified fork updated for Paper 26.1.2 / MC 1.21.11 support, specifically adapted for the **SkyXNetwork** server.

## About

[TreasureMapsPlus](https://github.com/MC-Machinations/TreasureMapsPlus) is a Paper plugin that modifies the behavior of treasure maps in Minecraft. This fork has been updated to work with **Paper 26.1.2** (Minecraft 1.21.11) after the original plugin became incompatible due to major API changes in this version.

## What changed

### Migration to Paper 26.1.2

The plugin has been updated to support the following API changes:

| Change | Before (1.21.6) | After (26.1.2) |
|---|---|---|
| `ResourceLocation` | `net.minecraft.resources.ResourceLocation` | `net.minecraft.resources.Identifier` |
| `LootItemFunctionType` | Existing class | Removed, direct `MapCodec` usage |
| `VillagerTrades` inner classes | `TreasureMapForEmeralds`, `TypeSpecificTrade` | Removed, data-driven system with `ResourceKey<VillagerTrade>` |
| Villager trade system | `VillagerTrades.TRADES` (static map) | `Registries.VILLAGER_TRADE` (data-driven registry) |
| Java | 21 | 25 (required by Paper 26.1.2) |

### Modified files

- `RegistryOverride.java` - Removed `reflectionRemapper` dependency (unused at runtime)
- `ExplorationMapItemFunctionOverride.java` - Adapted to new loot function system
- `VillagerTradeOverride.java` - Full rewrite for new data-driven trade system
- `ServerLoad.java` - Pass `RegistryAccess` to constructor
- `PlayerInteract.java` - `ResourceLocation` -> `Identifier`
- `ExplorationMapItemFunctionOverrideTest.java` - Updated types
- `VillagerTradeOverrideTest.java` - Adapted to new constructor

### Deleted files

- `TypeSpecificTradeProxy.java` - NMS inner class no longer exists
- `TreasureMapForEmeraldsProxy.java` - NMS inner class no longer exists

### Build config

- `gradle/libs.versions.toml` - Minecraft `26.1.2`, paperweight `2.0.0-beta.21`
- `build.gradle.kts` - Java 25, dev bundle format updated
- `paper-plugin.yml` - api-version `26.1.2`

## Installation

1. Download `TreasureMapsPlus-0.8.1-SNAPSHOT-all.jar`
2. Place it in your Paper 26.1.2 server's `plugins/` folder
3. Restart the server

## Configuration

The plugin's `config.yml` allows you to configure:

```yaml
replace:
  chests: false        # Replace treasure maps in loot chests
  villagers:
    monument: false    # Replace ocean explorer map trades from cartographers
    mansion: false     # Replace woodland mansion map trades from cartographers

messages:
  map:
    use:               # Lore added to treasure maps
      - "<gray>Right click to reveal the map"
```

## Credits

- **Original plugin**: [Machine-Maker](https://github.com/MC-Machinations/TreasureMapsPlus) - Licensed under GPL-3.0
- **Fork**: XPAladiumyX / SkyXNetwork

## License

This plugin is distributed under the **GNU General Public License v3.0**. See the `LICENSE` file for details.
