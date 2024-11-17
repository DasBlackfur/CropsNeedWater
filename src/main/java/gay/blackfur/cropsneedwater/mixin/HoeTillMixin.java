package gay.blackfur.cropsneedwater.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IBlockExtension.class)
public interface HoeTillMixin {
    @Redirect(method = "getToolModifiedState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"))
    private boolean allowWaterAboveTilling(BlockState instance) {
        return instance.isAir() || instance.is(Blocks.WATER);
    }
}
