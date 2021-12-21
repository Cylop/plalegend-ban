package at.nipe.playlegend.playlegendbans;

import at.nipe.playlegend.playlegendbans.entities.Ban;
import at.nipe.playlegend.playlegendbans.entities.User;
import at.nipe.playlegend.playlegendbans.services.bans.BanService;
import at.nipe.playlegend.playlegendbans.services.users.UserService;
import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import at.nipe.playlegend.playlegendbans.shared.utils.ServerUtil;
import lombok.extern.java.Log;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;

@Log
@Component
public class BanFacade {

    private final UserService userService;
    private final BanService banService;

    @Inject
    public BanFacade(UserService userService, BanService banService) {
        this.userService = userService;
        this.banService = banService;
    }

    public Ban banPlayer(Player player, CommandSender sender, Date until, String message) throws SQLException {
        User toBeBanned;
        User banner;

        try {
            var userOpt = this.userService.findById(player.getUniqueId());
            if(userOpt.isEmpty()) {
                sender.sendMessage(ChatColor.RED + "The player has no User profile");
                throw new IllegalArgumentException("The player has no User profile");
            }
            toBeBanned = userOpt.get();

            var bannerOpt = this.userService.findById(sender instanceof Player ? ((Player)sender).getUniqueId() : ServerUtil.SERVER_UUID);
            if(bannerOpt.isEmpty()) {
                sender.sendMessage(ChatColor.RED + "The player has no User profile");
                throw new IllegalArgumentException("The player has no User profile");
            }
            banner = userOpt.get();

        } catch ( SQLException e) {
            log.log(Level.WARNING, "Whilst trying to ban a user an exception occurred", e);
            throw e;
        }

        final var ban = Ban.builder()
                .banned(toBeBanned)
                .bannedBy(banner)
                .reason(message)
                .until(until)
                .active(true)
                .build();

        return this.banService.createIfNotExists(ban);
    }
}
