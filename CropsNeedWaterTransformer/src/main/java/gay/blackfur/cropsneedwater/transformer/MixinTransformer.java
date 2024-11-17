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
        return "{\n" +
                "  \"gay.blackfur.cropsneedwater.mixin.configured.WaterloggedStateMixin\": [\n" +
                "    \"net.minecraft.world.level.block.CropBlock\",\n" +
                "    \"net.minecraft.world.level.block.FarmBlock\",\n" +
                "    \"net.minecraft.world.level.block.TorchflowerCropBlock\",\n" +
                "    \"net.minecraft.world.level.block.BeetrootBlock\"\n" +
                "  ],\n" +
                "  \"gay.blackfur.cropsneedwater.mixin.configured.WaterloggedBonemealMixin\": [\n" +
                "    \"net.minecraft.world.level.block.CropBlock\"\n" +
                "  ],\n" +
                "  \"gay.blackfur.cropsneedwater.mixin.configured.WaterloggedTickMixin\": [\n" +
                "    \"net.minecraft.world.level.block.CropBlock\"\n" +
                "  ],\n" +
                "  \"gay.blackfur.cropsneedwater.mixin.configured.WaterloggedSurvivalMixin\": [\n" +
                "    \"net.minecraft.world.level.block.CropBlock\"\n" +
                "  ],\n" +
                "  \"gay.blackfur.cropsneedwater.mixin.configured.WaterloggedMarker\": [\n" +
                "    \"net.minecraft.world.level.block.CropBlock\",\n" +
                "    \"net.minecraft.world.level.block.FarmBlock\"\n" +
                "  ],\n" +
                "  \"gay.blackfur.cropsneedwater.mixin.configured.WaterloggedSpreadMixin\": [\n" +
                "    \"net.minecraft.world.level.block.BushBlock\",\n" +
                "    \"net.minecraft.world.level.block.FarmBlock\"\n" +
                "  ]\n" +
                "}";
    }
}
