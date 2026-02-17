package org.asylum_media.statsync.integrations.punisherx;

import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import pl.syntaxdevteam.punisher.api.PunisherXApi;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

import org.asylum_media.statsync.punishments.PunishmentRecord;
import org.asylum_media.statsync.punishments.PunishmentType;
import org.asylum_media.statsync.punishments.PunishmentSeverity;

public final class PunisherXAdapter {

    private final PunisherXApi api;

    private PunisherXAdapter(PunisherXApi api) {
        this.api = api;
    }

    public static PunisherXAdapter load(Server server) {
        PunisherXApi api = server.getServicesManager().load(PunisherXApi.class);
        return (api == null) ? null : new PunisherXAdapter(api);
    }

    public void fetchActivePunishments(UUID playerUuid, Consumer<PunishmentRecord> consumer) {

        api.getActivePunishments(playerUuid.toString(), "ALL")
                .thenAccept(punishments -> {

                    punishments.forEach(p -> {

                        PunishmentType type = switch (p.getType().toUpperCase()) {
                            case "MUTE" -> PunishmentType.MUTE;
                            case "JAIL" -> PunishmentType.JAIL;
                            case "BAN" -> PunishmentType.PERM_BAN;
                            default -> PunishmentType.WARN;
                        };

                        long end = p.getEnd();
                        Instant expiresAt = (end > 0)
                                ? Instant.ofEpochMilli(end)
                                : null;

                        PunishmentRecord record = new PunishmentRecord(
                                UUID.randomUUID(),
                                playerUuid,
                                type,
                                PunishmentSeverity.defaultWeight(type),
                                "PunisherX",
                                p.getReason(),
                                p.getOperator(),
                                Instant.now(),
                                expiresAt,
                                true
                        );

                        consumer.accept(record);
                    });

                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });
    }
}
