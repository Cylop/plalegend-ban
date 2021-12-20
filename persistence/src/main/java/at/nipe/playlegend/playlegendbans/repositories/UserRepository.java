package at.nipe.playlegend.playlegendbans.repositories;

import at.nipe.playlegend.playlegendbans.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findUserByName(String name);

}