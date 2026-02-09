package org.asylum_media.statsync.integrations.punisherx;

import org.bukkit.Server;
import pl.syntaxdevteam.punisher.api.PunisherXApi;

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
}
