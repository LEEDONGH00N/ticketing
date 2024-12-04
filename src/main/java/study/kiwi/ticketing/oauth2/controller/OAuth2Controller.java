package study.kiwi.ticketing.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class OAuth2Controller {
    // OAuth2 로그인 시 최초 로그인인 경우 회원가입 진행, 필요한 정보를 쿼리 파라미터로 받는다
    @GetMapping("/oauth2/signup")
    public String loadOAuthSignUp(@RequestParam String email,
                                  @RequestParam String providerType,
                                  @RequestParam String oauthId,
                                  @RequestParam String name,
                                  @RequestParam String phoneNum,
                                  Model model) {
        model.addAttribute("email", email);
        model.addAttribute("providerType", providerType);
        model.addAttribute("oauthId", oauthId);
        model.addAttribute("name", name);
        model.addAttribute("phoneNum", phoneNum);
        return "member/signup";
    }
}
