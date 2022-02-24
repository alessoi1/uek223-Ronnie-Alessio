package com.example.demo.domain.appMyListEntrry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MyListEntryRepository extends JpaRepository<MyListEntry, UUID> {
    @Modifying
    @Query("SELECT m FROM mylistentry m WHERE m.user.id = :userId ORDER BY m.wichtigkeit")
    List<MyListEntry> findAllByUser(@Param("userId") UUID userId);
}
