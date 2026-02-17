package gay.blackfur.cropsneedwater.mixin.configured;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "")
@Pseudo
public class WaterloggedStemFruitMixin {
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;isEmptyBlock(Lnet/minecraft/core/BlockPos;)Z"))
    public boolean allowWaterAsEmpty(ServerLevel instance, BlockPos blockPos) {
        return instance.getBlockState(blockPos).is(Blocks.WATER) || instance.isEmptyBlock(blockPos);
    }
}
