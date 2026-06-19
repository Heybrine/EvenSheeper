package net.heybrine.evensheeper.common.level.entities.animal;

import com.blackgear.vanillabackport.common.api.variant.*;
import com.blackgear.vanillabackport.common.api.variant.spawn.PriorityProvider;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnCondition;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnContext;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnPrioritySelectors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.util.StringRepresentable;

public record SheepVariant(
        ModelAndTexture<ModelType> modelAndTexture,
        ClientAsset undercoat,
        ClientAsset wool,
        SpawnPrioritySelectors spawnConditions) implements PriorityProvider<SpawnContext, SpawnCondition> {

    public static final Codec<SheepVariant> CODEC =
            RecordCodecBuilder.create((instance) -> instance.group(
                            ModelAndTexture.codec(
                                            SheepVariant.ModelType.CODEC,
                                            SheepVariant.ModelType.NORMAL)
                                    .forGetter(SheepVariant::modelAndTexture),

                            ClientAsset.DEFAULT_FIELD_CODEC
                                    .fieldOf("undercoat")
                                    .forGetter(SheepVariant::undercoat),

                            ClientAsset.DEFAULT_FIELD_CODEC
                                    .fieldOf("wool")
                                    .forGetter(SheepVariant::wool),

                            SpawnPrioritySelectors.CODEC
                                    .fieldOf("spawn_conditions")
                                    .forGetter(SheepVariant::spawnConditions))
                    .apply(instance, SheepVariant::new));

    public List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }

    public static enum ModelType implements StringRepresentable {
        NORMAL("normal"),
        //COLD("cold"),
        WARM("warm");

        public static final Codec<ModelType> CODEC = StringRepresentable.fromEnum(ModelType::values);
        private final String name;

        private ModelType(String name) {
            this.name = name;
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}