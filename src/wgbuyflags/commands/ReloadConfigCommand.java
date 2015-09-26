package wgbuyflags.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import wgbuyflags.Config;
import wgbuyflags.utils.Validate;

public class ReloadConfigCommand implements SubCommand {

	private final Config config;
	public ReloadConfigCommand(Config config) {
		this.config = config;
	}

	@Override
	public void execute(CommandSender sender, String[] args) throws Validate.InvalidateException, Exception {
		Validate.isTrue(sender.hasPermission("wgbuyflags.admin"), ChatColor.RED + "You have no power here!");
		config.loadConfig();
		sender.sendMessage(ChatColor.YELLOW + "Config reloaded");
	}

}
