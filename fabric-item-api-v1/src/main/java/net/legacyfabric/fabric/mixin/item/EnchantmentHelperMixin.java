/*
 * Copyright (c) 2016-2021 FabricMC
 * Copyright (c) 2020-2021 Legacy Fabric
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

package net.legacyfabric.fabric.mixin.item;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.legacyfabric.fabric.api.item.v1.Enchantable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
	@Redirect(method = "calculateEnchantmentPower", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/Item;getEnchantability()I"))
	private static int redirectCalculateEnchantmentPower(Item item, Random random, int num, int enchantmentPower, ItemStack stack) {
		return Enchantable.enchantabilityOf(stack);
	}

	@Redirect(method = "getEnchantments", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/Item;getEnchantability()I"))
	private static int redirectGetEnchantments(Item item, Random random, ItemStack stack, int level, boolean hasTreasure) {
		return Enchantable.enchantabilityOf(stack);
	}
}