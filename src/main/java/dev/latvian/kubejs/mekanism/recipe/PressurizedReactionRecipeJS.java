package dev.latvian.kubejs.mekanism.recipe;

import dev.latvian.mods.kubejs.recipe.IngredientMatch;
import dev.latvian.mods.kubejs.recipe.ItemInputTransformer;
import dev.latvian.mods.kubejs.recipe.ItemOutputTransformer;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import mekanism.api.JsonConstants;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * @author LatvianModder
 */
public class PressurizedReactionRecipeJS extends MekanismRecipeJS {
	public ItemStack itemOutput = ItemStack.EMPTY;
	public ItemStackIngredient itemInput;

	@Override
	public void create(RecipeArguments args) {
		throw new RecipeExceptionJS("Creation not supported yet!");
	}

	@Override
	public void deserialize() {
		if (json.has(JsonConstants.ITEM_OUTPUT)) {
			itemOutput = parseItemOutput(json.get(JsonConstants.ITEM_OUTPUT));
		}

		itemInput = mekStackIngredient(json.get(JsonConstants.ITEM_INPUT));
	}

	@Override
	public void serialize() {
		if (serializeInputs) {
			json.add(JsonConstants.ITEM_INPUT, itemInput.serialize());
		}

		if (serializeOutputs) {
			if (itemOutput != ItemStack.EMPTY) {
				json.add(JsonConstants.ITEM_OUTPUT, itemToJson(itemOutput));
			}
		}
	}

	@Override
	public boolean hasInput(IngredientMatch match) {
		return mekMatchStackIngredient(itemInput, match);
	}

	@Override
	public boolean replaceInput(IngredientMatch match, Ingredient with, ItemInputTransformer transformer) {
		var newIn = mekReplaceStackIngredient(itemInput, match, with, transformer);

		if (itemInput != newIn) {
			itemInput = newIn;
			return true;
		}

		return false;
	}

	@Override
	public boolean hasOutput(IngredientMatch match) {
		return match.contains(itemOutput);
	}

	@Override
	public boolean replaceOutput(IngredientMatch match, ItemStack with, ItemOutputTransformer transformer) {
		if (match.contains(itemOutput)) {
			itemOutput = transformer.transform(this, match, itemOutput, with);
			return true;
		}

		return false;
	}
}