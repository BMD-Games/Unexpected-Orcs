class Ability extends Item {
  
  protected int manaCost;
  float cooldown;
  
  Ability(String sprite, String name) {
    super(sprite, name);
    this.type = "Ability";
  }
  
  public void makeText() {
    
    if (manaCost > engine.player.stats.getMana() && engine.player.textTimer >= 0.5){
      String cooldownText = "Not Enough Mana";
      engine.addCooldownText(cooldownText, engine.player.x, engine.player.y, 0.5);
      engine.player.textTimer = 0;
    }
    
    if (engine.player.cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
      engine.player.cooldownTimer = cooldown;
      
      engine.player.stats.setMana(engine.player.stats.getMana() - manaCost);
      String cooldownText = "Speed Buff";
      engine.addCooldownText(cooldownText, engine.player.x, engine.player.y, 0.5);
      engine.player.textTimer = 0;
    } else {
      if (engine.player.textTimer >= 0.5 && manaCost < engine.player.stats.getMana()) {
        String cooldownText = "";
        cooldownText = String.format("%.3gs%n", engine.player.cooldownTimer);
        engine.addCooldownText(cooldownText, engine.player.x, engine.player.y, 0.5);
        engine.player.textTimer = 0;
      }
    }
  }
  
  public void ability() {}
  
}

class SwiftBoots extends Ability {
  
  SwiftBoots() {
    super("BOOTS", "Boots of Swiftness");
    this.cooldown = 4;
    this.manaCost = 30;
  }
  
  @Override
  public void ability(){
    if (engine.player.cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
      engine.player.stats.addStatusEffect("SPEEDY", 3);
    }
  }
  
}


class FireBomb extends Ability {
  
  FireBomb() {
    super("FIREBOMB", "Fire Bomb");
    this.cooldown = 4;
    this.manaCost = 30;
  }
  
  @Override
  public void ability() {
    if (engine.player.cooldownTimer <= 0 && manaCost <= engine.player.stats.getMana()){
      for (int i = 0; i < 8; i++) {
        engine.playerProjectiles.add(new Projectile(engine.player.x, engine.player.y, PVector.fromAngle(PI * i / 4), 
                5, 3, 100, "FIREBALL"));
      }
    }
  
  }
}
