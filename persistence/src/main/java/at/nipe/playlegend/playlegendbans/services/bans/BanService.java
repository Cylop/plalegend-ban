package at.nipe.playlegend.playlegendbans.services.bans;

import at.nipe.playlegend.playlegendbans.entities.Ban;
import at.nipe.playlegend.playlegendbans.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface BanService {

    Page<Ban> findAllForUser(UUID uuid, Pageable pageable);

    Page<Ban> findAllByUser(UUID uuid, Pageable pageable);

    void save(Ban ban);

    void delete(Ban ban);

    boolean unban(UUID user);

    boolean isBanned(UUID user);

    Optional<Ban> getLongestBan(UUID user);
}
