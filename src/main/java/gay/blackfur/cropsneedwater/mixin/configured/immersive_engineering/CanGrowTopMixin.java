package gay.blackfur.cropsneedwater.mixin.configured.immersive_engineering;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "")
@Pseudo
public class CanGrowTopMixin {
    @Final
    @Shadow
    public static EnumProperty<DoubleBlockHalf> HALF;
    @Inject(method = "canGrowTop", at = @At("HEAD"), cancellable = true)
    public void addWaterCheck(LevelReader world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER && world.isWaterAt(pos.above())) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
    }
}
