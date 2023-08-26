package com.example.rgt.oauth2.controller;

import com.example.rgt.oauth2.module.GoogleOAuthModule;
import com.example.rgt.oauth2.controller.request.GoogleOauthLoginRequest;
import com.example.rgt.oauth2.controller.response.GoogleOauthLoginDto;
import com.example.rgt.oauth2.controller.response.GoogleOauthLoginResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class GoogleOauthController {

    private final GoogleOAuthModule googleOAuthModule;

    public GoogleOauthController(GoogleOAuthModule googleOAuthModule) {
        this.googleOAuthModule = googleOAuthModule;
    }

    @GetMapping("/login")
    public ModelAndView oatu2Login(){
        return new ModelAndView("redirect:" + googleOAuthModule.googleInitUrl());
    }

    @GetMapping(value = "/login/redirect")
    public ResponseEntity<GoogleOauthLoginDto> redirectGoogleLogin(@RequestParam(value = "code") String authCode) {
        // HTTP 통신을 위해 RestTemplate 활용
        RestTemplate restTemplate = new RestTemplate();

        GoogleOauthLoginRequest requestParams = GoogleOauthLoginRequest.builder()
                .clientId(googleOAuthModule.getGoogleClientId())
                .clientSecret(googleOAuthModule.getGoogleSecret())
                .code(authCode)
                .redirectUri(googleOAuthModule.getGoogleRedirectUri())
                .grantType("authorization_code")
                .build();

        try {
            // Http Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GoogleOauthLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams, headers);
            ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(googleOAuthModule.getGoogleAuthUrl() + "/token", httpRequestEntity, String.class);

            // ObjectMapper를 통해 String to Object로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
            GoogleOauthLoginResponse googleOauthLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleOauthLoginResponse>() {});

            // 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
            String jwtToken = googleOauthLoginResponse.getIdToken();

            // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
            String requestUrl = UriComponentsBuilder.fromHttpUrl(googleOAuthModule.getGoogleAuthUrl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();

            String resultJson = restTemplate.getForObject(requestUrl, String.class);

            if(resultJson != null) {
                GoogleOauthLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleOauthLoginDto>() {});

                return ResponseEntity.ok().body(userInfoDto);
            }
            else {
                throw new Exception("Google OAuth failed!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(null);
    }

}

