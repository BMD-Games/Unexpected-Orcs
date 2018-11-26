class Ability extends Item {
  
  float cooldown, cooldownTimer = 0;
  int manaCost;
  
  Ability(String sprite, String name) {
    super(sprite, name);
    this.type = "Ability";
  }
  
  public void updateCooldown(double delta) {
    cooldownTimer -= delta;
  }
  
  public float getPercentCooldown() {
    return cooldownTimer/cooldown;
  }
  
  protected void buff() {}
  
  
}

class SpeedBuff extends Ability {
  
  SpeedBuff() {
    super("MACHINE_GUN", "SPEEDBUFF");
    this.cooldown = 4;
    this.manaCost = 60;
  }
  
  @Override
  public void buff() {
    if (cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
      cooldownTimer = cooldown;
      engine.player.stats.addStatusEffect("SPEEDY", 3);
      engine.player.stats.setMana(engine.player.stats.getMana() - manaCost);
    }
  }
  
}
