package at.nipe.playlegend.playlegendbans.listener;

import at.nipe.playlegend.playlegendbans.PlaylegendBansPlugin;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

public class PlayerJoinListenerTest {

  private ServerMock server;
  private PlaylegendBansPlugin plugin;

  @AfterAll
  static void tearDown() {
    MockBukkit.unmock();
  }

  @BeforeEach
  public void setUp() {
    server = MockBukkit.mock();
    plugin = MockBukkit.load(PlaylegendBansPlugin.class);
  }
}
