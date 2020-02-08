package Items.Scrolls;

import Items.Scroll;
import Stats.StatusEffectType;
import Utility.Pair;
import Utility.Util;

import java.util.ArrayList;

public class DebuffScroll extends Scroll {

    public DebuffScroll(StatusEffectType[] debuffNames) {
        super("SCROLL_1", "");
        ArrayList<Pair> debuffs = new ArrayList();
        String[] effectNames = new String[debuffNames.length];
        String[] debuffDesc = new String[debuffNames.length];
        for(int i = 0; i < debuffNames.length; ++i) {
            debuffs.add(new Pair(StatusEffectType.ALL, debuffNames[i]));
            effectNames[i] = Util.debuffToVerb(debuffNames[i]);
            debuffDesc[i] = Util.debuffToPresentVerb(debuffNames[i]);
        }
        this.name = "Scroll of " + Util.linkWords(effectNames);
        this.statusEffects = debuffs;
        this.description = Util.capFirstLetter(Util.linkWords(debuffDesc)) + " all enemies hit for one second";
    }

}