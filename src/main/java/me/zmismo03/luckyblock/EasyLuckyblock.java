package me.zmismo03.luckyblock;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class EasyLuckyblock extends JavaPlugin implements CommandExecutor{

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new LbBreak(this),this);
        getCommand("easyreload").setExecutor(new LbReload(this));
        getCommand("easygive").setExecutor(new LbGive(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
