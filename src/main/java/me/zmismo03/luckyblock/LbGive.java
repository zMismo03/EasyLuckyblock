package me.zmismo03.luckyblock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class LbGive implements CommandExecutor {

    private EasyLuckyblock main;
    public LbGive(EasyLuckyblock main) {
        this.main = main;
    }

    public String getPrefix(){
        return this.main.getConfig().getString("messages.prefix");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("easylb.give")){
            if(args.length>=2&&args.length<3){
                Player target = Bukkit.getPlayerExact(args[0]);
                if(target == null){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getPrefix()+""+this.main.getConfig().getString("messages.player_not_online")));
                    return false;
                }
                Material mat = Material.getMaterial(this.main.getConfig().getString("block"));
                try{
                    int amount = Integer.parseInt(args[1]);
                    ItemStack item = new ItemStack(mat,amount);
                    ItemMeta meta = item.getItemMeta();
                    String name = this.main.getConfig().getString("name").replace('&','§');
                    meta.setDisplayName(name);
                    item.setItemMeta(meta);
                    target.getInventory().addItem(item);
                    String message_format = this.main.getConfig().getString("messages.received").replaceAll("%amount%",""+amount);
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&',getPrefix()+""+message_format));
                    return true;
                } catch(Exception e){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getPrefix()+""+this.main.getConfig().getString("messages.invalid_amount")));
                    return false;
                }
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getPrefix()+""+this.main.getConfig().getString("messages.give_usage")));
            return false;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',getPrefix()+""+this.main.getConfig().getString("messages.no_permission")));
        return false;
    }
}
