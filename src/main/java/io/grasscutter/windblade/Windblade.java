package io.grasscutter.windblade;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.game.world.World;
import emu.grasscutter.plugin.Plugin;
import emu.grasscutter.server.packet.send.PacketWindSeedClientNotify;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Windblade extends Plugin {
    private static final String FAIL = "print(\"Failed to read script.\")";

    @Getter private static Windblade instance;

    /**
     * Attempts to read a Lua script from a file.
     *
     * @param file The file to read the script from.
     * @return The Lua script.
     */
    public static String scriptFromFile(File file) {
        return Windblade.scriptFromFile(file.toPath());
    }

    /**
     * Attempts to read a Lua script from a file.
     *
     * @param file The file path to read the script from.
     * @return The Lua script.
     */
    public static String scriptFromFile(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException ignored) {
            return Windblade.FAIL;
        }
    }

    /**
     * Reads a Lua script from a file.
     * Compiles a Lua script into Lua bytecode.
     * Prepares a WindSeedClientNotify packet for sending.
     *
     * @param file The file to read the script from.
     * @return The packet to be sent.
     */
    public static PacketWindSeedClientNotify compileScriptFromFile(File file) {
        return Windblade.compileScriptFromFile(file.toPath());
    }

    /**
     * Reads a Lua script from a file.
     * Compiles a Lua script into Lua bytecode.
     * Prepares a WindSeedClientNotify packet for sending.
     *
     * @param file The file path to read the script from.
     * @return The packet to be sent.
     */
    public static PacketWindSeedClientNotify compileScriptFromFile(Path file) {
        return Windy.compilePacket(Windblade.scriptFromFile(file));
    }

    /**
     * Executes a Lua script on a player's client.
     *
     * @param player The player to execute the script on.
     * @param script The Lua script to execute.
     */
    public static void windy(Player player, String script) {
        player.sendPacket(Windy.compilePacket(script));
    }

    /**
     * Executes a Lua script on all players' clients.
     *
     * @param world The world containing players to execute the script on.
     * @param script The Lua script to execute.
     */
    public static void windy(World world, String script) {
        world.broadcastPacket(Windy.compilePacket(script));
    }

    /**
     * Executes a Lua script on all players' clients in a scene.
     *
     * @param scene The scene containing players to execute the script on.
     * @param script The Lua script to execute.
     */
    public static void windy(Scene scene, String script) {
        scene.broadcastPacket(Windy.compilePacket(script));
    }

    /**
     * Executes a Lua script on a player's client.
     *
     * @param player The player to execute the script on.
     * @param script The Lua script to execute.
     */
    public static void windy(Player player, File script) {
        player.sendPacket(Windy.compilePacket(
                Windblade.scriptFromFile(script)));
    }

    @Override
    public void onLoad() {
        Windblade.instance = this;
    }

    @Override
    public void onEnable() {
        this.getHandle().registerCommand(new WindyCommand());
    }
}
