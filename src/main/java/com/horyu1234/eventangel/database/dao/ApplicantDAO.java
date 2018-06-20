package com.horyu1234.eventangel.database.dao;

import com.horyu1234.eventangel.domain.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by horyu on 2018-04-15
 */
@Repository
public class ApplicantDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTableIfNotExist() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `APPLICANT` ( " +
                "`EMAIL` VARCHAR(255) NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`YOUTUBE_NICKNAME` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`APPLY_TIME` DATETIME NOT NULL, " +
                "`IP_ADDRESS` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`USER_AGENT` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`FINGERPRINT2` TEXT NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "PRIMARY KEY (`EMAIL`) " +
                ") " +
                "COLLATE='utf8mb4_unicode_ci' " +
                "ENGINE=InnoDB;");
    }

    public int getApplicantCount() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM `APPLICANT`;",
                Integer.class);
    }

    public List<Applicant> getApplicant(String email) {
        return jdbcTemplate.query(
                "SELECT * FROM `APPLICANT` " +
                        "WHERE EMAIL = ?;",
                new Object[]{email},
                new BeanPropertyRowMapper<>(Applicant.class));
    }

    public List<Applicant> getRecent50Applicant() {
        return jdbcTemplate.query(
                "SELECT * FROM `APPLICANT` " +
                        "ORDER BY APPLY_TIME " +
                        "DESC LIMIT 50;",
                new BeanPropertyRowMapper<>(Applicant.class));
    }

    public List<Applicant> getApplicant() {
        return jdbcTemplate.query(
                "SELECT * FROM `APPLICANT` " +
                        "ORDER BY APPLY_TIME;",
                new BeanPropertyRowMapper<>(Applicant.class));
    }

    public void insertApplicant(Applicant applicant) {
        jdbcTemplate.update("INSERT INTO `APPLICANT` (EMAIL, YOUTUBE_NICKNAME, APPLY_TIME, IP_ADDRESS, USER_AGENT, FINGERPRINT2) VALUES (?, ?, ?, ?, ?, ?);",
                applicant.getEmail(), applicant.getYoutubeNickname(), applicant.getApplyTime(), applicant.getIpAddress(), applicant.getUserAgent(), applicant.getFingerprint2());
    }

    public void truncate() {
        jdbcTemplate.execute("TRUNCATE `APPLICANT`;");
    }
}
