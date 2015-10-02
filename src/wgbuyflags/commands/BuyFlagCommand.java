package wgbuyflags.commands;

import java.util.Arrays;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import wgbuyflags.Config;
import wgbuyflags.utils.Utils;
import wgbuyflags.utils.Validate;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class BuyFlagCommand implements SubCommand {

	private static final Economy econ = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();

	private final Config config;
	public BuyFlagCommand(Config config) {
		this.config = config;
	}

	@Override
	public void execute(CommandSender sender, String[] args) throws Validate.InvalidateException, Exception {
		try {
			Validate.isTrue(sender.hasPermission("wgbuyflags.use"), ChatColor.RED + "You have no power here!");
			Validate.isTrue(args.length >= 3, ChatColor.RED + "Not enough args");
			Player player = Validate.cast(() -> (Player) sender, ChatColor.RED + "Only for players");

			RegionManager rm = Validate.notNull(WGBukkit.getRegionManager(player.getWorld()), ChatColor.RED + "Regions are disabled in this world");
			ProtectedRegion region = Validate.notNull(rm.getRegion(args[0].toLowerCase()), ChatColor.RED + "Region " + args[0].toLowerCase() + " doesn't exist");
			Validate.isTrue(region.isOwner(WGBukkit.getPlugin().wrapPlayer(player, true)), ChatColor.RED + "You are not an owner of region " + region.getId());

			Flag<?> flag = Validate.notNull(Utils.findFlag(args[1].toLowerCase()), ChatColor.RED + "Flag "+args[1].toLowerCase()+" doesn't exist");
			Double cost = Validate.notNull(config.getFlagCost(flag.getName()), ChatColor.RED + "This flag is not available for buying");
			Validate.isTrue(econ.getBalance(player) >= cost, ChatColor.RED + "You don't have enough money to buy this flag");

			econ.withdrawPlayer(player, cost);
			Utils.setFlag(region, flag, String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
			sender.sendMessage(ChatColor.YELLOW + "Flag set");
		} catch (CommandException | InvalidFlagFormat e) {
			sender.sendMessage(ChatColor.RED + "Unable to set flag: "+e.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}

}
