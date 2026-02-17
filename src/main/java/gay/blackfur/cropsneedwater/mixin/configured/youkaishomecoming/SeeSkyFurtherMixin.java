package gay.blackfur.cropsneedwater.mixin.configured.youkaishomecoming;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "")
@Pseudo
public class SeeSkyFurtherMixin {
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;canSeeSky(Lnet/minecraft/core/BlockPos;)Z"))
    public boolean allowWaterAsSky(ServerLevel instance, BlockPos blockPos) {
        return instance.getBrightness(LightLayer.SKY, blockPos) >= instance.getMaxLightLevel() -1;
    }
}
