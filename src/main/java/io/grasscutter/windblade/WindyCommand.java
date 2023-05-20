package io.grasscutter.windblade;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.Command.TargetRequirement;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.io.File;
import java.util.List;

@Command(label = "windy", aliases = {"lua", "windblade"},
        usage = "/windy <file name>", targetRequirement = TargetRequirement.ONLINE,
        permissionTargeted = "windblade.command")
public final class WindyCommand implements CommandHandler {
    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, "Player not found.");
            return;
        }

        // Check for a file name.
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, "Please specify a file name.");
            return;
        }

        // Get the file name.
        var fileName = String.join(" ", args);
        // Get the file path.
        var file = new File(Windblade.getInstance().getDataFolder(), fileName);

        // Check if the file exists.
        if (!file.exists()) {
            CommandHandler.sendMessage(sender, "File not found.");
            return;
        }

        // Execute the script.
        Windblade.windy(targetPlayer, file);
    }
}
