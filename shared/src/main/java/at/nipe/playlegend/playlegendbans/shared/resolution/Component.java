package at.nipe.playlegend.playlegendbans.shared.resolution;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to provide spring like dependency bean auto registration
 *
 * @author NoSleep - Nipe
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Component {
  boolean singleton() default false;
}
