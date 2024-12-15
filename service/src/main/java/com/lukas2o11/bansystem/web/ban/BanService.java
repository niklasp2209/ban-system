package com.lukas2o11.bansystem.web.ban;

import com.github.lukas2o11.bansystem.api.Ban;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BanService {

    @NotNull Ban getBanById(int id);

    @NotNull Page<Ban> listBans(Optional<Integer> page, Optional<Integer> pageSize);
}
