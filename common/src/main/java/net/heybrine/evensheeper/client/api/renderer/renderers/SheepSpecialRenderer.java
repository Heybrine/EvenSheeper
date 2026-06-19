package net.heybrine.evensheeper.client.api.renderer.renderers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import com.blackgear.platform.core.BuiltInCoreRegistry;
import com.blackgear.vanillabackport.client.api.renderer.AbstractVariantRenderer;
import net.heybrine.evensheeper.client.level.entities.model.sheep.ColdSheepModel;
import net.heybrine.evensheeper.client.level.entities.model.sheep.WarmSheepModel;
import net.heybrine.evensheeper.client.registries.ModModelLayers;
import net.heybrine.evensheeper.common.level.entities.animal.SheepVariant;
import net.heybrine.evensheeper.common.level.entities.animal.SheepVariants;
import net.heybrine.evensheeper.core.registries.ModBuiltinRegistries;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;

@Environment(EnvType.CLIENT)
public class SheepSpecialRenderer extends AbstractVariantRenderer<Sheep, SheepModel<Sheep>, SheepVariant, SheepVariant.ModelType> {
    public SheepSpecialRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Map<SheepVariant.ModelType, SheepModel<Sheep>> bakeModels(EntityRendererProvider.Context context) {
        Map<SheepVariant.ModelType, SheepModel<Sheep>> map = Maps.newEnumMap(SheepVariant.ModelType.class);
        map.put(SheepVariant.ModelType.NORMAL, null);
        map.put(SheepVariant.ModelType.WARM, new WarmSheepModel<>(context.bakeLayer(ModModelLayers.WARM_SHEEP)));
        map.put(SheepVariant.ModelType.COLD, new ColdSheepModel<>(context.bakeLayer(ModModelLayers.COLD_SHEEP)));
        return map;
    }

    @Override
    protected SheepVariant.ModelType getModelType(SheepVariant variant) {
        return variant.modelAndTexture().model();
    }

    @Override
    protected ResourceLocation getTexture(SheepVariant variant) {
        return variant.modelAndTexture().asset().path();
    }

    @Override
    protected BuiltInCoreRegistry<SheepVariant> getRegistry() {
        return ModBuiltinRegistries.SHEEP_VARIANTS;
    }

    @Override
    protected ResourceKey<SheepVariant> getDefaultVariant() {
        return SheepVariants.TEMPERATE;
    }
}

