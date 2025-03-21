package gay.blackfur.cropsneedwater.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import gay.blackfur.cropsneedwater.WaterloggedBlacklist;
import gay.blackfur.cropsneedwater.WaterloggedWhitelist;
import gay.blackfur.cropsneedwater.mixin.configured.WaterloggedMarkerMixin;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IBlockExtension.class)
public interface HoeTillMixin {
    @Redirect(method = "getToolModifiedState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"))
    private boolean allowWaterAboveTilling(BlockState instance) {
        return instance.isAir() || instance.getFluidState().getType() == Fluids.WATER;
    }

    @Redirect(method = "getToolModifiedState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState waterloggedIfUnderwater(Block instance, @Local(argsOnly = true) UseOnContext context) {
        var state = instance.defaultBlockState();
        if (instance instanceof WaterloggedWhitelist && !(this instanceof WaterloggedBlacklist) && context.getLevel().getBlockState(context.getClickedPos().above()).is(Blocks.WATER)) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, true);
        }
        return state;
    }
}
