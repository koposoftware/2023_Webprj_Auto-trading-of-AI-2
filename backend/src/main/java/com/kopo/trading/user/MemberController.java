package com.kopo.trading.user;


import com.kopo.trading.user.dto.*;
import com.kopo.trading.user.service.MemberService;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping(value = "/user", produces = "application/json")
@RestController
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public RegisterResponseDto register(@RequestBody RegisterDto registerDto) {
        return memberService.register(registerDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginDto loginDto) {

        return memberService.login(loginDto);
    }

    @PostMapping("/login/oauth")
    public OauthResponseDto oauthLogin(@RequestBody OauthDto oauthDto) throws IOException, ParseException {
        return memberService.oauthLogin(oauthDto);
    }

    @PostMapping("/refresh")
    public RefreshResponseDto refresh(@RequestBody RefreshDto refreshDto) {
        return memberService.reIssue(refreshDto);
    }

    @GetMapping("/nickname/duplicate")
    public void duplicate(@RequestParam String nickname) {
        memberService.isDuplicated(nickname);
    }
}
