/*
 * Copyright (c) 2016, 2017, 2018 FabricMC
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

package net.fabricmc.fabric.particles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.particles.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particles.ParticleTypeRegistry;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ParticleModClient implements ClientModInitializer {

	public static final DefaultParticleType SIMPLE_TEST_PARTICLE = ParticleTypeRegistry.getTnstance().register(new Identifier("testmod", "simple"));
	public static final DefaultParticleType CUSTOM_TEST_PARTICLE = ParticleTypeRegistry.getTnstance().register(new Identifier("testmod", "custom"));

	@Override
	public void onInitializeClient() {
		ParticleFactoryRegistry.getInstance().register(SIMPLE_TEST_PARTICLE, SimpleTestParticle::new);
		ParticleFactoryRegistry.getInstance().register(CUSTOM_TEST_PARTICLE, CustomTestParticle.Factory::new);
	}

	@Environment(EnvType.CLIENT)
	static class SimpleTestParticle extends SpriteBillboardParticle {

		public SimpleTestParticle(ParticleEffect effect, World world, double x, double y, double z, double velX, double velY, double velZ) {
			super(world, x, y, z, velX, velY, velZ);
		}

		@Override
		public ParticleTextureSheet getType() {
			return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
		}
	}

	@Environment(EnvType.CLIENT)
	static class CustomTestParticle extends AnimatedParticle {

		protected CustomTestParticle(World world, double x, double y, double z, SpriteProvider sprites) {
			super(world, x, y, z, sprites, 1);
		}

		@Environment(EnvType.CLIENT)
		public static class Factory implements ParticleFactory<DefaultParticleType> {

			private final SpriteProvider sprites;

			public Factory(SpriteProvider sprites) {
				this.sprites = sprites;
			}

			@Override
			public Particle createParticle(DefaultParticleType type, World world, double x, double y, double z, double vX, double vY, double vZ) {
			   return new CustomTestParticle(world, x, y, z, sprites);
			}
		}
	}
}
