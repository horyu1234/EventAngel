package com.horyu1234.eventangel.database.mapper;

import com.horyu1234.eventangel.domain.Company;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CompanyMapper implements RowMapper<Company> {
    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company();
        company.setEventId(rs.getInt("EVENT_ID"));
        company.setCompanyId(rs.getInt("COMPANY_ID"));
        company.setCompanyName(rs.getString("COMPANY_NAME"));
        company.setCompanyDetail(rs.getString("COMPANY_DETAIL"));
        company.setCompanyLogoImageFileName(rs.getString("COMP_LOGO_IMG_FNAME"));

        return company;
    }
}
