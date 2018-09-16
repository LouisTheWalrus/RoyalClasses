package royal.louise;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.*;
import org.bukkit.plugin.*;
import org.bukkit.event.player.*;

import org.bukkit.event.*;

import org.bukkit.potion.PotionEffectType;

/**
 * The main plugin class
 * @author louis
 */

public final class RoyalClasses extends JavaPlugin implements Listener
{
    public HashMap<String, String> classMap = new HashMap<>();  //Store player and current class
    ArrayList<String> classes = new ArrayList<>();              //Store list of classes
    Random r = new Random();
    public static WorldGuardPlugin wg;

    @Override
    public void onEnable() 
    {
        this.getLogger().info("enabled!");
        
        // Register each class listener here
        this.getServer().getPluginManager().registerEvents((Listener)new Vampire(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new Hunter(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        
        // Add all class names here.
        classes.add("vampire");
        classes.add("hunter");

        wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        // If anyone online add their class.
        for(Player online: Bukkit.getOnlinePlayers())
        {
        String classy= getConfig().getString(online.getName());
        classMap.put(online.getName(), classy);
        }

    }
    @Override
    
    //This is the command handler
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
        Player player = null;
        if (sender instanceof Player)   // Let only players issue commands
        {
            player = (Player) sender;
            if(commandLabel.equalsIgnoreCase("class"))  /* /class is the main command */
                {
                if(args.length==0)      // when player uses /class tell them their current class
                    {
                    if(classMap.containsKey(player.getName()))
                        {
                        String classs = classMap.get(player.getName());
                        RD.displayMessage("You are a member of the " + classs + " class", player);
                        return true;
                        }
                    else
                        {
                        RD.displayMessage("You don't have a class", player); 
                        return true;
                        }
                    }
                    else
                        {
                        if(args[0].equalsIgnoreCase("info"))    //class info 
                                {
                                    if(args.length==2)          //class info <classname>
                                    {
                                        try {
                                            displayFile(args[1] +".txt" , player);  // Show them the text file
                                            return true;                            // in RoyalClasses/classname.txt
                                        } catch (IOException ex) {
                                           RD.displayMessage(args[1] +".txt &cnot found", player);
                                           return false;
                                        }
                                    }
                                    
                                    try {
                                        displayFile("classinfo.txt", player);       // Show them RoyalClasses.classinfo.txt
                                        return true;
                                    } catch (IOException ex) {
                                        Logger.getLogger(RoyalClasses.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                else if(args[0].equalsIgnoreCase("reload"))         // Reload all classes
                                {
                                    if(player.hasPermission("sr.reload"))
                                    {
                                    for(Player online: Bukkit.getOnlinePlayers())
                                    {
                                    String classy= getConfig().getString(online.getName());
                                    classMap.put(online.getName(), classy);
                                    RD.displayMessage("Welcome to the &b" + classy + " &7class", online);
                                        }
                                    }
                                }
                                else if(classes.contains(args[0]))                  // /class <classname>
                                {                                                   // gives player the chosen class
                                classMap.put(player.getName(), args[0]);            // save in local memory
                                RD.displayMessage("Welcome to the " + args[0] + " class", player);
                                getConfig().set(player.getName(), args[0]);
                                saveConfig();                                       // save in the data file
                                }
                                else
                                {
                                    RD.displayMessage("Not a class. Available classes: &ahunter, vampire", player);
                                }
                                return true;
                            }
                        }
		}
        return false;

        }
        
    
        // When someone login load their class from file
        @EventHandler
        public void onPlayerLogin(PlayerLoginEvent evt)
        {
            if(getConfig().get(evt.getPlayer().getName())!=null)
            {
                String classy= getConfig().getString(evt.getPlayer().getName());
                classMap.put(evt.getPlayer().getName(), classy);
                RD.displayMessage("Welcome to the &b" + classy + " &7class", evt.getPlayer());
            }
        }
            
        /**
         * Check what class the player is by playername.
         * @param player The player to check the class for.
         * @param className The class to check for.
         * @return true, if player has the class, false otherwise.
         */
        public boolean isClass(String player, String className)
        {
            try{
            if(!Bukkit.getPlayer(player).getWorld().getName().equals("events"))
            {
            if(classMap.containsKey(player))
            {
                return(classMap.get(player).equalsIgnoreCase(className));
            }
            return false;
            }
            else
            {
               
            return false;
            }
            }catch(Exception e)
            {
                return false;
            }
        }
        
         /**
         * Check what player the class is.
         * @param playerr The player to check the class for.
         * @param className The class to check for.
         * @return true, if player has the class, false otherwise.
         */
        public boolean isClass(Player playerr, String className)
        {
                        if(!playerr.getWorld().getName().equals("events"))
            {
            String player = playerr.getName();
                        if(classMap.containsKey(player))
            {
                return(classMap.get(player).equalsIgnoreCase(className));
            }
            return false;
        }
                        else
                        {
                        return false;
                        }
        }

        	public int getCurrentTime()
	{
		Calendar calendar = new GregorianCalendar();

		int hour = calendar.get(Calendar.HOUR) * 3600;
		int minute = calendar.get(Calendar.MINUTE) * 60;
		int second = calendar.get(Calendar.SECOND);

		return hour + minute + second;
	}
                



    /*
    * Display a text file line by line to the player            
    */
    private void displayFile(String filename, Player p) throws FileNotFoundException, IOException 
    {
        File fi = new File(getDataFolder().getPath()+File.separator+filename);
        FileReader fr = new FileReader(fi);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while(line!=null)
        {
            RD.displayMessage(line, p);
            line = br.readLine();
        }
    }
    
    /*
    * ???
    */
    public static boolean isShort(String s) 
    {
        try
        {
            Short.parseShort(s);
        } catch (NumberFormatException e)
        {
            return false;
        }
        return true;
}

}
