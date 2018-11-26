class Ability extends Item {
  
  float cooldown;
  float lastUse;
  float timeSinceLast;
  float percentCooldown;
  int manaCost;
  
  Ability(String sprite, String name) {
    super(sprite, name);
    this.type = "Ability";
  }
  
  public float updateCooldown() {
    
    timeSinceLast = millis() - lastUse;
    if (timeSinceLast >= cooldown) {
      percentCooldown = 0;
    } else {
      percentCooldown = timeSinceLast / cooldown;
    }
    return percentCooldown;
  }
  
  public void buff(Player player) {
    println("ayo");
  }
  
  
}

class SpeedBuff extends Ability {
  
  SpeedBuff() {
    super("MACHINE_GUN", "SPEEDBUFF");
    this.cooldown = 1000;
    this.manaCost = 60;
  }
  
  public void buff(Player player) {
    if (timeSinceLast >= cooldown && manaCost <= player.stats.getMana()){
      player.stats.addStatusEffect("SPEEDY", 0.5);
      this.lastUse = millis();
      updateCooldown();
      println(percentCooldown);
      player.stats.setMana(player.stats.getMana() - manaCost);
    }
  }
  
}
