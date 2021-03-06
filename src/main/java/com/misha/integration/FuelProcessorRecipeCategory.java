package com.misha.integration;

import com.misha.lavaplus.LavaPlus;
import com.misha.recipes.FuelProcessorRecipe;
import com.misha.setup.Registration;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class FuelProcessorRecipeCategory implements IRecipeCategory<FuelProcessorRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(LavaPlus.MODID, "fuelprocessing");
    public final static ResourceLocation TEXTURE = new ResourceLocation(LavaPlus.MODID, "textures/gui/fuelprocessor_gui.png");

    private final IDrawable background;
    private final IDrawable icon;


    public FuelProcessorRecipeCategory(IGuiHelper helper){
        this.background = helper.createDrawable(TEXTURE, 5, 15,161,60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(Registration.FUELPROCESSOR.get()));
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Fuel Processing");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends FuelProcessorRecipe> getRecipeClass() {
        return FuelProcessorRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FuelProcessorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 28, 23).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 23).addItemStack(recipe.getResultItem());



    }
}
