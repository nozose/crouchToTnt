package nozose.crouchtotnt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class CrouchToTnt extends JavaPlugin implements Listener {

    boolean game = false;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equalsIgnoreCase("game")) {
                if (args.length > 0) {
                    if (args[0].equals("start")) {
                        if (game) p.sendMessage(ChatColor.RED + "게임이 이미 시작되어 있습니다.");
                        else if (!game) {
                            game = true;
                            Bukkit.broadcastMessage(ChatColor.GREEN + "게임이 시작되었습니다!");
                        }
                    } else if (args[0].equals("stop")) {
                        if (!game) p.sendMessage(ChatColor.RED + "게임이 이미 중지되어 있습니다.");
                        else if (game) {
                            game = false;
                            Bukkit.broadcastMessage(ChatColor.GREEN + "게임이 중단되었습니다!");
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "사용법 : /game start | stop");
                }
            }
        } else {
            if (label.equalsIgnoreCase("game")) {
                if (args.length > 0) {
                    if (args[0].equals("start")) {
                        if (game) System.out.println(ChatColor.GREEN + "게임이 이미 시작되어 있습니다.");
                        else if (!game) {
                            game = true;
                            Bukkit.broadcastMessage(ChatColor.GREEN + "게임이 시작되었습니다!");
                        }
                    } else if (args[0].equals("stop")) {
                        if (!game) System.out.println(ChatColor.RED + "게임이 이미 중지되어 있습니다.");
                        else if (game) {
                            game = false;
                            Bukkit.broadcastMessage(ChatColor.GREEN + "게임이 중단되었습니다!");
                        }
                    }
                } else {
                    System.out.println(ChatColor.RED + "사용법 : /game start | stop");
                }
            }
        }
        return super.onCommand(sender, command, label, args);
    }

    @EventHandler
    public void crouchEvent(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();
        if (p.isSneaking()) {
            if (game) {
                Location location = p.getLocation();
                TNTPrimed tnt = (TNTPrimed) w.spawnEntity(location, EntityType.PRIMED_TNT);
                tnt.setFuseTicks(20);
            }
        }
    }

    @Override
    public void onDisable() {

    }
}
