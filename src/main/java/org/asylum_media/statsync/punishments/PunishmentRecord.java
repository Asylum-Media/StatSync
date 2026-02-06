package org.asylum_media.statsync.punishments;

import java.time.Instant;
import java.util.UUID;

public class PunishmentRecord {

    private final UUID punishmentId;
    private final UUID playerUuid;

    private final PunishmentType type;
    private final int severityWeight;

    private final String sourcePlugin; // e.g. "PunisherX"
    private final String reason;
    private final String issuedBy; // UUID string, "CONSOLE", "SYSTEM"

    private final Instant issuedAt;
    private final Instant expiresAt; // nullable

    private boolean active;

    public PunishmentRecord(
            UUID punishmentId,
            UUID playerUuid,
            PunishmentType type,
            int severityWeight,
            String sourcePlugin,
            String reason,
            String issuedBy,
            Instant issuedAt,
            Instant expiresAt,
            boolean active
    ) {
        this.punishmentId = punishmentId;
        this.playerUuid = playerUuid;
        this.type = type;
        this.severityWeight = severityWeight;
        this.sourcePlugin = sourcePlugin;
        this.reason = reason;
        this.issuedBy = issuedBy;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.active = active;
    }

    public UUID getPunishmentId() {
        return punishmentId;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public PunishmentType getType() {
        return type;
    }

    public int getSeverityWeight() {
        return severityWeight;
    }

    public String getSourcePlugin() {
        return sourcePlugin;
    }

    public String getReason() {
        return reason;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
