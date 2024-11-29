package gay.blackfur.cropsneedwater.mixin;

import gay.blackfur.cropsneedwater.mixin.configured.WaterloggedBlacklist;
import gay.blackfur.cropsneedwater.mixin.configured.WaterloggedMarker;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    public void addWaterloggedToPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (this instanceof WaterloggedMarker && !(this instanceof WaterloggedBlacklist) && cir.getReturnValue().hasProperty(BlockStateProperties.WATERLOGGED) && context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER) {
            cir.setReturnValue(cir.getReturnValue().setValue(BlockStateProperties.WATERLOGGED, true));
        }
    }

    @Redirect(method = "registerDefaultState", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState:Lnet/minecraft/world/level/block/state/BlockState;"))
    public void addWaterloggedToDefaultState(Block instance, BlockState state) {
        if (this instanceof WaterloggedMarker && !(this instanceof WaterloggedBlacklist) && state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false));
        }
        instance.defaultBlockState = state;
    }
}
