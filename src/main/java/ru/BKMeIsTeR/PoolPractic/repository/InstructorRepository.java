package ru.BKMeIsTeR.PoolPractic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDtoResponse;
import ru.BKMeIsTeR.PoolPractic.entity.InstructorEntity;

import java.util.List;

public interface InstructorRepository extends JpaRepository<InstructorEntity, Long> {
        @Query(value = "select new ru.BKMeIsTeR.PoolPractic.DTO.InstructorDtoResponse(ie.fullName, ie.experience, " +
                "ie.education, ie.workRate, ie.preferredTimeStart, ie.preferredTimeEnd, " +

        "(select sum (gse.preferredTimeEnd - gse.preferredTimeStart) FROM GroupScheduleEntity gse " +
        "where gse.groupEntity.instructorEntity.id = ie.id and EXTRACT(WEEK FROM gse.date) = :weekNumber), " +

        "(select count(gse2.id) from GroupScheduleEntity gse2 " +
        "where gse2.groupEntity.id = :group_id " + // and ge2.instructorEntity.id=ie.id " +
        "and gse2.preferredTimeStart between ie.preferredTimeStart and ie.preferredTimeEnd) " +

        ") from InstructorEntity ie " +
        "WHERE ie.workRate > (select sum (gse3.preferredTimeEnd - gse3.preferredTimeStart) FROM GroupScheduleEntity gse3 " +
        "where gse3.groupEntity.instructorEntity.id = ie.id and EXTRACT(WEEK FROM gse3.date) = :weekNumber)")
         List<InstructorDtoResponse> showInstructorByFilter(@Param("group_id") Long groupId, @Param("weekNumber") int weekNumber);
}