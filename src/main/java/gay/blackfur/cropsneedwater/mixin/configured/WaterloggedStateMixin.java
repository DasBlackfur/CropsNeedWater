package gay.blackfur.cropsneedwater.mixin.configured;

import gay.blackfur.cropsneedwater.WaterloggedBlacklist;
import gay.blackfur.cropsneedwater.WaterloggedWhitelist;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "")
@Pseudo
public class WaterloggedStateMixin {
    @Inject(method = "createBlockStateDefinition", at = @At("HEAD"))
    public void addWaterloggedBlockstate(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        if (this instanceof WaterloggedWhitelist && !(this instanceof WaterloggedBlacklist)) {
            builder.add(BlockStateProperties.WATERLOGGED);
        }
    }
}
