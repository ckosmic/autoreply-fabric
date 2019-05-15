package com.ckosmic.autoreply.mixin;

import com.ckosmic.autoreply.ExampleMod;
import com.ckosmic.autoreply.Helper;
import net.minecraft.client.network.packet.ChatMessageS2CPacket;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.text.TextComponent;
import net.minecraft.client.MinecraftClient;

@Mixin(ChatMessageS2CPacket.class)
public class AutoReplyMixin {

    @Inject(at = @At("RETURN"), method = "getMessage")
    private void onGetMessage(CallbackInfoReturnable<TextComponent> cir) {
        String message = cir.getReturnValue().getFormattedText().toLowerCase();

        String formatted = getFormattedMessage(message);

        if(formatted.length() > 0 && !message.contains("noctis") && !message.contains("[shop]")) {
            Helper.printMessage(formatted);
        }
    }

    private String getFormattedMessage(String searchTerm) {
        String[] searchWords = searchTerm.split(" ");
        searchTerm = "";
        for (int i = 1; i < searchWords.length; i++) {
            searchTerm = searchTerm + searchWords[i] + " ";
        }

        String[] ins = ExampleMod.config.ins.toArray(new String[0]);

        for (int i = 0; i < Helper.chatMessages.chatTerms.size(); i++) {
            String term = Helper.chatMessages.chatTerms.get(i).term;
            String itemName = Helper.chatMessages.chatTerms.get(i).itemName;
            for (int k = 0; k < ins.length; k++) {
                if (searchTerm.contains(ins[k]) && searchTerm.contains(term)) {
                    String out = Helper.chatMessages.getRandomOutMessage();
                    out = out.replace("[item]", itemName);
                    return out;
                }
            }
        }
        return "";
    }

}
