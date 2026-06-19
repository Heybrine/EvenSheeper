package net.heybrine.evensheeper.core.mixin.client.entities.renderer;

import com.blackgear.vanillabackport.client.api.renderer.AbstractVariantRenderer;
import com.blackgear.vanillabackport.client.util.LazyModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.heybrine.evensheeper.client.api.renderer.renderers.SheepSpecialRenderer;
import net.heybrine.evensheeper.common.level.entities.animal.SheepVariant;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SheepFurModel)
public class SheepFurLayerMixin extends AbstractVariantRenderer<Sheep, SheepModel<Sheep>> {
    private final LazyModel<Sheep, EntityModel<Sheep>>
}