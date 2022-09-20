package ru.mcmodding.tutorial.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import ru.mcmodding.tutorial.McModding;
import ru.mcmodding.tutorial.common.handler.*;
import ru.mcmodding.tutorial.common.handler.packet.ServerMessagePacket;
import ru.mcmodding.tutorial.common.handler.recipe.WhetstoneRecipe;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        McModding.NETWORK.registerMessage(new ServerMessagePacket(), ServerMessagePacket.class, 0, Side.SERVER);

        FMLCommonHandler.instance().bus().register(new FMLEventListener());
        MinecraftForge.EVENT_BUS.register(new ForgeEventListener());

        ModBlocks.register();
        ModItems.register();
        ModEnchantments.register();

        GameRegistry.registerFuelHandler(new FuelHandler());

        MinecraftForge.EVENT_BUS.register(new BucketHandler());
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.RUBY_SWORD), " R ", " R ", " S ", 'R', ModItems.RUBY, 'S', Items.stick);

        for (int damage = 0; damage < 15; damage++) {
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.BALLOON, 1, damage), new ItemStack(Blocks.wool, 1, ~damage & 15), Items.string);
        }

        GameRegistry.addSmelting(ModBlocks.RUBY_ORE, new ItemStack(ModItems.RUBY), 5F);

        GameRegistry.addRecipe(new WhetstoneRecipe());
    }
}