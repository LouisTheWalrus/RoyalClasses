package royal.louise;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

/**
 *
 * @author LouisE
 */

public class Hunter implements Listener
{
  public RoyalClasses plugin;
  
  public Hunter (RoyalClasses plugin)
  {
      this.plugin = plugin;
  }
  

  /*
  Called when enttiy takes damage
  */
  @EventHandler
  public void onEntityDamage(EntityDamageEvent event)
  {
      // Hunter's Damage Reduction
      if(event.getEntity() instanceof Player)
      {
          Player p = (Player) event.getEntity();
          if(plugin.isClass(p, "hunter"))
           {
            event.setDamage(event.getDamage()*.8);  
           }
      }
      
      // End Hunter's Damage Reduction
      
    
    // Hunter Damage Boost Melee
    if (!(event instanceof EntityDamageByEntityEvent)) return;
    EntityDamageByEntityEvent event_EE = (EntityDamageByEntityEvent)event;
    if ((event_EE.getDamager() instanceof Player))
    {
      Player damager = (Player)event_EE.getDamager();
          if (plugin.isClass(damager, "Hunter"))
          {
           ItemStack hand = damager.getInventory().getItemInMainHand();
            {
              if(hand.getType().toString().contains("SWORD")|hand.getType().toString().contains("AXE"))
              {
                  event.setDamage(event.getDamage()+3);
              }
            }
          }

        }
    // End Hunter Damage Boost Melee
    
    // Hunter Bow Damage
    else if ((event_EE.getDamager() instanceof Arrow))
    {
        Arrow a = (Arrow) event_EE.getDamager();
        ProjectileSource s = a.getShooter();
        
        if(s instanceof Player)
        {
            Player p = (Player) s;
            if (plugin.isClass(p, "Hunter"))
          {
              event.setDamage(event.getDamage()+5);
          }
        }
    }
    
    // End Hunter Bow Damage

  }
 
}
