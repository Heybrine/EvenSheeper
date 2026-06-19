package net.heybrine.evensheeper.core.registries;

import com.blackgear.platform.core.RegistryBuilder;
import com.blackgear.platform.core.api.registrar.RegistrarBuilder;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnCondition;
import net.heybrine.evensheeper.common.level.entities.animal.SheepVariant;
import net.heybrine.evensheeper.core.EvenSheeper;
import com.blackgear.vanillabackport.core.VanillaBackport;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class ModRegistries {
    public static final RegistryBuilder BUILDER = RegistryBuilder.create(EvenSheeper.NAMESPACE);
    public static final RegistryBuilder INTERNAL = RegistryBuilder.create(EvenSheeper.MOD_ID);

    public static final ResourceKey<Registry<SheepVariant>> SHEEP_VARIANT_KEY = BUILDER.resource("sheep_variant");
    public static final Supplier<Registry<SheepVariant>> SHEEP_VARIANT = BUILDER.registry(SHEEP_VARIANT_KEY);
}
