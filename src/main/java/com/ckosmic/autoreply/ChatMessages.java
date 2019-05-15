package com.ckosmic.autoreply;

import java.util.ArrayList;
import java.util.Random;

public class ChatMessages {
    public ArrayList<ChatTerm> chatTerms = new ArrayList<ChatTerm>();

    public String getRandomOutMessage() {
        String[] msgs = ExampleMod.config.outs.toArray(new String[0]);

        if(msgs.length > 1) {
            Random rand = new Random();
            return msgs[rand.nextInt(msgs.length - 1)];
        } else if(msgs.length == 1) {
            return msgs[0];
        } else {
            return "";
        }
    }
}
