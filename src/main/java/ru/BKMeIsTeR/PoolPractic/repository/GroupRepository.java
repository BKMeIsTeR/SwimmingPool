package ru.BKMeIsTeR.PoolPractic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.BKMeIsTeR.PoolPractic.entity.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    @Query("select count(ge.id) FROM GroupEntity ge inner join ge.visitorEntityList vi where vi.sex = :sex")
    int checkFreePlacesInGroupByGender(@Param("sex") boolean sex);
}
