package com.horyu1234.eventangel.database.mapper;

import com.horyu1234.eventangel.domain.Applicant;
import com.horyu1234.eventangel.factory.DateFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class ApplicantMapper implements RowMapper<Applicant> {
    @Override
    public Applicant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Applicant applicant = new Applicant();
        applicant.setEventId(rs.getInt("EVENT_ID"));
        applicant.setApplyEmail(rs.getString("APPLY_EMAIL"));
        applicant.setYoutubeNickname(rs.getString("YOUTUBE_NICKNAME"));
        applicant.setApplyTime(LocalDateTime.parse(rs.getString("APPLY_TIME"), DateFactory.DATABASE_FORMAT));
        applicant.setIpAddress(rs.getString("IP_ADDRESS"));
        applicant.setReferer(rs.getString("REFERER"));
        applicant.setUserAgent(rs.getString("USER_AGENT"));
        applicant.setFingerprint2(rs.getString("FINGERPRINT2"));
        applicant.setDuplicated("Y".equals(rs.getString("DUPLICATED_YN")));
        applicant.setDupCheckAdminName(rs.getString("DUP_CHECK_ADMIN_NAME"));

        return applicant;
    }
}
