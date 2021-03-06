package com.misha.blocks;

import com.misha.setup.Registration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class ReactorCore extends Block implements EntityBlock{
    public ReactorCore(){
        super(BlockBehaviour.Properties.of(Material.STONE)
                .sound(SoundType.METAL)
                .noOcclusion()
                .lightLevel(state -> state.isAir() ? 13: 13)
                .strength(0.5f));
    }



    boolean built=false;
    boolean oldbuilt=false;
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        list.add(new TranslatableComponent("message.reactorcore").withStyle(ChatFormatting.DARK_GRAY));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos){
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos){
        return 0;
    }



    @OnlyIn(Dist.CLIENT)
    public static void registerRenderLayer(){
        ItemBlockRenderTypes.setRenderLayer(Registration.REACTORCORE.get(), RenderType.translucent());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ReactorCoreBE(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return (level1, pos, state1, tile) -> {
                if (tile instanceof ReactorCoreBE block) {
                    block.tickClient(built, oldbuilt);
                    oldbuilt=built;
                }
            };
        } else {
            return (level1, pos, state1, tile) -> {
                if (tile instanceof ReactorCoreBE block) {
                    block.tickServer(state1);
                    this.built=block.built;
                }
            };
        }
    }

}
