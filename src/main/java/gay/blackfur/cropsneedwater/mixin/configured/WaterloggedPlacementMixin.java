package gay.blackfur.cropsneedwater.mixin.configured;

import gay.blackfur.cropsneedwater.WaterloggedBlacklist;
import gay.blackfur.cropsneedwater.WaterloggedWhitelist;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "")
@Pseudo
public class WaterloggedPlacementMixin {
    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    public void addWaterloggedToPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (this instanceof WaterloggedWhitelist && !(this instanceof WaterloggedBlacklist) && cir.getReturnValue().hasProperty(BlockStateProperties.WATERLOGGED) && context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER) {
            cir.setReturnValue(cir.getReturnValue().setValue(BlockStateProperties.WATERLOGGED, true));
        }
    }
}
