package com.ckosmic.autoreply.mixin;

import com.ckosmic.autoreply.Helper;
import net.minecraft.client.network.packet.ChatMessageS2CPacket;
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
            MinecraftClient.getInstance().player.sendChatMessage(formatted);
        }
    }

    private String getFormattedMessage(String searchTerm) {
        String[] searchWords = searchTerm.split(" ");
        searchTerm = "";
        for(int i = 1; i < searchWords.length; i++) {
            searchTerm = searchWords + " ";
        }

        String[] ins = {
                "anyone selling",
                "anyone have",
                "anyone sell",
                "anyone got",
                "who has",
                "who sells",
                "whos selling",
                "who's selling",
                "whos got",
                "who's got",
                "shop have",
                "warp have",
                "shop selling",
                "warp selling",
                "shop sell",
                "warp sell",
                "shop has",
                "warp has",
        };

        for(int i = 0; i < Helper.chatMessages.chatTerms.size(); i++) {
            for(int j = 0; j < Helper.chatMessages.chatTerms.get(i).terms.length; j++) {
                for(int k = 0; k < ins.length; k++) {
                    if(searchTerm.contains(ins[k]) && searchTerm.contains(Helper.chatMessages.chatTerms.get(i).terms[j])) {
                        return Helper.chatMessages.chatTerms.get(i).itemName + Helper.chatMessages.getRandomOutMessage();
                    }
                }
            }
        }
        return "";
    }

}
