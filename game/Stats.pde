class Stats {
  private int health = 0, healthMax = 0;
  private int mana = 0, manaMax = 0;
  
  private int vitality = 0, attack = 0, wisdom = 0, defence = 0;
  
  private float speed = 0, fireTimer = 0;
  
  private HashMap<String, Float> statusEffects = new HashMap<String, Float>();
  
  public void update(double delta) {
    for(String s : statusEffects.keySet()) {
      if(statusEffects.get(s) - delta < 0) {
        statusEffects.remove(s);
      } else {
        statusEffects.replace(s, (float)(statusEffects.get(s) - delta));
      }
    }
    
    if(getHealth() < getHealthMax()) {
      setHealth(getHealth() + getVitality());
    }
    if(getMana() < getManaMax()) {
      setMana(getMana() + getWisdom());
    }
    setFireTimer((float)(getFireTimer() + delta));
  }
  
  public void addStatusEffect(String name, float duration) {
    if(!STATUSES.containsKey(name)) return;
    statusEffects.put(name, duration);
  }
  
  public int getHealth() { return health; }
  public int getHealthMax() { return healthMax; }
  public int getMana() { return mana; }
  public int getManaMax() { return manaMax; }
  public int getVitality() { return (statusEffects.containsKey("SICK") ? 0 : statusEffects.containsKey("HEALING") ? vitality * 2 : vitality); }
  public int getAttack() { return (statusEffects.containsKey("WEAK") ? 0 : statusEffects.containsKey("DAMAGING") ? attack * 2 : attack); }
  public int getWisdom() { return (statusEffects.containsKey("DUMB") ? 0 : statusEffects.containsKey("SMART") ? wisdom * 2 : wisdom); }
  public int getDefence() { return (statusEffects.containsKey("ARMOURBREAK") ? 0 : statusEffects.containsKey("STRONG") ? defence * 2 : defence); }
  public float getFireTimer() { return (statusEffects.containsKey("DAZED") ? fireTimer/2 : statusEffects.containsKey("BEZERK") ? fireTimer * 2 : fireTimer); }
  public float getSpeed() { return (statusEffects.containsKey("SLOWED") ? speed/2 : statusEffects.containsKey("SPEEDY") ? speed * 2 : speed); }
  
  public void setHealth(int value) { health = value; }
  public void setHealthMax(int value) { healthMax = value; }
  public void setMana(int value) { mana = value; }
  public void setManaMax(int value) { manaMax = value; }
  public void setVitality(int value) { vitality = value; }
  public void setAttack(int value) { attack = value; }
  public void setWisdom(int value) { wisdom = value; }
  public void setDefence(int value) { defence = value; }
  public void setFireTimer(float value) { fireTimer = value; }
  public void setSpeed(float value) { speed = value; }
  
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
  
  private int totalKills = 0;
  
  PlayerStats() {
    setHealth(5); 
    setHealthMax(100);
    setMana(5);
    setManaMax(100);
    setVitality(1);
    setAttack(1);
    setDefence(1);
    setWisdom(1);
    setSpeed(2);
  }
  
  public void addKill(ArrayList<String> stats, int tier) {
    totalKills ++;
    for(String stat : stats) {
      switch(stat) {
        case("HEALTH"):
          healthKills.put(tier, healthKills.getOrDefault(tier, 0) + 1);
          setHealthMax(calcStatValue(healthKills, 5, 0.5));
          break;
        case("MANA"):
          manaKills.put(tier, manaKills.getOrDefault(tier, 0) + 1);
          setManaMax(calcStatValue(manaKills, 5, 0.2));
          break;
        case("VITALITY"):
          vitalityKills.put(tier, vitalityKills.getOrDefault(tier, 0) + 1);
          setVitality(calcStatValue(vitalityKills, 1, 0.1));
          break;
        case("ATTACK"):
          attackKills.put(tier, attackKills.getOrDefault(tier, 0) + 1);
          setAttack(calcStatValue(attackKills, 1, 0.1));
          break;
        case("WISDOM"):
          wisdomKills.put(tier, wisdomKills.getOrDefault(tier, 0) + 1);
          setWisdom(calcStatValue(wisdomKills, 1, 0.1));
          break;
        case("DEFENCE"):
          defenceKills.put(tier, defenceKills.getOrDefault(tier, 0) + 1);
          setDefence(calcStatValue(defenceKills, 1, 0.1));
          break;
        case("SPEED"):
          speedKills.put(tier, speedKills.getOrDefault(tier, 0) + 1);
          setSpeed(calcStatValue(speedKills, 1, 0.1));
          break;
      }
    }
  }  
  
  private int calcStatValue(HashMap<Integer, Integer> stat, int max, float rate) {
    int value = 0;
    for(int tier : stat.keySet()) {
      value += calcStatTierValue(max, rate, stat.get(tier)) * tier;
    }
    return value;
  }
  
  private int calcStatTierValue(int max, float rate, int num) {
    return (int)(-max * exp(-rate * num) + max); 
  }
  
  public int getTotalKills() {
    return totalKills;
  }
  
}

public HashMap<String, String> STATUSES = new HashMap<String, String>();
public void loadStatuses() {
  STATUSES.put("SICK", "SICK");
  STATUSES.put("HEALING", "HEALING");
  STATUSES.put("WEAK", "WEAK");
  STATUSES.put("DAMAGING", "DAMAGING");
  STATUSES.put("DUMB", "DUMB");
  STATUSES.put("SMART", "SMART");
  STATUSES.put("ARMOURBREAK", "ARMOURBREAK");
  STATUSES.put("STRONG", "STRONG");
  STATUSES.put("DAZED", "DAZED");
  STATUSES.put("BEZERK", "BEZERK");
  STATUSES.put("SLOWED", "SLOWED");
  STATUSES.put("SPEEDY", "SPEEDY");
}
