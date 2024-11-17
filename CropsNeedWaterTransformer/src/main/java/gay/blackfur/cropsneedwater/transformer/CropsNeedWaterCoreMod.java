package gay.blackfur.cropsneedwater.transformer;

import cpw.mods.modlauncher.api.ITransformer;
import net.neoforged.neoforgespi.coremod.ICoreMod;

import java.util.List;

public class CropsNeedWaterCoreMod implements ICoreMod {
    public CropsNeedWaterCoreMod() {
    }

    @Override
    public Iterable<? extends ITransformer<?>> getTransformers() {
        return List.of(new MixinTransformer());
    }
}
