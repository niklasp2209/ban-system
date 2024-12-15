package com.lukas2o11.bansystem.web.ban;

import com.github.lukas2o11.bansystem.api.Ban;
import com.lukas2o11.bansystem.web.ban.repository.BanRepository;
import com.lukas2o11.bansystem.web.exception.ResourceNotFoundException;
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

    @Autowired
    public DefaultBanService(BanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ban getBanById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ban with id '" + id + "' was not found"));
    }

    @Override
    public Page<Ban> listBans(Optional<Integer> page, Optional<Integer> pageSize) {
        return repository.findAll(PageRequest.of(page.orElse(0), pageSize.orElse(25)));
    }
}
