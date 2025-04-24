package gay.blackfur.cropsneedwater.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {
    @Shadow public abstract Block getBlock();

    @Inject(method = "is(Lnet/minecraft/world/level/block/Block;)Z", at = @At("RETURN"), cancellable = true)
    public void allFarmLandIsFarmland(Block block, CallbackInfoReturnable<Boolean> cir) {
        if (block == Blocks.FARMLAND && this.getBlock() instanceof FarmBlock) {
            cir.setReturnValue(true);
        }
    }
}
