package gay.blackfur.cropsneedwater.mixin;

import gay.blackfur.cropsneedwater.mixin.configured.WaterloggedBlacklist;
import gay.blackfur.cropsneedwater.mixin.configured.WaterloggedMarker;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {
    @Inject(method = "getFluidState", at = @At("RETURN"), cancellable = true)
    public void getFluidState(BlockState state, CallbackInfoReturnable<FluidState> cir) {
        if (this instanceof WaterloggedMarker && !(this instanceof WaterloggedBlacklist) && state.getValue(BlockStateProperties.WATERLOGGED)) {
            cir.setReturnValue(Fluids.WATER.getSource(false));
        }
    }
}
