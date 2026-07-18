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

import java.util.Collections;
import net.minecraft.SharedConstants;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.trading.VillagerTrades;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VillagerTradeOverrideTest {

    @BeforeAll
    static void init() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    void testVillagerTradeKeysExist() {
        assertNotNull(VillagerTrades.CARTOGRAPHER_3_EMERALD_AND_COMPASS_OCEAN_EXPLORER_MAP);
        assertNotNull(VillagerTrades.CARTOGRAPHER_5_EMERALD_AND_COMPASS_WOODLAND_MANSION_MAP);
    }

    @Test
    void testVillagerOverrideConstruction() {
        final RegistryAccess access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        final VillagerTradeOverride villagerTradeOverride = new VillagerTradeOverride(access, Collections.emptyList(), true, true);
        assertNotNull(villagerTradeOverride);
    }
}
