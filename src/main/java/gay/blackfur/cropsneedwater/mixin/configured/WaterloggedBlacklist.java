package gay.blackfur.cropsneedwater.mixin.configured;

import gay.blackfur.cropsneedwater.DummyInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Mixin(targets = "")
@Pseudo
public interface WaterloggedBlacklist extends DummyInterface {
}
