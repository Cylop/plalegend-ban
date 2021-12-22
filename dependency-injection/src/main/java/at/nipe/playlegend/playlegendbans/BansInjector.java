package at.nipe.playlegend.playlegendbans;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class BansInjector {
  public static Injector createInjector() {
    return createInjector(null, null);
  }

  public static Injector createInjector(@Nullable List<Module> beforeModules) {
    return createInjector(beforeModules, null);
  }

  public static Injector createInjector(
      @Nullable List<Module> beforeModules, @Nullable List<Module> afterModules) {
    if (beforeModules == null) beforeModules = new ArrayList<>();
    if (afterModules == null) afterModules = new ArrayList<>();

    var modules = List.of(new ComponentModule(), new PersistenceModule());
    var allModules =
        Stream.of(beforeModules, modules, afterModules).flatMap(Collection::stream).toList();
    return Guice.createInjector(allModules.toArray(Module[]::new));
  }
}
