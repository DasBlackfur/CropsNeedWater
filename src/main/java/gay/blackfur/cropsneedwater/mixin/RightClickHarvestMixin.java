package gay.blackfur.cropsneedwater.mixin;

import com.blakebr0.pickletweaks.feature.FeatureRightClickHarvest;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FeatureRightClickHarvest.class)
@Pseudo
public class RightClickHarvestMixin {
    @Redirect(method = "onRightClickCrop", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CropBlock;getStateForAge(I)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState addWaterToState(CropBlock instance, int age, @Local BlockState state) {
        var init_state = instance.getStateForAge(age);
        if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
            init_state = init_state.setValue(BlockStateProperties.WATERLOGGED, true);
        }
        return init_state;
    }
}
