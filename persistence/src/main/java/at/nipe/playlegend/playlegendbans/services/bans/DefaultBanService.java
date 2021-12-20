package at.nipe.playlegend.playlegendbans.services.bans;

import at.nipe.playlegend.playlegendbans.entities.Ban;
import at.nipe.playlegend.playlegendbans.entities.User;
import at.nipe.playlegend.playlegendbans.repositories.BanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class DefaultBanService implements BanService {

    private final BanRepository banRepository;

    @Autowired
    public DefaultBanService(BanRepository banRepository) {
        this.banRepository = banRepository;
    }

    @Override
    public Page<Ban> findAllForUser(UUID uuid, Pageable pageable) {
        return this.banRepository.findAllByBanned_Id(uuid, pageable);
    }

    @Override
    public Page<Ban> findAllByUser(UUID uuid, Pageable pageable) {
        return this.banRepository.findAllByBannedBy_IdOrderByActiveAsc(uuid, pageable);
    }

    @Override
    public void save(Ban ban) {
        this.banRepository.save(ban);
    }

    @Override
    public void delete(Ban ban) {
        this.banRepository.delete(ban);
    }

    @Override
    public boolean unban(UUID user) {
        return this.banRepository.unban(user);
    }

    @Override
    public boolean isBanned(UUID user) {
        return this.getLongestBan(user).isPresent();
    }

    @Override
    public Optional<Ban> getLongestBan(UUID user) {
        return this.banRepository.findTopByActiveTrueAndBanned_IdAndUntilIsLessThanOrderByUntilDesc(user, new Date());
    }

}
