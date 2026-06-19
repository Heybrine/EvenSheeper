package net.heybrine.evensheeper.common.level.entities.animal;

import com.blackgear.platform.core.BuiltInCoreRegistry;
import com.blackgear.vanillabackport.common.api.variant.ModelAndTexture;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnPrioritySelectors;
import com.blackgear.vanillabackport.common.api.variant.spawn.check.raw.RawBiomeCheck;
import com.blackgear.vanillabackport.common.api.variant.ClientAsset;
import com.blackgear.vanillabackport.core.data.tags.ModBiomeTags;
import net.heybrine.evensheeper.core.registries.ModBuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class SheepVariants {
    public static final BuiltInCoreRegistry<SheepVariant> REGISTRY = ModBuiltinRegistries.SHEEP_VARIANTS;

    public static final ResourceKey<SheepVariant> TEMPERATE = register(
            "temperate",
            SheepVariant.ModelType.NORMAL,
            "sheep",
            "sheep_wool_undercoat",
            "sheep_fur",
            SpawnPrioritySelectors.fallback(0)
    );
    public static final ResourceKey<SheepVariant> WARM = register(
            "warm",
            SheepVariant.ModelType.WARM,
            "warm_sheep",
            "warm_sheep_undercoat",
            "warm_sheep_wool",
            ModBiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS
    );
    public static final ResourceKey<SheepVariant> COLD = register(
            "cold",
            SheepVariant.ModelType.COLD,
            "cold_sheep",
            "cold_sheep_undercoat",
            "cold_sheep_wool",
            ModBiomeTags.SPAWNS_COLD_VARIANT_FARM_ANIMALS
    );

    private static ResourceKey<SheepVariant> register(String key, SheepVariant.ModelType type, String adultAssetId, String underWoolTextureId, String woolTextureId, TagKey<Biome> biome) {
        return register(key, type, adultAssetId, underWoolTextureId, woolTextureId, SpawnPrioritySelectors.single(new RawBiomeCheck(biome), 1));
    }

    private static ResourceKey<SheepVariant> register(String key, SheepVariant.ModelType type, String adultAssetId, String underWoolTextureId, String woolTextureId, SpawnPrioritySelectors selectors) {
        ResourceLocation adultTexture = new ResourceLocation("entity/sheep/" + adultAssetId);
        ResourceLocation underWoolTexture = new ResourceLocation("entity/sheep/" + underWoolTextureId);
        ResourceLocation woolTexture = new ResourceLocation("entity/sheep/" + woolTextureId);
        return REGISTRY.resource(key, new SheepVariant(new ModelAndTexture<>(type, adultTexture), new ClientAsset(underWoolTexture), new ClientAsset(woolTexture), selectors));
    }
}