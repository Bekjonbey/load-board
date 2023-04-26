package com.example.transaction2.util;

import com.example.transaction2.controller.AuthController;
import com.fasterxml.jackson.databind.ObjectMapper;
public interface RestConstants {
    ObjectMapper objectMapper = new ObjectMapper();

    String AUTHENTICATION_HEADER = "Authorization";

    String[] OPEN_PAGES = {
            "/*",
            "/api/load/open-loads",
            AuthController.AUTH_CONTROLLER_BASE_PATH + "/**",
            RestConstants.BASE_PATH + "/**"
    };
    String BASE_PATH = "/api/v1/";

}
