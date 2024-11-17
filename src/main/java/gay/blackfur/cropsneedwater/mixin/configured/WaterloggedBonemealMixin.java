package gay.blackfur.cropsneedwater.mixin.configured;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "")
public class WaterloggedBonemealMixin {
    @Redirect(method = "growCrops", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    public boolean addWaterloggedToBonemeal(Level instance, BlockPos blockPos, BlockState state, int flags) {
        if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, instance.getBlockState(blockPos).getValue(BlockStateProperties.WATERLOGGED));
        }
        return instance.setBlock(blockPos, state, flags);
    }
}
