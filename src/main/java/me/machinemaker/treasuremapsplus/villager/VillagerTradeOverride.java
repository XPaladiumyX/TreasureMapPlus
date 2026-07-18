/*
 * GNU General Public License v3
 *
 * TreasureMapsPlus, a plugin to alter treasure maps
 *
 * Copyright (C) 2023 Machine-Maker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.machinemaker.treasuremapsplus.villager;

import com.mojang.logging.LogUtils;
import io.papermc.paper.adventure.PaperAdventure;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.machinemaker.treasuremapsplus.RegistryOverride;
import me.machinemaker.treasuremapsplus.TreasureMapsPlus;
import me.machinemaker.treasuremapsplus.Utils;
import net.kyori.adventure.text.Component;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import org.jetbrains.annotations.VisibleForTesting;
import org.slf4j.Logger;

public class VillagerTradeOverride {

    private static final Logger LOGGER = LogUtils.getClassLogger();

    private static final ResourceKey<VillagerTrade> OCEAN_EXPLORER_KEY = VillagerTrades.CARTOGRAPHER_3_EMERALD_AND_COMPASS_OCEAN_EXPLORER_MAP;
    private static final ResourceKey<VillagerTrade> WOODLAND_MANSION_KEY = VillagerTrades.CARTOGRAPHER_5_EMERALD_AND_COMPASS_WOODLAND_MANSION_MAP;

    private final RegistryAccess access;
    private final List<Component> lore;
    private final boolean replaceMonuments;
    private final boolean replaceMansions;

    public VillagerTradeOverride(final RegistryAccess access, final TreasureMapsPlus plugin) {
        this(access, plugin.getMapUseLore(), plugin.shouldReplaceMonuments(), plugin.shouldReplaceMansions());
    }

    @VisibleForTesting
    VillagerTradeOverride(final RegistryAccess access, final List<Component> lore, final boolean replaceMonuments, final boolean replaceMansions) {
        this.access = access;
        this.lore = lore;
        this.replaceMonuments = replaceMonuments;
        this.replaceMansions = replaceMansions;
    }

    public int override() {
        int changed = 0;
        if (this.replaceMonuments) {
            final VillagerTrade replacement = this.createTradeOverride(
                new TradeCost(Items.EMERALD, 13),
                "filled_map.monument",
                "minecraft:on_ocean_explorer_maps"
            );
            new RegistryOverride<>(Registries.VILLAGER_TRADE, OCEAN_EXPLORER_KEY, replacement).override(this.access);
            changed++;
            LOGGER.info("Replaced ocean explorer map villager trade");
        }
        if (this.replaceMansions) {
            final VillagerTrade replacement = this.createTradeOverride(
                new TradeCost(Items.EMERALD, 14),
                "filled_map.mansion",
                "minecraft:on_woodland_explorer_maps"
            );
            new RegistryOverride<>(Registries.VILLAGER_TRADE, WOODLAND_MANSION_KEY, replacement).override(this.access);
            changed++;
            LOGGER.info("Replaced woodland mansion map villager trade");
        }
        return changed;
    }

    private VillagerTrade createTradeOverride(final TradeCost wants, final String translationKey, final String structureTagLocation) {
        return new VillagerTrade(
            wants,
            Optional.of(new TradeCost(Items.COMPASS, 1)),
            new ItemStackTemplate(Items.MAP),
            12,
            translationKey.equals("filled_map.monument") ? 10 : 30,
            0.2F,
            Optional.empty(),
            this.createGivenItemModifiers(translationKey, structureTagLocation)
        );
    }

    private List<LootItemFunction> createGivenItemModifiers(final String translationKey, final String structureTagLocation) {
        final List<LootItemFunction> modifiers = new ArrayList<>();

        modifiers.add(SetNameFunction.setName(
            net.minecraft.network.chat.Component.translatable(translationKey),
            SetNameFunction.Target.ITEM_NAME
        ).build());

        if (!this.lore.isEmpty()) {
            final SetLoreFunction.Builder loreBuilder = SetLoreFunction.setLore();
            this.lore.stream().map(PaperAdventure::asVanilla).forEach(loreBuilder::addLine);
            modifiers.add(loreBuilder.build());
        }

        final CompoundTag pdcNbt = new CompoundTag();
        final CompoundTag pdc = new CompoundTag();
        pdc.put(TreasureMapsPlus.IS_MAP.asString(), ByteTag.valueOf(true));
        pdc.put(TreasureMapsPlus.MAP_STRUCTURE_TAG_KEY.asString(), StringTag.valueOf(structureTagLocation));
        pdcNbt.put("PublicBukkitValues", pdc);
        modifiers.add(Utils.createSetNbtFunction(pdcNbt).build());

        return modifiers;
    }
}
