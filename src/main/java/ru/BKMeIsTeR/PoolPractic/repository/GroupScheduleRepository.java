package ru.BKMeIsTeR.PoolPractic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.BKMeIsTeR.PoolPractic.DTO.GroupScheduleDto;
import ru.BKMeIsTeR.PoolPractic.entity.GroupScheduleEntity;

import java.util.List;

public interface GroupScheduleRepository extends JpaRepository<GroupScheduleEntity, Long> {
        @Query(value = "select new ru.BKMeIsTeR.PoolPractic.DTO.GroupScheduleDto(gse.date, gse.preferredTimeStart, gse.preferredTimeEnd) " +
                "from GroupScheduleEntity gse " +
                "WHERE gse.groupEntity.id = :group_id")
        List<GroupScheduleDto> findAllByGroupEntityIdEquals(@Param("group_id") Long groupId);
}