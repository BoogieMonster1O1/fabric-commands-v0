/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.impl.biome;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;

import net.fabricmc.fabric.api.biome.v1.BiomeFeatureRegistry;

public class BiomeFeatureRegistryImpl implements BiomeFeatureRegistry {
	public final Set<OreFeatureEntry> ores = Sets.newHashSet();
	public final Set<GenericFeatureEntry<? extends Feature>> features = Sets.newHashSet();

	@Override
	public OreFeature registerOreFeature(OreFeature ore, int size, int minHeight, int maxHeight, Biome... restrictionBiomes) {
		ores.add(new OreFeatureEntry(ore, size, minHeight, maxHeight, Arrays.stream(restrictionBiomes).collect(Collectors.toSet())));
		return ore;
	}

	@Override
	public <T extends Feature> T registerFeature(T feature, Biome... restrictionBiomes) {
		if(feature instanceof OreFeature) {
			LogManager.getLogger().warn("Skipping registering feature {}. Use BiomeFeatureRegistry#registerOreFeature", feature);
		} else {
			features.add(new GenericFeatureEntry<>(feature, Arrays.stream(restrictionBiomes).collect(Collectors.toSet())));
		}
		return feature;
	}
}