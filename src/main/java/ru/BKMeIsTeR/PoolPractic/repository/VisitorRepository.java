package ru.BKMeIsTeR.PoolPractic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.BKMeIsTeR.PoolPractic.entity.VisitorEntity;

public interface VisitorRepository extends JpaRepository<VisitorEntity, Long> {

}
