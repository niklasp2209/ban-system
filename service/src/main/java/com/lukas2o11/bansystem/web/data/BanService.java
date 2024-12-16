package com.lukas2o11.bansystem.web.data;

import com.github.lukas2o11.bansystem.api.Ban;
import com.lukas2o11.bansystem.web.data.models.BanListEntry;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BanService {

    void unban(@NotNull Integer id);

    @NotNull Ban getBanById(@NotNull Integer id);

    @NotNull BanListEntry getBanAsListEntryById(@NotNull Integer id);

    @NotNull Page<Ban> listBans(Optional<Integer> page, Optional<Integer> pageSize);
}
