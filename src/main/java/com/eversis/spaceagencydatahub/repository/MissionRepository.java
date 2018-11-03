package com.eversis.spaceagencydatahub.repository;

import com.eversis.spaceagencydatahub.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, String> {
}
