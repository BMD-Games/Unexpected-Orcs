package com.bmd.Items.Scrolls;

import com.bmd.Items.Scroll;
import com.bmd.Util.Pair;
import com.bmd.Util.Util;

import java.util.ArrayList;

public class DebuffScroll extends Scroll {

    public DebuffScroll(String[] debuffNames) {
        super("SCROLL_1", "");
        ArrayList<Pair> debuffs = new ArrayList();
        String[] effectNames = new String[debuffNames.length];
        String[] debuffDesc = new String[debuffNames.length];
        for(int i = 0; i < debuffNames.length; ++i) {
            debuffs.add(new Pair("ALL", debuffNames[i]));
            effectNames[i] = Util.debuffToVerb(debuffNames[i]);
            debuffDesc[i] = Util.debuffToPresentVerb(debuffNames[i]);
        }
        this.name = "Scroll of " + Util.linkWords(effectNames);
        this.statusEffects = debuffs;
        this.description = Util.capFirstLetter(Util.linkWords(debuffDesc)) + " all enemies hit for one second";
    }

}