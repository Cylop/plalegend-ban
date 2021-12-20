package at.nipe.playlegend.playlegendbans.commands;

import at.nipe.playlegend.playlegendbans.PlaylegendBansPlugin;
import at.nipe.playlegend.playlegendbans.entities.Ban;
import at.nipe.playlegend.playlegendbans.entities.User;
import at.nipe.playlegend.playlegendbans.exceptions.UnknownDurationUnitException;
import at.nipe.playlegend.playlegendbans.parser.durationparser.DurationParser;
import at.nipe.playlegend.playlegendbans.parser.durationparser.DurationPossibilities;
import at.nipe.playlegend.playlegendbans.services.bans.BanService;
import at.nipe.playlegend.playlegendbans.services.users.UserService;
import dev.alangomes.springspigot.context.Context;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Date;
import java.util.concurrent.Callable;

@Log
//@Component
//@CommandLine.Command(name = "legendban", description = "Bans player defined in arguments (index0) with duration (index1) and message (index 2..n)")
public class BanCommand implements Callable<String> {

    @CommandLine.Parameters(index = "0", description = "The player that should be banned")
    private String player;

    @CommandLine.Parameters(index = "1", defaultValue = "999y", description = "The duration for ex.: 4w10d3h42s")
    private String duration;

    @CommandLine.Parameters(index = "2", arity = "1..*", defaultValue = "You have been banned", description = "Ban message or reason")
    private String message;

    private final Context context;

    private final UserService userService;

    private final BanService banService;

    @Autowired
    public BanCommand(Context context, UserService userService, BanService banService) {
        this.context = context;
        this.userService = userService;
        this.banService = banService;
    }

    @Override
    public String call() {
        CommandSender sender = context.getSender();
        var senderUuid = PlaylegendBansPlugin.SERVER_UUID;

        if(context.getPlayer() != null) {
            senderUuid = context.getPlayer().getUniqueId();
        }

        final var maybeUser = this.userService.findById(senderUuid);
        if (maybeUser.isEmpty()) {
           return ChatColor.RED + "Something went wrong. Please relog";
        }

        final var user = maybeUser.get();
        final var toBeBannedPlayer = Bukkit.getPlayerExact(player);
        if(player == null || toBeBannedPlayer == null) {
            return ChatColor.RED + "You must specify who you want to ban at index 0: Use /ban <player>";
        }

        final var maybeToBeBannedUser = this.userService.findById(toBeBannedPlayer.getUniqueId());
        if(maybeToBeBannedUser.isEmpty()) {
            return ChatColor.RED + "Something went wrong. The user you are trying to ban must relog";
        }

        final var toBeBannedUser = maybeToBeBannedUser.get();

        Date until;

        try {
            until = new DurationParser().parse(this.duration);
        } catch (UnknownDurationUnitException ex) {
            return "Wrong unit as duration provided. Allowed units are: " + String.join(", ", DurationPossibilities.getAllowedIdentifiers());
        }
        final var ban = Ban.builder()
                .banned(toBeBannedUser)
                .bannedBy(user)
                .reason(this.message)
                .until(until)
                .active(true)
                .build();

        toBeBannedPlayer.kickPlayer("");
        this.banService.save(ban);
        return ChatColor.GREEN + "Successfully banned player " + player;
    }
}
