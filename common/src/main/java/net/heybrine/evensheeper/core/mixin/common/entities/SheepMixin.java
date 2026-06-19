package net.heybrine.evensheeper.core.mixin.common.entities;

import com.blackgear.vanillabackport.common.level.entities.animal.SheepColorSpawnRules;
import com.blackgear.vanillabackport.core.mixin.common.entities.MobMixin;
import net.heybrine.evensheeper.core.registries.ModBuiltinRegistries;
import net.minecraft.util.RandomSource;
import com.blackgear.vanillabackport.common.api.variant.VariantDataHolder;
import com.blackgear.vanillabackport.common.api.variant.VariantSpawner;
import com.blackgear.vanillabackport.common.api.variant.VariantUtils;
import com.blackgear.vanillabackport.common.api.variant.spawn.SpawnContext;
import net.heybrine.evensheeper.common.level.entities.animal.SheepVariant;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
//import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheep.class)
public abstract class SheepMixin extends MobMixin implements VariantDataHolder<SheepVariant> {

    protected SheepMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    /*protected SheepMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }*/

    @Unique
    private static final EntityDataAccessor<String> DATA_VARIANT_ID = SynchedEntityData.defineId(Sheep.class, EntityDataSerializers.STRING);

    @Inject(
            method = {"getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/Sheep;"},
            at = {@At("RETURN")}
    )
    private void vb$getBreedOffspring(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<Sheep> cir) {
        Sheep child = (Sheep) cir.getReturnValue();
        if (child != null && otherParent instanceof Sheep mate) {
            VariantDataHolder.trySetOffspringVariant(child, this, mate);
        }

    }

    protected void vb$defineSynchedData(CallbackInfo ci) {
        this.entityData.define(DATA_VARIANT_ID, "minecraft:temperate");
    }

    public void setVariantData(SheepVariant variant) {
        this.entityData.set(DATA_VARIANT_ID, VariantUtils.getID(ModBuiltinRegistries.SHEEP_VARIANTS, variant));
    }

    public Optional<SheepVariant> getVariantData() {
        return VariantUtils.getOrDefault(ModBuiltinRegistries.SHEEP_VARIANTS, (String)this.entityData.get(DATA_VARIANT_ID));
    }

    protected void vb$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        VariantUtils.addVariantSaveData(this, tag, ModBuiltinRegistries.SHEEP_VARIANTS);
    }

    protected void vb$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        VariantUtils.readVariantSaveData(this, tag, ModBuiltinRegistries.SHEEP_VARIANTS);
    }

    protected void vb$finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        VariantUtils.selectVariantToSpawn(SpawnContext.create(level, this.blockPosition()), ModBuiltinRegistries.SHEEP_VARIANTS, VariantSpawner.FARM_ANIMALS).ifPresent(this::setVariantData);
    }

    @Redirect(
            method = {"finalizeSpawn"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/animal/Sheep;getRandomSheepColor(Lnet/minecraft/util/RandomSource;)Lnet/minecraft/world/item/DyeColor;"
            )
    )
    private DyeColor vb$updateColors(RandomSource random) {
        DyeColor originalColor = Sheep.getRandomSheepColor(random);
        return SheepColorSpawnRules.getRandomSheepColor(originalColor, this.level(), this.blockPosition(), random);
    }
}
