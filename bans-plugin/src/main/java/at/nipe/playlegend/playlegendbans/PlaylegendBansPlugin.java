package at.nipe.playlegend.playlegendbans;

import dev.alangomes.springspigot.SpringSpigotBootstrapper;
import dev.alangomes.springspigot.SpringSpigotInitializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class PlaylegendBansPlugin extends JavaPlugin {

    public static final UUID SERVER_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private ConfigurableApplicationContext context;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        try {
            context = SpringSpigotBootstrapper.initialize(this, new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF).sources(BanApplication.class));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        context.close();
        context = null;
    }
}
