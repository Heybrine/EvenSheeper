package net.heybrine.evensheeper.core.mixin.client.entities.renderer;

import com.blackgear.vanillabackport.client.api.renderer.SpecialMobRenderer;
//import com.blackgear.vanillabackport.core.mixin.client.entities.renderer.MobRendererMixin;
import com.blackgear.vanillabackport.core.mixin.client.entities.renderer.MobRendererMixin;
import net.heybrine.evensheeper.client.api.renderer.renderers.SheepSpecialRenderer;
import com.blackgear.vanillabackport.client.api.renderer.RenderConditions;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.world.entity.animal.Sheep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Supplier;

@Mixin(SheepRenderer.class)
public abstract class SheepRendererMixin extends MobRendererMixin<Sheep, SheepModel<Sheep>> {
    @Unique private Optional<Supplier<SheepSpecialRenderer>> renderer;

    public SheepRendererMixin(EntityRendererProvider.Context context, SheepModel<Sheep> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.renderer = SpecialMobRenderer.create(context, SheepSpecialRenderer::new, RenderConditions.FARM_ANIMALS);

        SpecialMobRenderer.addLayer(
                SpecialMobRenderer.create(context, ctx -> new SheepWoolUndercoatLayerMixin(this, ctx.getModelSet()), RenderConditions.SHEEP_UNDERCOAT),
                this::addLayer
        );
    }

    @Inject(
            method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Sheep;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void vb$getTextureLocation(Sheep entity, CallbackInfoReturnable<ResourceLocation> cir) {
        this.renderer.flatMap(renderer -> Optional.ofNullable(renderer.get()))
                .flatMap(renderer -> renderer.getTexture(entity))
                .ifPresent(cir::setReturnValue);
    }

    @Override
    public void render(Sheep entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.renderer.flatMap(renderer -> Optional.ofNullable(renderer.get()))
                .ifPresent(renderer -> this.model = renderer.getModel(entity).orElseGet(() -> this.defaultModel));
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}