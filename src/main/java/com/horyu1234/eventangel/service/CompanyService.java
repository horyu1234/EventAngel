package com.horyu1234.eventangel.service;

import com.horyu1234.eventangel.database.dao.CompanyDAO;
import com.horyu1234.eventangel.domain.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by horyu on 2018-04-06
 */
@Service
public class CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
    private CompanyDAO companyDAO;

    @Value("${spring.upload-dir.company-logo-image}")
    private String companyLogoImageUploadDir;

    @PostConstruct
    public void init() {
        companyDAO.createTableIfNotExist();
    }

    @Autowired
    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void saveCompany(Company company) {
        int eventId = company.getEventId();

        if (company.getCompanyId() == 0) {
            int maxCompanyId = 0;

            if (!companyDAO.getCompanyList(eventId).isEmpty()) {
                maxCompanyId = companyDAO.getMaxCompanyId(eventId);
            }

            company.setCompanyId(maxCompanyId + 1);
        }

        companyDAO.updateOrInsertCompany(company);
    }

    public void saveCompanyLogoImage(int eventId, int companyId, MultipartFile multipartFile) {
        File uploadDir = new File(companyLogoImageUploadDir);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFilename = multipartFile.getOriginalFilename();

        String extension = originalFilename.split("\\.")[1];
        String randomUUID = UUID.randomUUID().toString();

        String fileName = randomUUID + "." + extension;

        File file = new File(companyLogoImageUploadDir, fileName);

        try {
            file.createNewFile();
        } catch (IOException e) {
            LOGGER.error("업로드한 회사 로고 이미지 파일을 생성하는 중 오류가 발생하였습니다.", e);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            LOGGER.error("업로드한 회사 로고 이미지파일을 기록하는 중 오류가 발생하였습니다.", e);
        }

        companyDAO.updateCompanyLogo(eventId, companyId, fileName);
    }

    public void deleteCompany(int eventId, int companyId) {
        companyDAO.deleteCompany(eventId, companyId);
    }

    public void deleteCompanyLogoImage(int eventId, int companyId) {
        File companyLogoImage = getCompanyLogoImage(eventId, companyId);
        if (companyLogoImage == null) {
            return;
        }

        if (companyLogoImage.exists()) {
            companyLogoImage.delete();
        }
    }

    public List<Company> getCompanyList(int eventId) {
        return companyDAO.getCompanyList(eventId);
    }

    public Company getCompany(int eventId, int companyId) {
        return companyDAO.getCompany(eventId, companyId);
    }

    public File getCompanyLogoImage(int eventId, int companyId) {
        Company company = getCompany(eventId, companyId);

        String companyLogoImageFileName = company.getCompanyLogoImageFileName();
        if (companyLogoImageFileName == null) {
            return null;
        }

        return new File(companyLogoImageUploadDir, companyLogoImageFileName);
    }
}
