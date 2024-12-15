package com.lukas2o11.bansystem.web.ban.repository;

import com.github.lukas2o11.bansystem.api.Ban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DefaultBanRepository implements BanRepository {

    private static final String FIND_BY_ID_QUERY = "SELECT * " +
            "FROM bansystem_bans " +
            "WHERE id = ?";

    private static final String FIND_ALL_QUERY = "SELECT * " +
            "FROM bansystem_bans " +
            "LIMIT ? OFFSET ?";

    private static final String COUNT_QUERY = "SELECT COUNT(*) " +
            "FROM bansystem_bans";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DefaultBanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Ban> findById(@Param("id") Integer id) {
        return Optional.of(jdbcTemplate.query(FIND_BY_ID_QUERY, banFromRow(), id))
                .flatMap(bans -> bans.isEmpty()
                        ? Optional.empty()
                        : Optional.ofNullable(bans.getFirst()));
    }

    @Override
    public Page<Ban> findAll(Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;

        List<Ban> bans = jdbcTemplate.query(FIND_ALL_QUERY, banFromRow(), limit, offset);
        int total = Optional.ofNullable(jdbcTemplate.queryForObject(COUNT_QUERY, Integer.class))
                .orElseThrow(() -> new RuntimeException("Could not fetch total count"));
        return new PageImpl<>(bans, pageable, total);
    }

    private RowMapper<Ban> banFromRow() {
        return (rs, rowNum) -> new Ban(
                rs.getInt("id"),
                UUID.fromString(rs.getString("player")),
                rs.getString("templateId"),
                rs.getString("bannedBy"),
                rs.getLong("bannedAt"),
                rs.getLong("expiresAt"),
                rs.getBoolean("active")
        );
    }
}
