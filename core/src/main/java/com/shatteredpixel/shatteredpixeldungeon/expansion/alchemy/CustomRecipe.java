package com.shatteredpixel.shatteredpixeldungeon.expansion.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

//copy a lot from recipe.
//Avoid conflicts with existing recipe.
//todo: MIMO
public abstract class CustomRecipe {
    //judge if validate`
    public abstract boolean testIngredients(ArrayList<Item> ingredients);
    //energy cost
    public abstract int cost(ArrayList<Item> ingredients);
    //generate result
    public abstract Item brew(ArrayList<Item> ingredients);
    //preview
    public abstract Item sampleOutput(ArrayList<Item> ingredients);

    public static class FixedRecipe extends CustomRecipe{
        //NO duplications!!!
        protected Class<? extends Item> inputs[];
        protected int[] inQuantity;
        protected Class<? extends Item> output;
        protected int outQuantity;
        protected int cost;

        @Override
        public final boolean testIngredients(ArrayList<Item> ingredients) {
            int[] needed = inQuantity.clone();
            int all=0;
            for(int i:needed){
                all += i;
            }
            //size not equal, false;
            if(ingredients.size()!=all) return false;

            //cache Class to avoid duplicate reflections.
            ArrayList<Class<? extends Item>> itemClazz = new ArrayList<>(ingredients.size());
            for(Item ingredient: ingredients){
                itemClazz.add(ingredient.getClass());
            }
            //if recipe contains it, reduce 1 count
            for (int it=0;it<ingredients.size();++it){
                for (int i = 0; i < inputs.length; i++){
                    if (itemClazz.get(it) == inputs[i]){
                        needed[i] -= ingredients.get(it).quantity();
                        break;
                    }
                }
            }
            //if all ingredients are right,
            for (int i : needed){
                if (i > 0){
                    return false;
                }
            }

            return true;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return cost;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {
            if (!testIngredients(ingredients)) return null;

            int[] needed = inQuantity.clone();

            for (Item ingredient : ingredients){
                for (int i = 0; i < inputs.length; i++) {
                    if (ingredient.getClass() == inputs[i] && needed[i] > 0) {
                        if (needed[i] <= ingredient.quantity()) {
                            ingredient.quantity(ingredient.quantity() - needed[i]);
                            needed[i] = 0;
                        } else {
                            needed[i] -= ingredient.quantity();
                            ingredient.quantity(0);
                        }
                    }
                }
            }

            //sample output and real output are identical in this case.
            return sampleOutput(null);
        }

        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) {
            Item result;
            try {
                result = Reflection.newInstance(output);
                result.quantity(outQuantity);
                return result;
            } catch (Exception e) {
                ShatteredPixelDungeon.reportException( e );
                return null;
            }
        }

    }

}