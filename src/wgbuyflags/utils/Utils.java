package wgbuyflags.utils;

import java.util.Arrays;
import java.util.HashSet;

import org.bukkit.Bukkit;

import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Utils {

	public static Flag<?> findFlag(String name) {
		return Arrays.asList(DefaultFlag.getFlags()).stream().filter(flag -> flag.getName().equalsIgnoreCase(name)).findAny().orElse(null);
	}

	private static final HashSet<Character> valueFlags = new HashSet<Character>(Arrays.asList(new Character[] {'g'}));
	public static <T> void setFlag(ProtectedRegion region, Flag<T> flag, String value) throws CommandException, InvalidFlagFormat {
		CommandContext ccontext = new CommandContext("rg "+value, valueFlags);
		region.setFlag(flag, flag.parseInput(WGBukkit.getPlugin(), Bukkit.getConsoleSender(), ccontext.getRemainingString(0)));
		if (ccontext.hasFlag('g')) {
			String group = ccontext.getFlag('g');
			RegionGroupFlag groupFlag = flag.getRegionGroupFlag();
			if (groupFlag == null) {
				return;
			}
			RegionGroup groupValue = groupFlag.parseInput(WGBukkit.getPlugin(), Bukkit.getConsoleSender(), group);
			if (groupValue == groupFlag.getDefault()) {
				region.setFlag(groupFlag, null);
			} else {
				region.setFlag(groupFlag, groupValue);
			}
		}
	}

}
