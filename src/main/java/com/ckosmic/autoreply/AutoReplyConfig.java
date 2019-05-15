package com.ckosmic.autoreply;

import io.github.cottonmc.cotton.config.annotations.ConfigFile;
import io.github.cottonmc.repackage.blue.endless.jankson.Comment;

import java.util.ArrayList;

@ConfigFile(name="AutoReplyConfigFile")
public class AutoReplyConfig {
    @Comment(value="Chat [persistentPhrase] every [timeBetween] seconds")
    public boolean persistentChat = false;
    public String persistentPhrase = "Test persistent phrase.";
    public float timeBetween = 1000*60;

    public ArrayList<String> terms = new ArrayList<>();
    public ArrayList<String> ins = new ArrayList<>();
    public ArrayList<String> outs = new ArrayList<>();
}
