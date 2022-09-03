package contacts.entities;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;

/**
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, length = 36)
    private String uuid;

    @Version
    private int jpaVersion;

    public AbstractEntity() {
        this(false);
    }

    public AbstractEntity(boolean newEntity) {
        if (newEntity) {
            uuid = UUID.randomUUID().toString();
        }
    }

    public long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public int getJpaVersion() {
        return jpaVersion;
    }

    public void setJpaVersion(int jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    @Override
    public int hashCode() {
        if (uuid == null) {
            throw new IllegalStateException("UUID not set");
        }
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        return true;
    }

    @PrePersist
    public void checkUuid() {
        if (uuid == null || uuid.length() != 36) {
            throw new IllegalStateException(getClass().getSimpleName() + ": invalid UUID (" + uuid + ")");
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + getId() + "/" + getJpaVersion();
    }

}
