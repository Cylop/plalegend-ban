package at.nipe.playlegend.playlegendbans;

import at.nipe.playlegend.playlegendbans.commands.BanCommand;
import at.nipe.playlegend.playlegendbans.config.ConfigLoader;
import at.nipe.playlegend.playlegendbans.listener.PlayerJoinListener;
import com.google.inject.Injector;
import lombok.extern.java.Log;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@Log
public class PlaylegendBansPlugin extends JavaPlugin {

    private Injector injector;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.injector = BansInjector.createInjector(
                List.of(new PluginModule(this, new ConfigLoader(this).load()))
        );

        var playerJoinListener = injector.getInstance(PlayerJoinListener.class);

        this.getServer().getPluginManager().registerEvents(playerJoinListener, this);

        var banCommand = injector.getInstance(BanCommand.class);
        Objects.requireNonNull(this.getCommand("ban")).setExecutor(banCommand);
    }

    @Override
    public void onDisable() {
        this.injector = null;
    }
}
