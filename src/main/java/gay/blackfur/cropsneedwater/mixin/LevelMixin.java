package gay.blackfur.cropsneedwater.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import gay.blackfur.cropsneedwater.WaterloggedBlacklist;
import gay.blackfur.cropsneedwater.WaterloggedWhitelist;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
@Debug(export = true)
public abstract class LevelMixin {

    @Shadow public abstract FluidState getFluidState(BlockPos pos);

    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z", at = @At(value = "HEAD"))
    public void addWaterloggingToState(BlockPos pos, BlockState state, int flags, int recursionLeft, CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) LocalRef<BlockState> stateMutable) {
        Block block = state.getBlock();
        if (
                block instanceof WaterloggedWhitelist &&
                !(block instanceof WaterloggedBlacklist) &&
                state.hasProperty(BlockStateProperties.WATERLOGGED) &&
                this.getFluidState(pos).getType() == Fluids.WATER
        ) {
            stateMutable.set(state.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE));
        }
    }
}
