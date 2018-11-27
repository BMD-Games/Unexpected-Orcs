class Ability extends Item {
  
  protected float cooldown, cooldownTimer = 0, textTimer;
  protected int manaCost;
  
  Ability(String sprite, String name) {
    super(sprite, name);
    this.type = "Ability";
  }
  
  public void updateCooldown(double delta) {
    cooldownTimer -= delta;
    textTimer += delta;
  }
  
  public float getPercentCooldown() {
    return cooldownTimer/cooldown;
  }
  
  protected void buff() {}
  
  
}

class SwiftBoots extends Ability {
  
  SwiftBoots() {
    super("BOOTS", "Boots of Swiftness");
    this.cooldown = 4;
    this.manaCost = 30;
  }
  
  @Override
  public void buff() {
    
    if (manaCost > engine.player.stats.getMana() && textTimer >= 0.5){
      String cooldownText = "Not Enough Mana";
      engine.addCooldownText(cooldownText, engine.player.x, engine.player.y, 0.5);
      textTimer = 0;
    }
    
    if (cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
      cooldownTimer = cooldown;
      engine.player.stats.addStatusEffect("SPEEDY", 3);
      engine.player.stats.setMana(engine.player.stats.getMana() - manaCost);
      String cooldownText = "Speed Buff";
      engine.addCooldownText(cooldownText, engine.player.x, engine.player.y, 0.5);
      textTimer = 0;
    } else {
      if (textTimer >= 0.5 && manaCost < engine.player.stats.getMana()) {
        String cooldownText = "";
        cooldownText = String.format("%.3gs%n", cooldownTimer);
        engine.addCooldownText(cooldownText, engine.player.x, engine.player.y, 0.5);
        textTimer = 0;
      }
    }
  }
  
}
