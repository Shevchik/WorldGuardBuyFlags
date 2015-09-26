package wgbuyflags;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	private WGBuyFlags plugin;
	public Config(WGBuyFlags plugin) {
		this.plugin = plugin;
	}

	private HashMap<String, Double> flagsCost = new HashMap<>();

	public Double getFlagCost(String flag) {
		return flagsCost.get(flag.toLowerCase());
	}

	public Map<String, Double> getFlagCosts() {
		return Collections.unmodifiableMap(flagsCost);
	}

	public void loadConfig() {
		File configfile = new File(plugin.getDataFolder(), "config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configfile);
		flagsCost.clear();
		ConfigurationSection section = config.getConfigurationSection("cost");
		if (section != null) {
			for (String flag : section.getKeys(false)) {
				flagsCost.put(flag.toLowerCase(), section.getDouble(flag));
			}
		}
		saveConfig();
	}

	private void saveConfig() {
		File configfile = new File(plugin.getDataFolder(), "config.yml");
		YamlConfiguration config = new YamlConfiguration();
		ConfigurationSection section = config.createSection("cost");
		flagsCost.forEach((flag, cost) -> section.set(flag, cost));
		try {
			config.save(configfile);
		} catch (IOException e) {
		}
	}

}
