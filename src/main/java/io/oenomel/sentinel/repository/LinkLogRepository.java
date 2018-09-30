package io.oenomel.sentinel.repository;

import io.oenomel.sentinel.entity.LinkLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkLogRepository extends JpaRepository<LinkLog,Long> {
}
