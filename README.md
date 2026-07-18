# TreasureMapsPlus — Paper 26.1.2 (SkyXNetwork Fork)

> **Ceci n'est pas le plugin officiel.** Il s'agit d'une fork modifiée pour le support de Paper 26.1.2 / MC 1.21.11, spécialement adaptée pour le serveur **SkyXNetwork**.

## À propos

[TreasureMapsPlus](https://github.com/MC-Machinations/TreasureMapsPlus) est un plugin Paper qui modifie le comportement des cartes au trésor dans Minecraft. Ce fork a été mis à jour pour fonctionner avec **Paper 26.1.2** (Minecraft 1.21.11) après que le plugin original n'ait plus été compatible suite aux changements majeurs d'API dans cette version.

## Ce qui a été modifié

### Migration vers Paper 26.1.2

Le plugin a été mis à jour pour supporter les changements d'API suivants :

| Changement | Avant (1.21.6) | Après (26.1.2) |
|---|---|---|
| `ResourceLocation` | `net.minecraft.resources.ResourceLocation` | `net.minecraft.resources.Identifier` |
| `LootItemFunctionType` | Classe existante | Supprimée — utilisation directe de `MapCodec` |
| `VillagerTrades` inner classes | `TreasureMapForEmeralds`, `TypeSpecificTrade` | Supprimées — système data-driven avec `ResourceKey<VillagerTrade>` |
| Système de villager trades | `VillagerTrades.TRADES` (map statique) | `Registries.VILLAGER_TRADE` (registry data-driven) |
| Java | 21 | 25 (requis par Paper 26.1.2) |

### Fichiers modifiés

- `RegistryOverride.java` — Suppression de la dépendance `reflectionRemapper` (inutile en runtime)
- `ExplorationMapItemFunctionOverride.java` — Adaptation au nouveau système de loot functions
- `VillagerTradeOverride.java` — Réécriture complète pour le nouveau système de trades data-driven
- `ServerLoad.java` — Passage de `RegistryAccess` au constructeur
- `PlayerInteract.java` — `ResourceLocation` → `Identifier`
- `ExplorationMapItemFunctionOverrideTest.java` — Types mis à jour
- `VillagerTradeOverrideTest.java` — Adapté au nouveau constructeur

### Fichiers supprimés

- `TypeSpecificTradeProxy.java` — Classe interne NMS supprimée
- `TreasureMapForEmeraldsProxy.java` — Classe interne NMS supprimée

### Configuration build

- `gradle/libs.versions.toml` — Minecraft `26.1.2`, paperweight `2.0.0-beta.21`
- `build.gradle.kts` — Java 25, dev bundle format mis à jour
- `paper-plugin.yml` — api-version `26.1.2`

## Installation

1. Téléchargez le fichier `TreasureMapsPlus-0.8.1-SNAPSHOT-all.jar`
2. Placez-le dans le dossier `plugins/` de votre serveur Paper 26.1.2
3. Redémarrez le serveur

## Configuration

Le fichier `config.yml` du plugin permet de configurer :

```yaml
replace:
  chests: false        # Remplacer les cartes au trésor dans les coffres
  villagers:
    monument: false    # Remplacer les trades de carte ocean chez les cartographes
    mansion: false     # Remplacer les trades de carte manoir chez les cartographes

messages:
  map:
    use:               # Lore ajoutee aux cartes au tresor
      - "<gray>Cliquez droit pour reveler la carte"
```

## Credits

- **Plugin original** : [Machine-Maker](https://github.com/MC-Machinations/TreasureMapsPlus) — Licensed under GPL-3.0
- **Fork** : XPAladiumyX / SkyXNetwork

## Licence

Ce plugin est distribue sous la licence **GNU General Public License v3.0**. Voir le fichier `LICENSE` pour plus de details.
