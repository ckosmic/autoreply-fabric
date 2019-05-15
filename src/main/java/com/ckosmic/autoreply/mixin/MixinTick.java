package com.ckosmic.autoreply.mixin;

import com.ckosmic.autoreply.ExampleMod;
import com.ckosmic.autoreply.Helper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinTick {

    @Shadow public abstract void tick();

    private int tickCount = 0;

    @Inject(at = @At("RETURN"), method = "tick")
    private void onTick(CallbackInfo info) {
        tickCount++;
        if(tickCount >= ExampleMod.config.timeBetween && ExampleMod.config.persistentChat) {
            tickCount = 0;
            Helper.printMessage(ExampleMod.config.persistentPhrase);
        }
    }
}
