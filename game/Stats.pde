class Stats {
  public int healthMax = 0, manaMax = 0;
  public float mana = 0, health = 0;
  
  public int vitality = 0, attack = 0, wisdom = 0, defence = 0;
  
  public float speed = 0, fireTimer = 0;
  
  private HashMap<String, Float> statusEffects = new HashMap<String, Float>();
  
  public void update(double delta) {
    String s;
    for(int i = 0; i < statusEffects.keySet().size(); i++) {
      s = (String)statusEffects.keySet().toArray()[i];
      if(statusEffects.get(s) - delta < 0) {
        statusEffects.remove(s);
      } else {
        statusEffects.replace(s, (float)(statusEffects.get(s) - delta));
      }
    }
    
    if(health < healthMax) {
      health = health + (float)(getVitality() * delta);
    }
    if(mana < manaMax) {
      mana = mana + (float)(getWisdom() * delta);
    }
    fireTimer = (float)(getFireTimer() + delta);
  }
  
  public void addStatusEffect(String name, float duration) {
    if(!STATUSES.containsKey(name)) return;
    statusEffects.put(name, duration);
  }
  
  public int getHealth() { return (int)health; }
  public int getHealthMax() { return healthMax; }
  public int getMana() { return (int)mana; }
  public int getManaMax() { return manaMax; }
  public int getVitality() { return (statusEffects.containsKey("SICK") ? 0 : statusEffects.containsKey("HEALING") ? vitality * 2 : vitality); }
  public int getAttack() { return (statusEffects.containsKey("WEAK") ? 0 : statusEffects.containsKey("DAMAGING") ? attack * 2 : attack); }
  public int getWisdom() { return (statusEffects.containsKey("DUMB") ? 0 : statusEffects.containsKey("SMART") ? wisdom * 2 : wisdom); }
  public int getDefence() { return (statusEffects.containsKey("ARMOURBREAK") ? 0 : statusEffects.containsKey("ARMOURED") ? defence * 2 : defence); }
  public float getFireTimer() { return (statusEffects.containsKey("DAZED") ? fireTimer/2 : statusEffects.containsKey("BEZERK") ? fireTimer * 2 : fireTimer); }
  public float getSpeed() { return (statusEffects.containsKey("SLOWED") ? speed/2 : statusEffects.containsKey("SPEEDY") ? speed * 2 : speed); }  
}

class PlayerStats extends Stats {
  
  //-----KEEP TRACK OF KILLS AND TIERS----
  private HashMap<Integer, Integer> healthKills = new HashMap<Integer, Integer>();
  private HashMap<Integer, Integer> manaKills = new HashMap<Integer, Integer>();
  private HashMap<Integer, Integer> vitalityKills = new HashMap<Integer, Integer>();
  private HashMap<Integer, Integer> attackKills = new HashMap<Integer, Integer>();
  private HashMap<Integer, Integer> wisdomKills = new HashMap<Integer, Integer>();
  private HashMap<Integer, Integer> defenceKills = new HashMap<Integer, Integer>();
  private HashMap<Integer, Integer> speedKills = new HashMap<Integer, Integer>();
  
  private int baseHealth = 100, baseMana = 100;
  private int baseVitality = 5, baseAttack = 1, baseWisdom = 5, baseDefence = 1;
  
  private float baseSpeed = 2;
  
  private int totalKills = 0;
  
  PlayerStats() {
    health = baseHealth;
    mana = baseMana;
    calcAllStats();
  }
  
  public void addKill() {
    totalKills ++;
  }
  
  public void addOrbStat(String stat, int tier) {
    switch(stat) {
      case("HEALTH"):
        healthKills.put(tier, healthKills.getOrDefault(tier, 0) + 1);
        healthMax = (int)calcStatValue(healthKills, baseHealth, 5, 0.5);
        break;
      case("MANA"):
        manaKills.put(tier, manaKills.getOrDefault(tier, 0) + 1);
        manaMax = (int)calcStatValue(manaKills, baseMana, 5, 0.2);
        break;
      case("VITALITY"):
        vitalityKills.put(tier, vitalityKills.getOrDefault(tier, 0) + 1);
        vitality = (int)calcStatValue(vitalityKills, baseVitality, 1, 0.1);
        break;
      case("ATTACK"):
        attackKills.put(tier, attackKills.getOrDefault(tier, 0) + 1);
        attack = (int)calcStatValue(attackKills, baseAttack, 1, 0.1);
        break;
      case("WISDOM"):
        wisdomKills.put(tier, wisdomKills.getOrDefault(tier, 0) + 1);
        wisdom = (int)calcStatValue(wisdomKills, baseWisdom, 1, 0.1);
        break;
      case("DEFENCE"):
        defenceKills.put(tier, defenceKills.getOrDefault(tier, 0) + 1);
        defence = (int)calcStatValue(defenceKills, baseDefence, 1, 0.1);
        break;
      case("SPEED"):
        speedKills.put(tier, speedKills.getOrDefault(tier, 0) + 1);
        speed = calcStatValue(speedKills, baseSpeed, 1, 0.1);
        break;
    }
  }  
  
  private void calcAllStats() {
    healthMax = (int)calcStatValue(healthKills, baseHealth, 5, 0.5);
    manaMax = (int)calcStatValue(manaKills, baseMana, 5, 0.2);
    vitality = (int)calcStatValue(vitalityKills, baseVitality, 1, 0.1);
    attack = (int)calcStatValue(attackKills, baseAttack, 1, 0.1);
    wisdom = (int)calcStatValue(wisdomKills, baseWisdom, 1, 0.1);
    defence = (int)calcStatValue(defenceKills, baseDefence, 1, 0.1);
    speed = calcStatValue(speedKills, baseSpeed, 1, 0.1);
  }
  
  private float calcStatValue(HashMap<Integer, Integer> stat, float base, int max, float rate) {
    float value = base;
    for(int tier : stat.keySet()) {
      value += calcStatTierValue(max, rate, stat.get(tier)) * (tier + 1);
    }
    return value;
  }
  
  private float calcStatTierValue(int max, float rate, int num) {
    return (-max * exp(-rate * num) + max); 
  }
  
  public int getTotalKills() {
    return totalKills;
  }
}

public HashMap<String, String> STATUSES = new HashMap<String, String>();
public HashMap<String, Integer> statColours = new HashMap<String, Integer>();

public void loadStats() {
  STATUSES.put("SICK", "SICK");
  STATUSES.put("HEALING", "HEALING");
  STATUSES.put("WEAK", "WEAK");
  STATUSES.put("DAMAGING", "DAMAGING");
  STATUSES.put("DUMB", "DUMB");
  STATUSES.put("SMART", "SMART");
  STATUSES.put("ARMOURBREAK", "ARMOURBREAK");
  STATUSES.put("ARMOURED", "ARMOURED");
  STATUSES.put("DAZED", "DAZED");
  STATUSES.put("BEZERK", "BEZERK");
  STATUSES.put("SLOWED", "SLOWED");
  STATUSES.put("SPEEDY", "SPEEDY");
  
  statColours.put("HEALTH", color(230, 100, 100));
  statColours.put("MANA", color(153, 217, 234));
  statColours.put("SPEED", color(100, 230, 100));
  statColours.put("WISDOM", color(140, 50, 230));
  statColours.put("DEFENCE", color(0, 15, 230));
  statColours.put("ATTACK", color(230, 150, 0));
}

public PImage applyColourToImage(PImage img, color c) {
  img.loadPixels();
  for(int i = 0; i < img.pixels.length; i ++) {
    img.pixels[i] = color(red(c), green(c), blue(c), alpha(img.pixels[i]));
  }
  img.updatePixels();
  return img;
}

String[] stats = {"HEALTH", "MANA", "SPEED", "WISDOM", "DEFENCE", "ATTACK"};
