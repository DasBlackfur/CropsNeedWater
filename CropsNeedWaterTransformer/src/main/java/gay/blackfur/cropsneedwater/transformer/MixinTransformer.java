package gay.blackfur.cropsneedwater.transformer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TargetType;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MixinTransformer implements ITransformer<ClassNode> {
    final Map<String, List<String>> mixinTargetConfiguration;

    public MixinTransformer() {
        String configurationString;
        try {
            configurationString = Files.readString(Path.of("config/cropsneedwater.configurable_mixin.json"));
        } catch (IOException e) {
            configurationString = vanillaMixinConfig();
            try {
                Files.writeString(Path.of("config/cropsneedwater.configurable_mixin.json"), configurationString);
            } catch (IOException ignored) {}
        }
        mixinTargetConfiguration = new Gson().fromJson(configurationString, new TypeToken<Map<String, List<String>>>(){}.getType());
    }

    @Override
    public @NotNull ClassNode transform(ClassNode input, ITransformerVotingContext context) {
        String trivialName = input.name.replace("/", ".");
        AnnotationNode mixinAnnotation = input.invisibleAnnotations.getFirst();
        mixinAnnotation.values.set(1, mixinTargetConfiguration.get(trivialName));
        input.invisibleAnnotations.set(0, mixinAnnotation);

        return input;
    }

    @Override
    public @NotNull TransformerVoteResult castVote(ITransformerVotingContext context) {
        return TransformerVoteResult.YES;
    }

    @Override
    public @NotNull Set<Target<ClassNode>> targets() {
        return mixinTargetConfiguration.keySet().stream().map(Target::targetClass).collect(Collectors.toSet());
    }

    @Override
    public @NotNull TargetType<ClassNode> getTargetType() {
        return TargetType.CLASS;
    }

    static String vanillaMixinConfig() {
        return """
                {
                     "gay.blackfur.cropsneedwater.mixin.configured.WaterloggedStateMixin": [
                       "net.minecraft.world.level.block.CropBlock",
                       "net.minecraft.world.level.block.FarmBlock",
                       "net.minecraft.world.level.block.TorchflowerCropBlock",
                       "net.minecraft.world.level.block.BeetrootBlock",
                       "blusunrize.immersiveengineering.common.blocks.plant.HempBlock",
                       "com.buuz135.sushigocrafting.block.plant.CustomCropBlock",
                       "com.phantomwing.rusticdelight.block.custom.CottonCropBlock",
                       "com.phantomwing.rusticdelight.block.custom.BellPepperCropBlock",
                       "com.phantomwing.rusticdelight.block.custom.CoffeeCropBlock",
                       "vectorwing.farmersdelight.common.block.TomatoVineBlock",
                       "vectorwing.farmersdelight.common.block.BuddingBushBlock",
                       "vectorwing.farmersdelight.common.block.RicePaniclesBlock",
                       "net.minecraft.world.level.block.StemBlock",
                       "net.minecraft.world.level.block.AttachedStemBlock",
                       "de.ellpeck.actuallyadditions.mod.blocks.base.AACrops"
                     ],
                     "gay.blackfur.cropsneedwater.mixin.configured.WaterloggedSurvivalMixin": [
                       "net.minecraft.world.level.block.CropBlock",
                       "vectorwing.farmersdelight.common.block.TomatoVineBlock",
                       "vectorwing.farmersdelight.common.block.RiceBlock",
                       "vectorwing.farmersdelight.common.block.BuddingBushBlock",
                       "net.minecraft.world.level.block.BushBlock"
                     ],
                     "gay.blackfur.cropsneedwater.mixin.configured.WaterloggedStemFruitMixin": [
                       "net.minecraft.world.level.block.StemBlock"
                     ],
                     "gay.blackfur.cropsneedwater.mixin.configured.WaterloggedStemResetMixin": [
                       "net.minecraft.world.level.block.AttachedStemBlock"
                     ],
                     "gay.blackfur.cropsneedwater.mixin.configured.WaterloggedSpreadMixin": [
                       "net.minecraft.world.level.block.BushBlock",
                       "net.minecraft.world.level.block.FarmBlock",
                       "net.mehvahdjukaar.supplementaries.common.block.blocks.FlaxBlock",
                       "vectorwing.farmersdelight.common.block.TomatoVineBlock",
                       "net.minecraft.world.level.block.AttachedStemBlock",
                       "vectorwing.farmersdelight.common.block.BuddingTomatoBlock"
                     ],
                     "gay.blackfur.cropsneedwater.mixin.configured.WaterloggedMarkerMixin": [
                       "net.minecraft.world.level.block.CropBlock",
                       "net.minecraft.world.level.block.FarmBlock",
                       "net.minecraft.world.level.block.StemBlock",
                       "net.minecraft.world.level.block.AttachedStemBlock",
                       "vectorwing.farmersdelight.common.block.BuddingBushBlock"
                     ],
                     "gay.blackfur.cropsneedwater.mixin.configured.WaterloggedBlacklistMixin": [
                       "com.buuz135.sushigocrafting.block.plant.WaterCropBlock"
                     ],
                     "gay.blackfur.cropsneedwater.mixin.configured.immersive_engineering.CanGrowTopMixin": [
                       "blusunrize.immersiveengineering.common.blocks.plant.HempBlock"
                     ],
                     "gay.blackfur.cropsneedwater.mixin.configured.farmers_delight.GrowTopMixin": [
                       "vectorwing.farmersdelight.common.block.RiceBlock"
                     ]
                }""";
    }
}
