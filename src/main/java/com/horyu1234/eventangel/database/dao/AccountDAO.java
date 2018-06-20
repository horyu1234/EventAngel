package com.horyu1234.eventangel.database.dao;

import com.horyu1234.eventangel.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by horyu on 2018-04-15
 */
@Repository
public class AccountDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTableIfNotExist() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `ACCOUNT` ( " +
                "`USERNAME` VARCHAR(20) NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`PASSWORD` TEXT NOT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "`NICKNAME` VARCHAR(20) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci', " +
                "PRIMARY KEY (`USERNAME`) " +
                ") " +
                "COLLATE='utf8mb4_unicode_ci' " +
                "ENGINE=InnoDB;");
    }

    public List<Account> getAccount(String username) {
        return jdbcTemplate.query(
                "SELECT * FROM `ACCOUNT` " +
                        "WHERE USERNAME = ? " +
                        "LIMIT 1;",
                new Object[]{username},
                new BeanPropertyRowMapper<>(Account.class));
    }
}
