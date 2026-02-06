package org.asylum_media.statsync.punishments;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PunishmentManager {

    private final Map<UUID, List<PunishmentRecord>> punishments = new ConcurrentHashMap<>();

    public void recordPunishment(PunishmentRecord record) {
        punishments
                .computeIfAbsent(record.getPlayerUuid(), k -> new ArrayList<>())
                .add(record);
    }

    public List<PunishmentRecord> getPunishments(UUID playerUuid) {
        return punishments.getOrDefault(playerUuid, List.of());
    }

    public long getActivePunishmentCount(UUID playerUuid) {
        return getPunishments(playerUuid).stream()
                .filter(PunishmentRecord::isActive)
                .count();
    }

    public int getTotalActiveSeverity(UUID playerUuid) {
        return getPunishments(playerUuid).stream()
                .filter(PunishmentRecord::isActive)
                .mapToInt(PunishmentRecord::getSeverityWeight)
                .sum();
    }
}
