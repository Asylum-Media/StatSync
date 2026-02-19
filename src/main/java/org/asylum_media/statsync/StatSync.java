package org.asylum_media.statsync;

import org.asylum_media.statsync.integrations.punisherx.PunisherXAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

import org.asylum_media.statsync.punishments.PunishmentManager;


// Floodgate API
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

public final class StatSync extends JavaPlugin {

    private PunishmentManager punishmentManager;
    private final Map<UUID, Integer> sessionBeansStart = new HashMap<>();
    private String beansObjective;
    private PunisherXAdapter punisherXAdapter;

    @Override
    public void onEnable() {
        saveDefaultConfig();
// Core Punishment Manager
        this.punishmentManager = new PunishmentManager();

// Load PunisherX API adapter
        this.punisherXAdapter =
                org.asylum_media.statsync.integrations.punisherx.PunisherXAdapter.load(getServer());

        if (this.punisherXAdapter == null) {
            getLogger().warning("PunisherX API not available (PunisherX missing or API not registered).");
        } else {
            getLogger().info("PunisherX API loaded successfully.");
        }


        beansObjective = getConfig().getString("economy.beans-objective", "Beans");
        getLogger().info("Using Beans scoreboard objective: " + beansObjective);

        if (Bukkit.getPluginManager().getPlugin("Floodgate") != null) {
            getLogger().info("Floodgate detected.");
        } else {
            getLogger().warning("Floodgate not found. Platform data will be unavailable.");
        }

// Register session tracking + PunisherX probe listener
        getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                Player player = event.getPlayer();
                getLogger().info("JOIN EVENT FIRED for " + player.getName());
                getLogger().info("Adapter is " + (punisherXAdapter == null ? "NULL" : "NOT NULL"));
                getLogger().info("Calling fetchActivePunishments...");

                // Session Beans baseline
                int beans = getScoreboardValue(beansObjective, player);
                sessionBeansStart.put(player.getUniqueId(), beans);

                // Fetch punishments from PunisherX
                if (punisherXAdapter != null) {
                    punisherXAdapter.fetchActivePunishments(
                            player.getUniqueId(),
                            record -> {
                                punishmentManager.recordPunishment(record);
                                getLogger().info("Recorded punishment: "
                                        + record.getType()
                                        + " for " + player.getName());
                            }
                    );
                }
            }
        }, this);

        // Seed session baseline for already-online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            int beans = getScoreboardValue(beansObjective, player);
            sessionBeansStart.put(player.getUniqueId(), beans);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player staff)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        if (args.length < 2 || !args[0].equalsIgnoreCase("context")) {
            staff.sendMessage("Usage: /statsync context <player>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) {
            staff.sendMessage("Player not found or not online.");
            return true;
        }
       // Platform Detection
        String platform = "Java";

        try {
            UUID uuid = target.getUniqueId();
            FloodgateApi api = FloodgateApi.getInstance();

            if (api != null && api.isFloodgatePlayer(uuid)) {
                FloodgatePlayer fgPlayer = api.getPlayer(uuid);
                if (fgPlayer != null) {
                    platform = fgPlayer.getDeviceOs().toString();
                } else {
                    platform = "Bedrock (unknown device)";
                }
            }
        } catch (Throwable t) {
            getLogger().warning("Floodgate API query failed: " + t.getMessage());
        }
        // Snapshot Beans
        int beansNow = getScoreboardValue(beansObjective, target);
        int beansStart = sessionBeansStart.getOrDefault(
                target.getUniqueId(),
                beansNow
        );

        int beansDelta = beansNow - beansStart;

        // Write economy data to CommandPanels
        setCPData(staff, "ccm_target_beans_current", String.valueOf(beansNow));
        setCPData(staff, "ccm_target_beans_session", String.valueOf(beansDelta));

        setCPData(staff, "ccm_target_platform", platform);
        staff.sendMessage("§e[StatSync] §7Context synced for §f" + target.getName());
        return true;
    }
    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    private int getScoreboardValue(String objectiveName, Player player) {
        var manager = getServer().getScoreboardManager();
        if (manager == null) return 0;

        var board = manager.getMainScoreboard();
        var objective = board.getObjective(objectiveName);
        if (objective == null) return 0;

        return objective.getScore(player.getName()).getScore();
    }

    private void setCPData(Player staff, String key, String value) {
        String cmd = String.format(
                "pa data set %s %s %s",
                staff.getName(),
                key,
                value.replace(" ", "_")
        );

        getServer().dispatchCommand(
                getServer().getConsoleSender(),
                cmd
        );
    }

}
