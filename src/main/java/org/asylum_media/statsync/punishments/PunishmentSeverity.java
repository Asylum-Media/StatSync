package org.asylum_media.statsync.punishments;

public final class PunishmentSeverity {

    private PunishmentSeverity() {}

    public static int defaultWeight(PunishmentType type) {
        return switch (type) {
            case WARN -> 1;
            case MUTE -> 2;
            case JAIL -> 3;
            case KICK -> 4;
            case TEMP_BAN -> 6;
            case PERM_BAN -> 10;
        };
    }
}
