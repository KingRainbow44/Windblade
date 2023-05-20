package io.grasscutter.windblade;

import emu.grasscutter.game.player.Player;

/* Utilities for interacting with players. */
public interface WindyPlayer {
    /**
     * Changes the UID text in the bottom right for a player.
     *
     * @param player The player to change the UID for.
     * @param text The text to change the UID to.
     */
    static void changeUid(Player player, String text) {
        Windblade.windy(player,
                """
                        local uid = CS.UnityEngine.GameObject.Find("/BetaWatermarkCanvas(Clone)/Panel/TxtUID")
                        uid:GetComponent("Text").text = "%s"
                        """.formatted(text));
    }
}
