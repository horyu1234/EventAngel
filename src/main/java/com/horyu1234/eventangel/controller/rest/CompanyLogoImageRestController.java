package com.horyu1234.eventangel.controller.rest;

import com.horyu1234.eventangel.domain.Company;
import com.horyu1234.eventangel.domain.Event;
import com.horyu1234.eventangel.service.CompanyService;
import com.horyu1234.eventangel.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class CompanyLogoImageRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyLogoImageRestController.class);
    private EventService eventService;
    private CompanyService companyService;

    @Autowired
    public CompanyLogoImageRestController(EventService eventService, CompanyService companyService) {
        this.eventService = eventService;
        this.companyService = companyService;
    }

    @RequestMapping(value = "/companyLogo", method = RequestMethod.GET)
    public void companyLogoImage(HttpServletResponse response, int companyId) {
        Event currentEvent = eventService.getCurrentEvent();

        Company company = companyService.getCompany(currentEvent.getEventId(), companyId);

        String companyLogoImageFileName = company.getCompanyLogoImageFileName();

        File companyLogoImage = companyService.getCompanyLogoImage(currentEvent.getEventId(), companyId);
        if (companyLogoImage == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(getImageType(companyLogoImageFileName));

        try (InputStream inputStream = new FileInputStream(companyLogoImage)) {
            StreamUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("회사 로고 이미지 파일을 불러오는 중 오류가 발생하였습니다.", e);
        }
    }

    private String getImageType(String fileName) {
        String extension = fileName.split("\\.")[1];

        switch (extension.toLowerCase()) {
            case "png":
                return MediaType.IMAGE_PNG_VALUE;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG_VALUE;
            case "gif":
                return MediaType.IMAGE_GIF_VALUE;
            default:
                return MediaType.IMAGE_PNG_VALUE;
        }
    }
}
