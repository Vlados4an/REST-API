package ru.erma.restprojectup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.erma.restprojectup.models.Measurement;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
    Long countByRaining(boolean raining);

}
