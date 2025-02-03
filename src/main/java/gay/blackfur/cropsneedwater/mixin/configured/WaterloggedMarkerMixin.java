package gay.blackfur.cropsneedwater.mixin.configured;

import gay.blackfur.cropsneedwater.WaterloggedWhitelist;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Mixin(targets = "")
@Pseudo
public interface WaterloggedMarkerMixin extends SimpleWaterloggedBlock, WaterloggedWhitelist {
}