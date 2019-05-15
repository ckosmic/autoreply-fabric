package com.ckosmic.autoreply;

import java.util.ArrayList;
import java.util.Random;

public class ChatMessages {
    public ArrayList<ChatTerm> chatTerms = new ArrayList<ChatTerm>();

    public String getRandomOutMessage() {
        String[] msgs = {
                " can be found at /warp Noctis!",
                " are at /warp Noctis!",
                " at /warp Noctis!",
        };

        Random rand = new Random();
        return msgs[rand.nextInt(msgs.length-1)];
    }
}
