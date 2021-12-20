package at.nipe.playlegend.playlegendbans;

import at.nipe.playlegend.playlegendbans.repositories.BanRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = BanRepository.class)
public class PersistenceConfiguration {


}
