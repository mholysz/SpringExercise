package pl.springExercises.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.springExercises.users.entity.GroupEntity;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
}
