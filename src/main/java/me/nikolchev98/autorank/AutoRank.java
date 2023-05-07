package me.nikolchev98.autorank;

import me.nikolchev98.autorank.listeners.VoteListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AutoRank extends JavaPlugin {
    private final Plugin plugin = this;
    private final Logger logger = plugin.getLogger();

    @Override
    public void onEnable() {

        Plugin plugin = this;
        Logger logger = plugin.getLogger();

        logger.info("Enabled AutoRank!");
        getServer().getPluginManager().registerEvents(new VoteListener(this), this);
    }

    @Override
    public void onDisable() {
        logger.info("Enabled AutoRank!");
        // Plugin shutdown logic
    }
}
