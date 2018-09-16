package dcl.louise;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
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
import org.bukkit.projectiles.ProjectileSource;

/**
 *
 * @author Cross
 */
public class Hunter implements Listener
{
  public DennisClasses plugin;
  
  public Hunter (DennisClasses plugin)
  {
      this.plugin = plugin;
  }
  

  
      @EventHandler
  public void onEntityDamage(EntityDamageEvent event)
  {
     
      if(event.getEntity() instanceof Player)
      {
          Player p = (Player) event.getEntity();
          if(plugin.isClass(p, "hunter"))
           {
            event.setDamage(event.getDamage()*.8);
           }
      }
    
    
    
    if (!(event instanceof EntityDamageByEntityEvent)) return;
    EntityDamageByEntityEvent event_EE = (EntityDamageByEntityEvent)event;
    
    
    if ((event_EE.getDamager() instanceof Player))
    {
      Player damager = (Player)event_EE.getDamager();


      
      // if damager is hunter

        

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
