package com.ckosmic.autoreply;

public class Helper {
    public static ChatMessages chatMessages;

    public static void setupChatMessages() {
        chatMessages = new ChatMessages();
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"slime"}, "Slimeballs"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"shulker box"}, "Shulker Boxes"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"mending"}, "Mending Books"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"silk", "silk touch"}, "Silk Touch Books"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"mycelium"}, "Mycelium Blocks"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"lead"}, "Leads"));
        chatMessages.chatTerms.add(new ChatTerm(new String[]{"stone"}, "Stone Blocks"));
    }
}
