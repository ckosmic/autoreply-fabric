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
        boolean blacklisted = false;

        for(int i = 0; i < ExampleMod.config.blacklist.size(); i++) {
            if(message.toLowerCase().contains(ExampleMod.config.blacklist.get(i).toLowerCase())) {
                blacklisted = true;
                break;
            }
        }
        if(formatted.length() > 0 && blacklisted == false) {
            Helper.printMessage(formatted);
        } else if(message.contains("->") && message.contains("!price")) {
            String[] parts = message.split(" ");
            String itemName = "";
            boolean priceFound = false;
            for(int i = 0; i < parts.length; i++) {
                if(priceFound)
                    itemName += parts[i] + " ";
                if(parts[i].contains("!price"))
                    priceFound = true;
            }
            System.out.println(itemName);
            Helper.printMessage("/r " + getPriceOfItem(itemName));
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

    private String getPriceOfItem(String itemName) {
        String[] stock = ExampleMod.config.shopItems.toArray(new String[0]);
        String[] itemNameParts = itemName.split(" ");

        for(int i = 0; i < stock.length; i++) {
            String[] parts = stock[i].split("\\|");
            for(int j = 0; j < itemNameParts.length; j++) {
                if (parts[0].toLowerCase().contains(itemNameParts[j].toLowerCase())) {
                    return parts[1] + " " + parts[0] + " go for " + parts[2] + " at /warp Noctis.";
                }
            }
        }
        return "Sorry, that item is not carried at /warp Noctis.";
    }

}
