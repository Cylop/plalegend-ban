package at.nipe.playlegend.playlegendbans.localization;

import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import lombok.Getter;
import lombok.extern.java.Log;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.ResourceBundle;

@Log
@Singleton
@Component
public class LocalizationContainer {

  private static final String LOCALIZATION_FILE_NAMES = "localization";

  private final JavaPlugin plugin;

  @Getter private ResourceBundle resourceBundle;

  @Inject
  public LocalizationContainer(JavaPlugin plugin) {
    this.plugin = plugin;
    try {
      this.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void load() throws IOException, NullPointerException {
    this.copyDefaultLocalization();

    var configFolder = new File(this.plugin.getDataFolder(), "config");

    URL[] urls = new URL[0];
    try {
      urls = new URL[] {configFolder.toURI().toURL()};
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    var loader = new URLClassLoader(urls);

    this.resourceBundle =
        ResourceBundle.getBundle(LOCALIZATION_FILE_NAMES, Locale.getDefault(), loader);
  }

  private void copyDefaultLocalization() throws IOException, NullPointerException {
    Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

    var configFolder = new File(this.plugin.getDataFolder(), "config");

    if (!configFolder.exists()) {
      configFolder.mkdirs();
    }

    var listedFiles =
        configFolder.listFiles(
            (dir, name) ->
                name.startsWith(LOCALIZATION_FILE_NAMES) && name.endsWith(".properties"));
    if (listedFiles == null || listedFiles.length == 0) {
      var defaultLocalizationFile =
          Thread.currentThread()
              .getContextClassLoader()
              .getResourceAsStream(LOCALIZATION_FILE_NAMES + ".properties");
      if (defaultLocalizationFile == null) {
        throw new NullPointerException(
            "Default localization.properties file is not present. Please contact the developer");
      }
      Files.copy(
          defaultLocalizationFile,
          Paths.get(configFolder.toURI().resolve(LOCALIZATION_FILE_NAMES + ".properties")),
          StandardCopyOption.REPLACE_EXISTING);
    }
  }
}
