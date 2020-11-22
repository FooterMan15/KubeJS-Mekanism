package dev.latvian.kubejs.mekanism;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.kubejs.item.ingredient.IngredientJS;
import dev.latvian.kubejs.item.ingredient.IngredientStackJS;
import dev.latvian.kubejs.recipe.RecipeJS;
import dev.latvian.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class MekanismItemToItemRecipeJS extends RecipeJS
{
	public final String inputName;
	public final String outputName;

	public MekanismItemToItemRecipeJS(String in, String out)
	{
		inputName = in;
		outputName = out;
	}

	public MekanismItemToItemRecipeJS()
	{
		this("input", "output");
	}

	@Override
	public void create(ListJS args)
	{
		outputItems.add(parseResultItem(args.get(0)));
		inputItems.addAll(parseIngredientItemStackList(args.get(1)));
	}

	@Override
	public void deserialize()
	{
		outputItems.add(parseResultItem(json.get(outputName)));
		inputItems.addAll(parseIngredientItemStackList(json.get(inputName)));
	}

	@Override
	public void serialize()
	{
		if (serializeInputs)
		{
			if (inputItems.size() == 1)
			{
				json.add(inputName, inputItems.get(0).toJson());
			}
			else
			{
				JsonArray inputArray = new JsonArray();

				for (IngredientJS i : inputItems)
				{
					inputArray.add(i.toJson());
				}

				json.add(inputName, inputArray);
			}
		}

		if (serializeOutputs)
		{
			json.add(outputName, outputItems.get(0).toResultJson());
		}
	}

	@Override
	public JsonElement serializeIngredientStack(IngredientStackJS in)
	{
		JsonObject json = new JsonObject();
		json.add("ingredient", in.ingredient.toJson());

		if (in.getCount() > 1)
		{
			json.addProperty("amount", in.getCount());
		}

		return json;
	}
}