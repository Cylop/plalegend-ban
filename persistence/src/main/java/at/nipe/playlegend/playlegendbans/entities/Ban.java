package at.nipe.playlegend.playlegendbans.entities;

import lombok.*;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bans")
public class Ban {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "banned_id", nullable = false)
    private User banned;

    @ManyToOne
    @JoinColumn(name = "bannedBy_id")
    private User bannedBy;

    private String reason;

    @Temporal(TemporalType.TIMESTAMP)
    private Date until;

    private boolean permanent;

    private boolean active;

    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();

    @Version
    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ban ban = (Ban) o;
        return id != null && Objects.equals(id, ban.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "banned = " + banned + ", " +
                "bannedBy = " + bannedBy + ", " +
                "reason = " + reason + ", " +
                "until = " + until + ", " +
                "permanent = " + permanent + ", " +
                "active = " + active + ", " +
                "createdAt = " + createdAt + ", " +
                "version = " + version + ")";
    }
}