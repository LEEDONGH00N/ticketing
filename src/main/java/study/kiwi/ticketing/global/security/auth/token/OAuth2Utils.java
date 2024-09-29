//package study.kiwi.ticketing.global.security.auth.token;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//import study.kiwi.ticketing.global.properties.KakaoProperties;
//
//import java.net.URI;
//
//import static study.kiwi.ticketing.global.properties.NaverProperties.*;
//
//
//@Component
//@Slf4j
//public class OAuth2Utils {
//
////    public KakaoOAuthToken getKakaoToken(String code){
////        KakaoOAuthToken kakaoOAuthToken = null;
////        try {
////            URL url = new URL("https://kauth.kakao.com/oauth/token");
////            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////
////            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
////            conn.setRequestMethod("POST");
////            conn.setDoOutput(true);
////
////            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
////            StringBuilder sb = new StringBuilder();
////            sb.append("grant_type=authorization_code");
////            sb.append("&client_id=" + KakaoProperties.kakaoClientId);
////            sb.append("&redirect_uri=" + KakaoProperties.kakaoRedirectUri);
////            sb.append("&code=" + code);
////            bw.write(sb.toString());
////            bw.flush();
////
////            //결과 코드가 200이라면 성공
////            int responseCode = conn.getResponseCode();
////            System.out.println("responseCode : " + responseCode);
////
////            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
////            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
////            String line = "";
////            StringBuilder result = new StringBuilder();
////
////            while ((line = br.readLine()) != null) {
////                result.append(line);
////            }
////            br.close();
////            bw.close();
////            System.out.println("response body : " + result);
////            ObjectMapper objectMapper = new ObjectMapper();
////            kakaoOAuthToken = objectMapper.readValue(result.toString(), KakaoOAuthToken.class);
////
////        } catch (Exception e) {
////            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR); //TODO : 에러 코드 만들기
////        }
////        return kakaoOAuthToken;
////    }
////
//
//    public KakaoOAuthToken requestKakaoOAuthToken(String code) throws RuntimeException{
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", KakaoProperties.KAKAO_AUTHORIZATION_GRANT_TYPE);
//        params.add("client_id", KakaoProperties.KAKAO_CLIENT_ID);
//        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao");
//        params.add("code", code);
//        params.add("client_secret", KakaoProperties.KAKAO_CLIENT_SECRET);
//
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenReq = new HttpEntity<>(params, httpHeaders);
//        ResponseEntity<String> res = restTemplate.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenReq,
//                String.class
//        );
//        log.info("response 1: {}", res.getBody());
//        ObjectMapper objectMapper = new ObjectMapper();
//        KakaoOAuthToken kakaoOAuthToken = null;
//        try {
//            kakaoOAuthToken = objectMapper.readValue(res.getBody(), KakaoOAuthToken.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        log.info("성공");
//        return kakaoOAuthToken;
//    }
//
//
//    public KakaoOAuth2Response requestKakaoOAuth2Entity(String accessToken) throws RuntimeException{
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//        httpHeaders.add("Authorization", "Bearer " + accessToken);
//
//        HttpEntity<MultiValueMap<String, String>> kakaoProfileReq = new HttpEntity<>(httpHeaders);
//        ResponseEntity<String> response = restTemplate.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoProfileReq,
//                String.class
//        );
//        log.info("response 2: {}", response.getBody());
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        KakaoOAuth2Response kakaoOAuth2Response = null;
//        try {
//            kakaoOAuth2Response = objectMapper.readValue(response.getBody(), KakaoOAuth2Response.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return kakaoOAuth2Response;
//    }
//
//    public NaverOAuthToken requestNaverOAuthToken(String code) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-type", "application/json");
//
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", NAVER_AUTHORIZATION_GRANT_TYPE);
//        params.add("client_id", NAVER_CLIENT_ID);
//        params.add("client_secret", NAVER_CLIENT_SECRET);
//        params.add("code", code);
//        //params.add("state", NAVER_STATE);
//
//        URI uri = UriComponentsBuilder.fromHttpUrl("https://nid.naver.com/oauth2.0/token")
//                .queryParams(params)
//                .build().toUri();
//
//        HttpEntity<MultiValueMap<String, String>> naverTokenReq = new HttpEntity<>(httpHeaders);
//        ResponseEntity<String> res = restTemplate.exchange(
//                uri,
//                HttpMethod.POST,
//                naverTokenReq,
//                String.class
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        NaverOAuthToken naverOAuthToken = null;
//        try {
//            naverOAuthToken = objectMapper.readValue(res.getBody(), NaverOAuthToken.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        return naverOAuthToken;
//    }
//
//    public NaverOAuth2Response requestNaverOAuthEntity(String accessToken) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-type", "application/json");
//        httpHeaders.add("Authorization", "Bearer " + accessToken);
//
//        HttpEntity<MultiValueMap<String, String>> naverProfileReq = new HttpEntity<>(httpHeaders);
//        ResponseEntity<String> response = restTemplate.exchange(
//                "https://openapi.naver.com/v1/nid/me",
//                HttpMethod.POST,
//                naverProfileReq,
//                String.class
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        NaverOAuth2Response naverOAuth2Response = null;
//        try {
//            naverOAuth2Response = objectMapper.readValue(response.getBody(), NaverOAuth2Response.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return naverOAuth2Response;
//    }
//}
