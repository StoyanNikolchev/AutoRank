package me.nikolchev98.autorank.listeners;

import com.bencodez.votingplugin.events.PlayerPostVoteEvent;
import com.bencodez.votingplugin.topvoter.TopVoter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class VoteListener implements Listener {
    private final Plugin plugin;

    public VoteListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerVote(PlayerPostVoteEvent e) {

        String playerName = e.getUser().getPlayerName();
        PermissionUser pexUser = PermissionsEx.getUser(playerName);

        //Stops if the player is Donator/Jabuti/Owner
        if (!pexUser.inGroup("Donator") && !pexUser.inGroup("Jabuti") && !pexUser.inGroup("Owner")) {

            int voteCount = e.getUser().getTotal(TopVoter.AllTime);
            setRankAsync(pexUser, voteCount);
        }
    }

    private void setRankAsync(PermissionUser pexUser, int voteCount) {
        new BukkitRunnable() {
            @Override
            public void run() {
                setRank(pexUser, voteCount);
            }
        }.runTask(plugin);
    }

    private void setRank(PermissionUser pexUser, int voteCount) {

            //Promotes to Voter
        if (voteCount >= 50 && voteCount <= 249 && !pexUser.inGroup("Voter")) {
            pexUser.addGroup("Voter");
            announcePromotion(pexUser.getName(), "Voter");

            //Promotes to Voter+
        } else if (voteCount >= 250 && voteCount <= 999 && !pexUser.inGroup("Voter+")) {
            pexUser.addGroup("Voter+");
            announcePromotion(pexUser.getName(), "Voter+");

            //Promotes to Voter++
        } else if (voteCount >= 1000 && voteCount <= 3599 && !pexUser.inGroup("Voter++")) {
            pexUser.addGroup("Voter++");
            announcePromotion(pexUser.getName(), "Voter++");

            //Promotes to Jabuti
        } else if (voteCount >= 3600 && !pexUser.inGroup("Jabuti")) {
            pexUser.addGroup("Jabuti");
            announcePromotion(pexUser.getName(), "Jabuti");
        }
    }

    private void announcePromotion(String name, String rank) {
        Bukkit.broadcastMessage(ChatColor.GOLD + name + ChatColor.DARK_GREEN + " was promoted to " + ChatColor.GOLD + rank + "!");
    }


}
