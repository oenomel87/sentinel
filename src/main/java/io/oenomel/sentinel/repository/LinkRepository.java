package io.oenomel.sentinel.repository;

import io.oenomel.sentinel.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link,Long> {
}
