package gay.blackfur.cropsneedwater.mixin.configured;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Mixin(targets = "")
@Pseudo
public interface WaterloggedBlacklistMixin extends gay.blackfur.cropsneedwater.WaterloggedBlacklist {
}
