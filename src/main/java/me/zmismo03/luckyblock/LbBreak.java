package me.zmismo03.luckyblock;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;


public class LbBreak implements Listener {

    private EasyLuckyblock main;
    public LbBreak(EasyLuckyblock main) {
        this.main = main;
    }

    public String getPrefix(){
        return this.main.getConfig().getString("messages.prefix");
    }

    @EventHandler
    public void onBreakSponge(BlockBreakEvent e){
        String mat = main.getConfig().getString("block");
        Player p = e.getPlayer();
            if (e.getBlock().getType() == Material.getMaterial(mat) && !e.isCancelled()) {
                if(p.hasPermission("easylb.break")) {
                    FileConfiguration fc = main.getConfig();
                    int count = 1;
                    double sum = 0;
                    int tmp = 0;
                    int dim = fc.getConfigurationSection("rewards").getKeys(false).size();
                    e.setDropItems(false);
                    double bound = 0;
                    for(int i = 1;i <= dim;i++){
                        bound = bound + fc.getDouble("rewards." + i + ".chance");
                    }
                    DecimalFormat decFormat = new DecimalFormat("#.##");
                    String boundFormatted = decFormat.format(bound);
                    boundFormatted = boundFormatted.replace(",",".");
                    bound = Double.parseDouble(boundFormatted);
                    String chanceFormatted = decFormat.format(ThreadLocalRandom.current().nextDouble(bound));
                    String replaceComma = chanceFormatted.replace(",", ".");
                    double chance = Double.parseDouble(replaceComma);
                    while (count <= dim && tmp == 0) {
                        sum = sum + fc.getDouble("rewards." + count + ".chance");
                        if (chance < sum) {
                            String command = PlaceholderAPI.setPlaceholders(p, main.getConfig().getString("rewards." + count + ".command"));
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                            if(main.getConfig().getString("rewards." + count + ".message").length()!=0){
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&',this.getPrefix()+""+this.main.getConfig().getString("rewards." + count + ".message")));
                            }
                            tmp++;
                        }
                        count++;
                    }
                }
                else{
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getPrefix()+""+this.main.getConfig().getString("messages.deny_break")));
                }
            }
    }
}

