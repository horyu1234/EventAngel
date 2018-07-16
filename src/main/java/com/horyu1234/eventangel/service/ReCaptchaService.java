/*
 * Copyright 2018. horyu1234(류현오) All rights reserved
 *
 * 본 프로젝트의 소스는 이용자 본인의 학습 용도로만 사용하는 것을 허용하며, 영리추구, 운영 등등 원 저작자의 허락을 받지 않은 행위는 저작권법에 의거하여 엄격히 금지합니다
 *
 * 라이선스에 대한 세부 정보를 얻으시려면 아래를 방문해주시기 바랍니다.
 * https://github.com/horyu1234/EventAngel/blob/master/LICENSE.md
 */

package com.horyu1234.eventangel.service;

import com.horyu1234.eventangel.controller.ApplyController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReCaptchaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplyController.class);
    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Value("${google.recaptcha.secret}")
    private String reCaptchaSecretKey;

    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public ReCaptchaService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public boolean verifyReCaptcha(String ip, String reCaptchaResponse) {
        Map<String, String> body = new HashMap<>();
        body.put("secret", reCaptchaSecretKey);
        body.put("response", reCaptchaResponse);
        body.put("remoteip", ip);

        LOGGER.debug("Request body for recaptcha: {}", body);
        ResponseEntity<Map> recaptchaResponseEntity =
                restTemplateBuilder.build()
                        .postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL +
                                        "?secret={secret}&response={response}&remoteip={remoteip}",
                                body, Map.class, body);

        LOGGER.debug("Response from recaptcha: {}", recaptchaResponseEntity);

        Map<String, Object> responseBody = recaptchaResponseEntity.getBody();

        return (boolean) responseBody.get("success");
    }
}
