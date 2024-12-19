package gay.blackfur.cropsneedwater.mixin.configured.farmers_delight;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "")
@Pseudo
public class GrowTopMixin {
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;isEmptyBlock(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean isEmptyAllowsWater(ServerLevel instance, BlockPos blockPos) {
        if (instance.isWaterAt(blockPos)) {
            return !instance.getBlockState(blockPos).is(ResourceKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.parse("farmersdelight:rice_panicles")));
        } else {
            return false;
        }
    }
}
