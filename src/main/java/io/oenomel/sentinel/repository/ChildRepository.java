package io.oenomel.sentinel.repository;

import io.oenomel.sentinel.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child,Long> {
}
