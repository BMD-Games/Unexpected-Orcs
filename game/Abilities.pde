class Ability extends Item {
  
  protected float cooldown, cooldownTimer = 0;
  protected int manaCost;
  
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

class SwiftBoots extends Ability {
  
  SwiftBoots() {
    super("BOOTS", "Boots of Swiftness");
    this.cooldown = 4;
    this.manaCost = 30;
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
