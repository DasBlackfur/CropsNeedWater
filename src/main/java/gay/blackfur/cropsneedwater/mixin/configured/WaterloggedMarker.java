package gay.blackfur.cropsneedwater.mixin.configured;

import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "")
public interface WaterloggedMarker extends SimpleWaterloggedBlock {
}