package com.untitled.cherrymap.repository;

import com.untitled.cherrymap.domain.AlertMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertMessageRepository extends JpaRepository<AlertMessage, Long> {
    AlertMessage findByMode(String mode);
}
