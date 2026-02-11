package org.asylum_media.statsync.integrations.punisherx;

import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import pl.syntaxdevteam.punisher.api.PunisherXApi;
import java.util.UUID;

public final class PunisherXAdapter {

    private final PunisherXApi api;

    private PunisherXAdapter(PunisherXApi api) {
        this.api = api;
    }

    public static PunisherXAdapter load(Server server) {
        PunisherXApi api = server.getServicesManager().load(PunisherXApi.class);
        return (api == null) ? null : new PunisherXAdapter(api);
    }

    public PunisherXApi api() {
        return api;
    }
    public void logLastTenPunishments(UUID playerUuid) {
        api.getLastTenActivePunishments(playerUuid.toString())
                .thenAccept(punishments -> {
                    System.out.println("[StatSync][PunisherX] Active punishments: " + punishments.size());
                    punishments.forEach(p -> {
                        System.out.println(
                                "Type=" + p.getType()
                                        + " Reason=" + p.getReason()
                                        + " Operator=" + p.getOperator()
                                        + " End=" + p.getEnd()
                        );
                    });
                })
                .exceptionally(ex -> {
                    System.err.println("[StatSync][PunisherX] API query failed: " + ex.getMessage());
                    return null;
                });
    }

    public void logActivePunishments(UUID playerUuid) {
        System.out.println("[StatSync][PunisherX] Querying active punishments...");

        api.getActivePunishments(playerUuid.toString(), "MUTE")
                .thenAccept(punishments -> {
                    System.out.println("[StatSync][PunisherX] Active punishments found: " + punishments.size());

                    punishments.forEach(p -> {
                        System.out.println(
                                "Type=" + p.getType()
                                        + " Reason=" + p.getReason()
                                        + " Operator=" + p.getOperator()
                                        + " End=" + p.getEnd()
                        );
                    });
                })
                .exceptionally(ex -> {
                    System.err.println("[StatSync][PunisherX] API query failed:");
                    ex.printStackTrace();
                    return null;
                });
    }

}
