package gay.blackfur.cropsneedwater.mixin.configured;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "")
@Pseudo
public class CanGrowIntoMixin {
    @Redirect(method = "canGrowInto", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"))
    private static boolean allowWaterAsAir(BlockState instance, @Local(argsOnly = true) LevelReader level, @Local(argsOnly = true) BlockPos pos) {
        return level.isWaterAt(pos);
    }
}
