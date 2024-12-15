package com.lukas2o11.bansystem.web.ban.repository;

import com.github.lukas2o11.bansystem.api.Ban;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BanRepository {

    Optional<Ban> findById(@NotNull Integer id);

    Page<Ban> findAll(@NotNull Pageable pageable);
}
