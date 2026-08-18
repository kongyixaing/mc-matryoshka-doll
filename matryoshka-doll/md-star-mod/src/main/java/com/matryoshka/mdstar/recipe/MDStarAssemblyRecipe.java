package com.matryoshka.mdstar.recipe;

import com.google.gson.JsonObject;
import com.matryoshka.mdstar.MDStarMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

/**
 * MD 组装塔专用配方类型。
 * 数据驱动：data/mdstar/recipes/*.json
 *
 * JSON 格式：
 * {
 *   "type": "mdstar:md_assembly",
 *   "ingredients": [ {"item": "mdstar:create_essence"}, ... 8 个 ... ],
 *   "result": {"item": "mdstar:md_heart", "count": 1},
 *   "energy": 1000,
 *   "process_time": 4000
 * }
 */
public class MDStarAssemblyRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int energy;
    private final int processTime;

    public MDStarAssemblyRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients,
                                 ItemStack result, int energy, int processTime) {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
        this.energy = energy;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(Container inv, Level level) {
        if (level == null) return false;
        if (ingredients.isEmpty()) return false;
        // 8 个输入槽要全部匹配
        if (inv.getContainerSize() < ingredients.size()) return false;
        for (int i = 0; i < ingredients.size(); i++) {
            if (!ingredients.get(i).test(inv.getItem(i))) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess access) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getEnergy() { return energy; }
    public int getProcessTime() { return processTime; }

    // ---------------------------------------------------------------
    // RecipeType
    // ---------------------------------------------------------------
    public static class Type implements RecipeType<MDStarAssemblyRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
    }

    // ---------------------------------------------------------------
    // Serializer
    // ---------------------------------------------------------------
    public static class Serializer implements RecipeSerializer<MDStarAssemblyRecipe> {

        public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
                DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MDStarMod.MOD_ID);

        public static final RegistryObject<RecipeSerializer<MDStarAssemblyRecipe>> SERIALIZER =
                SERIALIZERS.register("md_assembly", Serializer::new);

        @Override
        public MDStarAssemblyRecipe fromJson(ResourceLocation id, JsonObject json) {
            com.google.gson.JsonArray ingArrayJson =
                    net.minecraft.util.GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (com.google.gson.JsonElement e : ingArrayJson) {
                ingredients.add(Ingredient.fromJson(e));
            }
            JsonObject resultJson = net.minecraft.util.GsonHelper.getAsJsonObject(json, "result");
            ItemStack result = CraftingHelper.getItemStack(resultJson, true);
            int energy = net.minecraft.util.GsonHelper.getAsInt(json, "energy", 1000);
            int processTime = net.minecraft.util.GsonHelper.getAsInt(json, "process_time", 4000);
            return new MDStarAssemblyRecipe(id, ingredients, result, energy, processTime);
        }

        @Override
        public MDStarAssemblyRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int size = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int i = 0; i < size; i++) ingredients.add(Ingredient.fromNetwork(buf));
            ItemStack result = buf.readItem();
            int energy = buf.readVarInt();
            int processTime = buf.readVarInt();
            return new MDStarAssemblyRecipe(id, ingredients, result, energy, processTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MDStarAssemblyRecipe recipe) {
            buf.writeVarInt(recipe.ingredients.size());
            for (Ingredient ing : recipe.ingredients) ing.toNetwork(buf);
            buf.writeItem(recipe.result);
            buf.writeVarInt(recipe.energy);
            buf.writeVarInt(recipe.processTime);
        }
    }
}
