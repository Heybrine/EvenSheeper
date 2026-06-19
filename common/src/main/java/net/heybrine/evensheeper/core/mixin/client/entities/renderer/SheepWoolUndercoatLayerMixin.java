package net.heybrine.evensheeper.core.mixin.client.entities.renderer;

import com.blackgear.vanillabackport.client.level.entities.layer.SheepWoolUndercoatLayer;
import com.blackgear.vanillabackport.client.util.LazyModel;
import com.blackgear.vanillabackport.common.api.variant.VariantDataHolder;
import net.heybrine.evensheeper.common.level.entities.animal.SheepVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
//import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SheepWoolUndercoatLayer.class)
public class SheepWoolUndercoatLayerMixin extends RenderLayer<Sheep, SheepModel<Sheep>> {
    //private static final ResourceLocation SHEEP_WOOL_UNDERCOAT_TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_wool_undercoat.png");
    private final LazyModel<Sheep, EntityModel<Sheep>> model;

    public SheepWoolUndercoatLayerMixin(RenderLayerParent<Sheep, SheepModel<Sheep>> renderer, EntityModelSet models) {
        super(renderer);
        this.model = LazyModel.of(models, ModelLayers.SHEEP, SheepFurModel::new);
    }

    @Override
    public void render(PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight,
                       Sheep sheep,
                       float limbSwing,
                       float limbSwingAmount,
                       float partialTick,
                       float ageInTicks,
                       float netHeadYaw,
                       float headPitch) {
        if (sheep.getType() == EntityType.SHEEP && !sheep.isInvisible()) {

            if (!(sheep instanceof VariantDataHolder<?> holder)) return;
            SheepVariant variant =
                    (SheepVariant) holder.getVariantData().orElse(null);
            if (variant == null) return;

            float red, green, blue;
            if (sheep.hasCustomName() && "jeb_".equals(sheep.getName().getString())) {
                int offset = sheep.tickCount / 25 + sheep.getId();
                int size = DyeColor.values().length;
                int fromIndex = offset % size;
                int toIndex = (offset + 1) % size;
                float delta = ((float) (sheep.tickCount % 25) + partialTick) / 25.0F;
                float[] fromColor = Sheep.getColorArray(DyeColor.byId(fromIndex));
                float[] toColor = Sheep.getColorArray(DyeColor.byId(toIndex));
                red = fromColor[0] * (1.0F - delta) + toColor[0] * delta;
                green = fromColor[1] * (1.0F - delta) + toColor[1] * delta;
                blue = fromColor[2] * (1.0F - delta) + toColor[2] * delta;
            } else {
                float[] color = Sheep.getColorArray(sheep.getColor());
                red = color[0];
                green = color[1];
                blue = color[2];
            }

            coloredCutoutModelCopyLayerRender(
                    this.getParentModel(),
                    this.model.get(),
                    //SHEEP_WOOL_UNDERCOAT_TEXTURE,
                    variant.undercoat().path(),
                    poseStack,
                    buffer,
                    packedLight,
                    sheep,
                    limbSwing,
                    limbSwingAmount,
                    ageInTicks,
                    netHeadYaw,
                    headPitch,
                    partialTick,
                    red,
                    green,
                    blue);
        }
    }
}
