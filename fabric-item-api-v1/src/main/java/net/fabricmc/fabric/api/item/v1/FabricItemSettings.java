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

package net.fabricmc.fabric.api.item.v1;

import java.util.concurrent.ThreadLocalRandom;

import org.jetbrains.annotations.Nullable;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.fabricmc.fabric.impl.item.FabricItemInternals;
import net.fabricmc.fabric.impl.item.ItemExtensions;

/**
 * Fabric's version of Item.Settings. Adds additional methods and hooks
 * not found in the original class.
 *
 * <p>To use it, simply replace {@code new Item.Settings()} with
 * {@code new FabricItemSettings()}.
 */
public class FabricItemSettings extends Item.Settings {
	/**
	 * Sets the equipment slot provider of the item.
	 *
	 * @param equipmentSlotProvider the equipment slot provider
	 * @return this builder
	 */
	public FabricItemSettings equipmentSlot(EquipmentSlotProvider equipmentSlotProvider) {
		FabricItemInternals.computeExtraData(this).equipmentSlot(equipmentSlotProvider);
		return this;
	}

	/**
	 * Sets the custom damage handler of the item.
	 * Note that this is only called on an ItemStack if {@link ItemStack#isDamageable()} returns true.
	 * @see CustomDamageHandler
	 */
	public FabricItemSettings customDamage(CustomDamageHandler handler) {
		FabricItemInternals.computeExtraData(this).customDamage(handler);
		return this;
	}

	/**
	 * Sets the stack-aware recipe remainder provider of the item.
	 * @see RecipeRemainderProvider
	 */
	public FabricItemSettings recipeRemainder(RecipeRemainderProvider provider) {
		FabricItemInternals.computeExtraData(this).recipeRemainderProvider(provider);
		return this;
	}

	/**
	 * Sets the stack-aware recipe remainder to damage the item by 1 every time it is used in crafting.
	 */
	public FabricItemSettings damageIfUsedInCrafting() {
		return this.damageIfUsedInCrafting(1);
	}

	/**
	 * Sets the stack-aware recipe remainder to damage the item by a certain amount every time it is used in crafting.
	 *
	 * @param by the amount
	 */
	public FabricItemSettings damageIfUsedInCrafting(int by) {
		return this.recipeRemainder((original, inventory, type, world, pos) -> {
			if (!original.isDamageable()) {
				return ItemStack.EMPTY;
			}

			ItemStack copy = original.copy();

			if (!copy.damage(by, ThreadLocalRandom.current(), null)) {
				return ItemStack.EMPTY;
			}

			return copy;
		});
	}

	// Overrides of vanilla methods

	@Override
	public FabricItemSettings food(FoodComponent foodComponent) {
		super.food(foodComponent);
		return this;
	}

	@Override
	public FabricItemSettings maxCount(int maxCount) {
		super.maxCount(maxCount);
		return this;
	}

	@Override
	public FabricItemSettings maxDamageIfAbsent(int maxDamage) {
		super.maxDamageIfAbsent(maxDamage);
		return this;
	}

	@Override
	public FabricItemSettings maxDamage(int maxDamage) {
		super.maxDamage(maxDamage);
		return this;
	}

	@Override
	public FabricItemSettings recipeRemainder(Item recipeRemainder) {
		super.recipeRemainder(recipeRemainder);
		return this;
	}

	@Override
	public FabricItemSettings group(ItemGroup group) {
		super.group(group);
		return this;
	}

	@Override
	public FabricItemSettings rarity(Rarity rarity) {
		super.rarity(rarity);
		return this;
	}

	@Override
	public FabricItemSettings fireproof() {
		super.fireproof();
		return this;
	}
}
