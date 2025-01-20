package gay.blackfur.cropsneedwater.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.ftb.mods.ftbultimine.RightClickHandlers;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RightClickHandlers.class)
@Pseudo
public class UltimineMixin {
    @Redirect(method = "resetAge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CropBlock;getStateForAge(I)Lnet/minecraft/world/level/block/state/BlockState;"))
    private static BlockState addWaterToState(CropBlock instance, int age, @Local(argsOnly = true) BlockState currentState) {
        BlockState state = instance.getStateForAge(age);
        if (state.hasProperty(BlockStateProperties.WATERLOGGED) && currentState.hasProperty(BlockStateProperties.WATERLOGGED) && currentState.getValue(BlockStateProperties.WATERLOGGED)) {
            return state.setValue(BlockStateProperties.WATERLOGGED, true);
        }
        return state;
    }
}
