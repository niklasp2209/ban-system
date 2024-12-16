package com.lukas2o11.bansystem.web.data;

import com.github.lukas2o11.bansystem.api.Ban;
import com.lukas2o11.bansystem.web.data.messaging.publisher.UnbanMessagePublisher;
import com.lukas2o11.bansystem.web.data.models.BanListEntry;
import com.lukas2o11.bansystem.web.data.repository.BanRepository;
import com.lukas2o11.bansystem.web.data.exception.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.Optional;

@Service
@ApplicationScope
public class DefaultBanService implements BanService {

    private final BanRepository repository;
    private final UnbanMessagePublisher publisher;

    @Autowired
    public DefaultBanService(BanRepository repository, UnbanMessagePublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public void unban(@NotNull Integer id) {
        publisher.sendMessage(String.valueOf(id));
    }

    @Override
    public @NotNull Ban getBanById(@NotNull Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ban with id '" + id + "' was not found"));
    }

    @Override
    public @NotNull BanListEntry getBanAsListEntryById(@NotNull Integer id) {
        return repository.findByIdAsListEntry(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ban with id '" + id + "' was not found"));
    }

    @Override
    public @NotNull Page<Ban> listBans(Optional<Integer> page, Optional<Integer> pageSize) {
        return repository.findAll(PageRequest.of(page.orElse(0), pageSize.orElse(25)));
    }
}
