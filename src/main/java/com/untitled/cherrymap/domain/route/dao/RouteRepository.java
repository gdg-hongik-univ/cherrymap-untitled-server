package com.untitled.cherrymap.domain.route.dao;

import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findAllByMember(Member member);

    @Query("SELECT COUNT(r) > 0 FROM Route r WHERE r.mode = :mode AND r.startLat = :startLat AND r.startLng = :startLng " +
            "AND r.endLat = :endLat AND r.endLng = :endLng")
    boolean existsByRouteDetails(@Param("mode") String mode,
                                 @Param("startLat") double startLat, @Param("startLng") double startLng,
                                 @Param("endLat") double endLat, @Param("endLng") double endLng);

}
