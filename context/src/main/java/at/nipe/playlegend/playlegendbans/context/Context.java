package at.nipe.playlegend.playlegendbans.context;

import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Main idea and codebase from
 * https://github.com/Alan-Gomes/mcspring-boot/blob/master/src/main/java/dev/alangomes/springspigot/util/ServerUtil.java
 */
@Singleton
@Component
public class Context {

  private final Map<Long, String> senderReferences = new ConcurrentHashMap<>();
  private final Map<Long, ContextProperties> contextProperties = new ConcurrentHashMap<>();

  private final ServerUtil serverUtil;

  @Inject
  public Context(ServerUtil serverUtil) {
    this.serverUtil = serverUtil;
  }

  public ContextProperties getContextProperties() {
    var threadId = Thread.currentThread().getId();
    return this.contextProperties.get(threadId);
  }

  public void setContextProperties(ContextProperties contextProperties) {
    var threadId = Thread.currentThread().getId();
    if (contextProperties == null) {
      this.contextProperties.remove(threadId);
      return;
    }
    this.contextProperties.put(threadId, contextProperties);
  }

  public Player getPlayer() {
    var sender = getSender();
    return sender instanceof Player ? (Player) sender : null;
  }

  public CommandSender getSender() {
    var senderRef = senderReferences.get(Thread.currentThread().getId());
    return serverUtil.getSenderFromId(senderRef);
  }

  void setSender(CommandSender sender) {
    var threadId = Thread.currentThread().getId();
    if (sender == null) {
      senderReferences.remove(threadId);
      return;
    }

    senderReferences.put(threadId, serverUtil.getSenderId(sender));
  }

  /**
   * Get the most unique id available for the player in the context. see {@link
   * ServerUtil#getSenderId(CommandSender)}
   *
   * @return the sender id
   */
  public String getSenderId() {
    return serverUtil.getSenderId(getSender());
  }

  /**
   * Run a {@param function} with a specific {@param sender} in the context
   *
   * @param sender The sender to be set at the context
   * @param function The code to be executed
   * @return the value returned by the function
   */
  public <T, S extends CommandSender> T runInContext(
      S sender, ContextProperties contextProperties, BiFunction<S, ContextProperties, T> function) {
    var oldSender = getSender();
    var oldContext = getContextProperties();
    setSender(sender);
    setContextProperties(contextProperties);
    try {
      return function.apply(sender, contextProperties);
    } finally {
      setSender(oldSender);
      setContextProperties(oldContext);
    }
  }

  public <T, S extends CommandSender> T runInContext(S sender, Function<S, T> function) {
    var oldSender = getSender();
    setSender(sender);
    try {
      return function.apply(sender);
    } finally {
      setSender(oldSender);
    }
  }
}
