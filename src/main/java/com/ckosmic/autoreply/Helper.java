package com.ckosmic.autoreply;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;

public class Helper {
    public static ChatMessages chatMessages;

    public static void setupChatMessages() {
        chatMessages = new ChatMessages();

        /*ArrayList<String[]> arrayList = ExampleMod.config.terms;
        String[][] terms = new String[arrayList.size()][];
        for(int i = 0; i < arrayList.size(); i++) {
            String[] row = arrayList.get(i);
            terms[i] = arrayList.get(i);
        }*/
        String[] terms = ExampleMod.config.terms.toArray(new String[0]);
        //String[] meanings = ExampleMod.config.meanings.toArray(new String[0]);

        if(terms.length > 0) {
            for (int i = 0; i < terms.length; i++) {
                String[] termMeanings = terms[i].split("\\|");
                chatMessages.chatTerms.add(new ChatTerm(termMeanings[0], termMeanings[1]));
            }
        }

        /*chatMessages.chatTerms.add(new ChatTerm(new String[]{"slime"}, "Slimeballs"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"shulker box"}, "Shulker Boxes"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"mending"}, "Mending Books"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"silk", "silk touch"}, "Silk Touch Books"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"mycelium"}, "Mycelium Blocks"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"lead"}, "Leads"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"stone"}, "Stone Blocks"));*/
    }

    public static void printMessage(String message) {
        MinecraftClient.getInstance().player.sendChatMessage(message);
    }
}
