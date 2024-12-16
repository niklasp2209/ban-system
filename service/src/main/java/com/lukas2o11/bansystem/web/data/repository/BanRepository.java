package com.lukas2o11.bansystem.web.data.repository;

import com.github.lukas2o11.bansystem.api.Ban;
import com.lukas2o11.bansystem.web.data.models.BanListEntry;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BanRepository {

    Optional<Ban> findById(@NotNull Integer id);

    Optional<BanListEntry> findByIdAsListEntry(@NotNull Integer id);

    Page<Ban> findAll(@NotNull Pageable pageable);
}
