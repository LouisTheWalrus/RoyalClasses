package dcl.louise;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Cross
 */
public class Vampire implements Listener
{
  public DennisClasses plugin;
  Cooldown cd = new Cooldown(5000);
  
  public Vampire (DennisClasses plugin)
  {
      this.plugin = plugin;
  }
  

  
  @EventHandler
  public void onEntityDamage(EntityDamageEvent event)
  {
      if(event.getEntity() instanceof Player)
      {
          Player p = (Player) event.getEntity();
          if(plugin.isClass(p, "vampire"))
           {
            event.setDamage(event.getDamage()*.9);
           }
      }

    if (!(event instanceof EntityDamageByEntityEvent)) return;
    EntityDamageByEntityEvent event_EE = (EntityDamageByEntityEvent)event;
    
    
    if ((event_EE.getDamager() instanceof Player))
    {
      Player damager = (Player)event_EE.getDamager();
      
          if (plugin.isClass(damager, "vampire") && !damager.isSneaking())
          {
            event.setDamage(event.getDamage()+4);   
          }
          else
          {
              if(!cd.isOnCooldown(damager))
              {
            RD.displayMessage("You lifestealed &c" + event.getEntity().getName() + "&7", damager);
            event.setDamage(event.getDamage()+8);
            if(damager.getHealth()<15)
            {
            damager.setHealth(damager.getHealth()+5);
            }
            else
            {
            damager.setHealth(20);    
            }
            cd.addCooldown(damager);
              }
              else
              {
                  RD.displayMessage("On cooldown", damager);
              }
          }
      }
    
  }
  
    @EventHandler
  public void onPlayerMove(PlayerMoveEvent event)
  {
    Player player = event.getPlayer();

    if (plugin.isClass(player.getName(), "vampire"))
    {
        if ((event.getPlayer().getWorld().getTime()>12000))
        {
          player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));

        }

    }
  }
    
}
