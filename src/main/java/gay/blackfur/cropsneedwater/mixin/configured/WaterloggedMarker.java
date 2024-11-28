package gay.blackfur.cropsneedwater.mixin.configured;

import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Mixin(targets = "")
@Pseudo
public interface WaterloggedMarker extends SimpleWaterloggedBlock {
}