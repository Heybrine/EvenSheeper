package net.heybrine.evensheeper.core;

import com.blackgear.platform.core.Environment;
import com.blackgear.platform.core.ModInstance;
import com.blackgear.platform.core.api.registrar.Registrar;
import com.blackgear.platform.core.util.config.ConfigLoader;
import com.blackgear.platform.core.util.config.ModConfig;

import com.blackgear.vanillabackport.core.registries.ModBuiltinRegistries;
import com.mojang.logging.LogUtils;
import net.heybrine.evensheeper.common.level.entities.animal.SheepVariants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EvenSheeper {
    public static final String MOD_ID = "evensheeper";
    public static final String NAMESPACE = "minecraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        // Write common init code here.
        LOGGER.info("Shearing sheaps...I mean sheeps");

        /*public static void bootstrap() {
            INSTANCE.bootstrap();
            ConfigLoader.bootstrap();

            SheepVariants.REGISTRY.register();

        }*/
    }
}
