package gay.blackfur.cropsneedwater.mixin.configured;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "")
@Pseudo
public class WaterloggedBonemealInterfaceMixin {
    @Redirect(method = "performBonemeal", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    public boolean addWaterloggedToBonemeal(ServerLevel instance, BlockPos blockPos, BlockState state, int flags) {
        if (state.hasProperty(BlockStateProperties.WATERLOGGED) && instance.isWaterAt(blockPos)) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, true);
        }
        return instance.setBlock(blockPos, state, flags);
    }
}
