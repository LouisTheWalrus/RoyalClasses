package royal.louise;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class RD
{
  public static void displayMessage(String s, Player p)
  {
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DC&8]&7 " + 
      s + " lol"));
  }
  
  public static void hitEntityMessage(String s, String s2, LivingEntity e, Player g)
  {
      if(e instanceof Player)
      {
          Player p = (Player) e;
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DC&8]&7 " + 
      s + " lol"));  
      }
      else
      {
              g.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DC&8]&7 " + 
      s2 + " lol"));
      }
  }
  
  public static void bcastMessage(String s, Player p)
  {
    for (Player x : getPlayersWithin(p, 20)) {
      displayMessage(s, x);
    }
  }
  

  public static List<Player> getPlayersWithin(Player player, int distance)
  {
    List<Player> res = new ArrayList();
    int d2 = distance * distance;
    for (Player p: Bukkit.getOnlinePlayers())
    {
      if ((p.getWorld() == player.getWorld()) && 
        (p.getLocation().distanceSquared(player.getLocation()) <= d2)) {
        res.add(p);
      }
    }
    return res;
  }
  public static List<Player> getPlayersWithin(Location loc, int distance)
  {
    List<Player> res = new ArrayList();
    int d2 = distance * distance;
    for (Player p: Bukkit.getOnlinePlayers())
    {
      if ((p.getWorld() == loc.getWorld()) && 
        (p.getLocation().distanceSquared(loc) <= d2)) {
        res.add(p);
      }
    }
    return res;
  }
  
  public static Player getNearPlayer(Location l, int distance)
  {
    Player rp = null;
    int d2 = distance * distance;

    for (Player p: Bukkit.getOnlinePlayers())
    {

      if ((p.getWorld() == l.getWorld()) && 
        (p.getLocation().distanceSquared(l) <= d2))
      {
        rp = p;
        return rp;
      }
    }
    return rp;
  }
    public static List<Player> getPlayersWithin(Block b, int distance)
  {
    List<Player> res = new ArrayList();
    int d2 = distance * distance;

    for (Player p: Bukkit.getOnlinePlayers())
    {

      if ((p.getWorld() == b.getWorld()) && 
        (p.getLocation().distanceSquared(b.getLocation()) <= d2)) {
        res.add(p);
      }
    }
    return res;
  }

    static Iterable<LivingEntity> getLivingEntitiesWithin(Block b, int distance) 
    {
    List<LivingEntity> res = new ArrayList();
    int d2 = distance * distance;

    for (LivingEntity p: b.getWorld().getEntitiesByClass(LivingEntity.class))
    {

      if ((p.getWorld() == b.getWorld()) && 
        (p.getLocation().distanceSquared(b.getLocation()) <= d2)) {
        res.add(p);
      }
    }
    return res;
    }

    static boolean PvPDenied(LivingEntity le) 
    {
            ApplicableRegionSet set = RoyalClasses.wg.getRegionManager(le.getWorld()).getApplicableRegions(le.getLocation());
            
                    return set.queryState(null, DefaultFlag.PVP)==StateFlag.State.DENY;
    }
    
    static LivingEntity getTarget(Player player)
        {
            World plworld = player.getWorld();
            BlockVector pt = BukkitUtil.toVector(player.getLocation().getBlock());
            LivingEntity target = null;
            Player target2 = null;
      
            double targetDistanceSquared = 0;
            final double radiusSquared = 1;
            final Vector l = player.getEyeLocation().toVector(), n = player.getLocation().getDirection().normalize();
            final double cos45 = Math.cos(Math.PI / 4);
      
            for (final LivingEntity other : player.getWorld().getEntitiesByClass(LivingEntity.class))
            {
                if (other == player)
                {
                continue;
                }
              
                if (target == null || targetDistanceSquared > other.getLocation().distanceSquared(player.getLocation()))
                    {
                    final Vector t = other.getLocation().add(0, 1, 0).toVector().subtract(l);
                    if (n.clone().crossProduct(t).lengthSquared() < radiusSquared && t.normalize().dot(n) >= cos45)
                        {
                        target = other;
                        targetDistanceSquared = target.getLocation().distanceSquared(player.getLocation());
                        }
                    }
            }
      
                if (target != null)
                {
  
                                Location ploc = player.getLocation();
                                Location tloc = target.getLocation();
                                if (ploc.distance(tloc) > 10)
                                {
                                    RD.displayMessage("Target out of range", player);
                                    return null;
                                }
                                else
                                {
                                    return target;
                                }

                            
                        
                }
                RD.displayMessage("No target", player);
                return null;
        }
    
}
