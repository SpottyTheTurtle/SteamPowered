/*
 * Copyright (c) 2021 TeamMoeg
 *
 * This file is part of Steam Powered.
 *
 * Steam Powered is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Steam Powered is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Steam Powered. If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.steampowered.client;

import com.teammoeg.steampowered.FluidRegistry;
import com.teammoeg.steampowered.block.SPBlockPartials;
import com.teammoeg.steampowered.ponder.SPPonderIndex;
import com.teammoeg.steampowered.registrate.SPBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class SteamPoweredClient {
    public static void addClientListeners(IEventBus forgeEventBus, IEventBus modEventBus) {
        SPBlockPartials.clientInit();
        modEventBus.addListener(SteamPoweredClient::clientInit);
        modEventBus.addListener(SteamPoweredClient::setupRenderType);
        modEventBus.addListener(SteamPoweredClient::registerParticleFactories);
    }

    public static void clientInit(FMLClientSetupEvent event) {
        SPPonderIndex.register();
    }
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(Particles.STEAM.get(), SteamParticle.Factory::new);
    }
    public static void setupRenderType(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(FluidRegistry.steam.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FluidRegistry.steamFlowing.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(SPBlocks.DYNAMO.get(), RenderType.cutoutMipped());
        });
    }
}
