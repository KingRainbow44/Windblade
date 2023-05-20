package io.grasscutter.windblade;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import emu.grasscutter.server.packet.send.PacketWindSeedClientNotify;

public interface Windy extends Library {
    Windy INSTANCE = Native.load("gc-windy", Windy.class);

    /**
     * Compiles a Lua script into Lua bytecode.
     *
     * @param script The Lua script to be compiled.
     * @param output The output buffer to write the bytecode to.
     * @return The length of the bytecode buffer.
     */
    int compile(String script, Pointer output);

    /**
     * Compiles a Lua script into Lua bytecode.
     *
     * @param script The Lua script to be compiled.
     * @return The bytecode buffer.
     */
    static byte[] compile(String script) {
        var buffer = new Memory(4096);
        var length = INSTANCE.compile(script, buffer);
        return length == -1 ? new byte[0] :
                buffer.getByteArray(0, length);
    }

    /**
     * Prepares a WindSeedClientNotify packet for sending.
     *
     * @param script The Lua script to be compiled.
     * @return The packet to be sent.
     */
    static PacketWindSeedClientNotify compilePacket(String script) {
        return new PacketWindSeedClientNotify(Windy.compile(script));
    }
}
