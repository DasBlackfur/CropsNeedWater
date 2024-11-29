package gay.blackfur.cropsneedwater.mixin.configured;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "")
@Pseudo
public class WaterloggedBonemealWithUpdateMixin {
    @Redirect(method = "growCrops", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    public boolean addWaterloggedToBonemealWithUpdate(Level instance, BlockPos blockPos, BlockState state) {
        if (state.hasProperty(BlockStateProperties.WATERLOGGED) && instance.isWaterAt(blockPos)) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, true);
        }
        return instance.setBlock(blockPos, state, 3);
    }
}
