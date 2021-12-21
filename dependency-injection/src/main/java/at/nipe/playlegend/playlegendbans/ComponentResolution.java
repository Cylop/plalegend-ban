package at.nipe.playlegend.playlegendbans;

import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.spi.ScopeBinding;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ComponentResolution extends AbstractModule {

    private final String packageName;
    private final Set<Class<? extends Annotation>> bindingAnnotations;

    @SafeVarargs
    public ComponentResolution(String packageName, final Class<? extends Annotation>... bindingAnnotations) {
        this.packageName = packageName;
        this.bindingAnnotations = new HashSet<>(Arrays.asList(bindingAnnotations));
    }

    @Override
    public void configure() {
        Reflections packageReflections = new Reflections(packageName);
        bindingAnnotations.stream()
                .map(packageReflections::getTypesAnnotatedWith)
                .flatMap(Set::stream)
                .forEach(clazz -> {
                    var declaredAnnotations = clazz.getDeclaredAnnotations();
                    if(Arrays.stream(declaredAnnotations).anyMatch(annotation -> annotation instanceof Singleton)) {
                        bind(clazz).in(Singleton.class);
                    } else {
                        bind(clazz);
                    }
                });
    }
}
