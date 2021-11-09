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

            "(select sum (ge.preferredTimeEnd - ge.preferredTimeStart) FROM GroupEntity ge " +
            "where ge.instructorEntity.id = ie.id), " +

            "(select count(ge2.id) from GroupEntity ge2 " +
            "where ge2.id = :group_id " + // and ge2.instructorEntity.id=ie.id " +
            "and ge2.preferredTimeStart between ie.preferredTimeStart and ie.preferredTimeEnd) " +

            ") from InstructorEntity ie " +
            "WHERE ie.workRate > (select sum (ge.preferredTimeEnd - ge.preferredTimeStart) FROM GroupEntity ge " +
            "where ge.instructorEntity.id = ie.id)")
    List<InstructorDtoResponse> showInstructorByFilter(@Param("group_id") Long groupId);
}

//@Query("select new ru.BKMeIsTeR.PoolPractic.DTO.InstructorDtoResponse(ie.fullName AS fullName, ie.experience AS experience, " +
//        "ie.education as education, ie.workRate as workRate, ie.preferredTimeStart as preferredTimeStart, " +
//        "ie.preferredTimeEnd as preferredTimeStart ," +
//
//        "(select sum(ge.preferredTimeEnd-ge.preferredTimeStart) from GroupEntity ge " +
//        "where ge.instructorEntity.id = ie.id) as currentEmployment, " +
//
//        "(select true from GroupEntity ge2 " +
//        "where ge2.id = :group_id and ge2.instructorEntity.id=ie.id" +
//        "having ge2.preferredTimeStart between ie.preferredTimeStart and ie.preferredTimeEnd) as intersects" +
//
//        ") from InstructorEntity ie")