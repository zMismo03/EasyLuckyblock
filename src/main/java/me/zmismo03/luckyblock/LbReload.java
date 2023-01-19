package me.zmismo03.luckyblock;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LbReload implements CommandExecutor {

    private EasyLuckyblock main;
    public LbReload(EasyLuckyblock main) {
        this.main = main;
    }

    public String getPrefix(){
        return this.main.getConfig().getString("messages.prefix");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("easylb.reload")){
            this.main.reloadConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getPrefix()+""+this.main.getConfig().getString("messages.reload")));
            return true;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getPrefix()+""+this.main.getConfig().getString("messages.no_permission")));
        return false;
    }
}
