package at.nipe.playlegend.playlegendbans;

import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import com.google.inject.AbstractModule;

public class ComponentModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new ComponentResolution("at.nipe.playlegend", Component.class));
  }
}
