package com.aimed.aimed.doctorsearch;

import com.aimed.aimed.doctorsearch.entity.DoctorWithSimilarity;
import com.aimed.aimed.doctorsearch.entity.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DoctorSearchRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<DoctorWithSimilarity> findTopDoctorsByEmbedding(
            float[] contextEmbedding,
            SearchCriteria searchCriteria
    ) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("embedding", Arrays.toString(contextEmbedding), Types.OTHER);

        StringBuilder sql = new StringBuilder(
            """
            SELECT d.*, (1 - (d.profile_embedding <=> CAST(:embedding AS vector))) AS similarity
            FROM doctor_profile d
            WHERE 1 = 1
            """
        );

        if (searchCriteria.filterLicense()) {
            sql.append("AND d.license IS NOT NULL AND d.license_expiry_date >= CURRENT_DATE");
        }
        if (searchCriteria.filterBlankEducation()) {
            sql.append("AND d.education IS NOT NULL AND d.education <> ''");
        }
        if (searchCriteria.filterBlankDescription()) {
            sql.append("AND d.description IS NOT NULL AND d.description <> ''");
        }

        sql.append(" ORDER BY d.profile_embedding <=> CAST(:embedding AS vector)");

        if (searchCriteria.orderByExperience()) {
            sql.append(", d.practice_start_date");
        }

        sql.append(" LIMIT 20");

        return jdbcTemplate.query(
                sql.toString(),
                parameterSource,
                new DataClassRowMapper<>(DoctorWithSimilarity.class)
        );
    }
}
