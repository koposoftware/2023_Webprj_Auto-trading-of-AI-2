package com.kopo.trading.user.service;

import com.kopo.trading.security.JWTUtil;
import com.kopo.trading.security.exception.InvalidTokenException;
import com.kopo.trading.user.constants.Social;
import com.kopo.trading.user.dto.*;
import com.kopo.trading.user.entity.Member;
import com.kopo.trading.user.entity.MemberInfo;
import com.kopo.trading.user.exception.*;
import com.kopo.trading.user.repository.MemberInfoRepository;
import com.kopo.trading.user.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;
    private final MemberInfoRepository memberInfoRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil, MemberInfoRepository memberInfoRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.memberInfoRepository = memberInfoRepository;
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginDto loginDto) {
        Member member = memberRepository.findMemberByEmailAddress(loginDto.getEmailAddress());
        if (member == null) throw new UserNotFoundException();

        boolean isMatch = passwordEncoder.matches(loginDto.getPassword(), member.getPassword());
        if (isMatch) {
            Map<String, String> tokens = jwtUtil.generateTokens(loginDto.getEmailAddress());
            String token = tokens.get("token");
            String refreshToken = tokens.get("refreshToken");
            return new LoginResponseDto(loginDto.getEmailAddress(), token, jwtUtil.getExp(token), refreshToken, jwtUtil.getExp(refreshToken));

        } else {
            throw new PasswordNotCorrectException();
        }

    }


    @Transactional
    public RegisterResponseDto register(RegisterDto registerDto) {
        isRegisterAvailable(registerDto);

        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Member member = memberRepository.save(registerDto.toEntity());
        memberInfoRepository.save(new MemberInfo(registerDto.getNickname(), member));
        return new RegisterResponseDto(member.getId());

    }

    public RefreshResponseDto reIssue(RefreshDto refreshDto) {
        String tokenUsername = jwtUtil.getSubject(refreshDto.getToken());
        String refreshTokenUsername = jwtUtil.getSubject(refreshDto.getRefreshToken());

        if (tokenUsername == refreshTokenUsername) {
            Map<String, String> tokens = jwtUtil.generateTokens(tokenUsername);
            String token = tokens.get("token");
            String refreshToken = tokens.get("refreshToken");

            return new RefreshResponseDto(tokenUsername, token, jwtUtil.getExp(token), refreshToken, jwtUtil.getExp(refreshToken));

        } else {
            throw new TokenNotMatchException();
        }
    }


    private void isRegisterAvailable(RegisterDto registerDto) {
        Member member = memberRepository.findMemberByEmailAddress(registerDto.getEmailAddress());
        if (member != null) throw new UserExistsException();
        MemberInfo memberInfo = memberInfoRepository.findMemberInfoByNickname(registerDto.getNickname());
        if (memberInfo != null) throw new NicknameExistsException();
    }

    public void isDuplicated(String nickname) {
        MemberInfo memberInfo = memberInfoRepository.findMemberInfoByNickname(nickname);
        if (memberInfo != null) throw new NicknameExistsException();
    }

    private String google(String token) throws IOException, ParseException {
        String url = "https://www.googleapis.com/oauth2/v2/userinfo";
        return getDataOfGoogle(url, token);
    }

    private String kakao(String token) throws IOException, ParseException {
        return getDataofKakao(token);
    }

    private String getDataOfGoogle(String url, String token) throws IOException, ParseException {
        URL uri = new URL(url + "?access_token=" + token);
        HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        StringBuilder response = new StringBuilder();
        while (true) {
            String inpt = bufferedReader.readLine();
            if (inpt == null) break;
            response.append(inpt);
        }
        bufferedReader.close();
        httpURLConnection.disconnect();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(String.valueOf(response));
        String userEmail = (String) jsonObject.get("email");
        return userEmail;

    }
    private String getDataofKakao( String token)  throws IOException, ParseException{
        URL uri = new URL("https://kapi.kakao.com/v2/user/me" );
        HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();
        httpURLConnection.setRequestMethod("POST");
        // 헤더 설정
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        httpURLConnection.setDoOutput(true);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
        StringBuilder response = new StringBuilder();
        while (true) {
            String inpt = bufferedReader.readLine();
            if (inpt == null) break;
            response.append(inpt);
        }
        bufferedReader.close();
        httpURLConnection.disconnect();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(String.valueOf(response));
        JSONObject j = (JSONObject) jsonObject.get("kakao_account");
        return (String)j.get("email");
    }


    private String oauthCheck(OauthDto oauthDto) throws IOException, ParseException {
        switch (getProviderEnum(oauthDto.getProvider())) {
            case GOOGLE:
                return google(oauthDto.getAccessToken());
            case KAKAO:
                return kakao( oauthDto.getAccessToken());
        }
        return "";

    }

    private Social getProviderEnum(String provider) {
        switch (provider) {
            case "google":
                return Social.GOOGLE;
            case "kakao":
                return Social.KAKAO;
            case "naver":
                return Social.NAVER;
            default:
                return Social.NONE;
        }
    }

    private Long isRegisteredWithOAuth(String email) {
        Member member = memberRepository.findMemberByEmailAddress(email);
        if (member == null) return -1L;
        return member.getId();
    }

    private String createRandomNickname() {
        return "user-" + UUID.randomUUID().toString().substring(0, 6);
    }

    private Long createOAuthUser(String email, Social provider) {
        Member member = memberRepository.save(new Member(email, null, provider));
        MemberInfo memberInfo = memberInfoRepository.save(new MemberInfo(createRandomNickname(), member));
        return member.getId();
    }

    public OauthResponseDto oauthLogin(OauthDto oauthDto) throws IOException, ParseException {
        String email = oauthCheck(oauthDto);

        Map<String, String> tokens = jwtUtil.generateTokens(email);
        Long id = isRegisteredWithOAuth(email);

        Boolean isFirstLogin = id == -1L;
        if (id == -1L) {
            id = createOAuthUser(email, getProviderEnum(oauthDto.getProvider()));
        }

        return new OauthResponseDto(id, email, tokens.get("token"), jwtUtil.getExp(tokens.get("token")), tokens.get("refreshToken"), jwtUtil.getExp(tokens.get("refreshToken")), isFirstLogin);
    }


}
