package dcl.louise;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Cooldown
{
	private HashMap<String, Long> cd = new HashMap<String, Long>();
	private int time;

	public Cooldown(int time)
	{
		this.time = time;
	}

	public boolean isOnCooldown(Player p)
	{
		if (cd.containsKey(p.getName()))
		{
			if (System.currentTimeMillis() < cd.get(p.getName()))
			{
				return true;
			}
		}
		return false;
	}
	public void addCooldown(Player p)
	{
			cd.put(p.getName(), System.currentTimeMillis() + time);
	}
        public void resetCooldown(Player p)
        {
            cd.remove(p.getName());
        }
	public int getRemainingTime(Player p)
	{
		return (int) ((cd.get(p.getName()) - System.currentTimeMillis())/1000);
	}
}
