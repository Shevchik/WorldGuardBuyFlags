package wgbuyflags.commands;

import org.bukkit.command.CommandSender;

import wgbuyflags.utils.Validate;

public interface SubCommand {

	public void execute(CommandSender sender, String[] args) throws Validate.InvalidateException, Exception;

}
