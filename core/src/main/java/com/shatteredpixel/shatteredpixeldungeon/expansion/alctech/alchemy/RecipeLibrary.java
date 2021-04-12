package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion.ExpPotionEX;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;

import java.util.ArrayList;

public class RecipeLibrary {
    private static CustomRecipe[] oneIngredientRecipes = new CustomRecipe[]{

    };

    private static CustomRecipe[] twoIngredientRecipes = new CustomRecipe[]{

    };

    private static CustomRecipe[] threeIngredientRecipes = new CustomRecipe[]{
            new ExpPotionEX.ExpP3Recipe(),
            new ExpPotionEX.ExpN3Recipe()
    };

    public static CustomRecipe findRecipe(ArrayList<Item> ingredients){

        if (ingredients.size() == 1){
            for (CustomRecipe recipe : oneIngredientRecipes){
                if (recipe.testIngredients(ingredients)){
                    return recipe;
                }
            }

        } else if (ingredients.size() == 2){
            for (CustomRecipe recipe : twoIngredientRecipes){
                if (recipe.testIngredients(ingredients)){
                    return recipe;
                }
            }

        } else if (ingredients.size() == 3){
            for (CustomRecipe recipe : threeIngredientRecipes){
                if (recipe.testIngredients(ingredients)){
                    return recipe;
                }
            }
        }

        return null;
    }
}
