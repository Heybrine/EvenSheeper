package net.heybrine.evensheeper.client.registries;

import net.heybrine.evensheeper.core.EvenSheeper;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    //SHEAR WILL
    public static final ModelLayerLocation COLD_SHEEP = register("cold_sheep");
    public static final ModelLayerLocation WARM_SHEEP = register("warm_sheep");

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String name, String layer) {
        return new ModelLayerLocation(new ResourceLocation(name), layer);
    }

    private static ModelLayerLocation registerBuiltIn(String name) {
        return registerBuiltIn(name, "main");
    }

    private static ModelLayerLocation registerBuiltIn(String name, String layer) {
        return new ModelLayerLocation(EvenSheeper.resource(name), layer);
    }
}
