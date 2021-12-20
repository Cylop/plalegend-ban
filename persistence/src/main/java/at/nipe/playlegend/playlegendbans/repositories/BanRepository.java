package at.nipe.playlegend.playlegendbans.repositories;

import at.nipe.playlegend.playlegendbans.entities.Ban;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface BanRepository extends PagingAndSortingRepository<Ban, Long> {

    Page<Ban> findAllByBanned_Id(UUID uuid, Pageable pageable);

    Page<Ban> findAllByBannedBy_IdOrderByActiveAsc(UUID uuid, Pageable pageable);

    /*
     * This function is returning the longest active ban for a user
     */
    Optional<Ban> findTopByActiveTrueAndBanned_IdAndUntilIsLessThanOrderByUntilDesc(UUID uuid, Date date);

    @Query("update Ban set active = false where id = :id")
    @Modifying
    boolean unban(@Param("id") UUID uuid);
}