package com.example.controller;

import com.example.dto.AccessTokenDTO;
import com.example.dto.GithubUser;
import com.example.mapper.UserMapper;
import com.example.provider.GithubProvider;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    // 从application.properties 配置文件获取 github.Client.id 的值
    @Value("${github.Client.id}")
    private String clientId;

    @Value("${github.Client.secret}")
    private String clientSecret;

    @Value("${github.Client.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.toString());
        /**
         * access_token=4b63033d10e34bee903245fe2bb2322443177f29&scope=user&token_type=bearer
         * GithubUser{name='null', id=15377001, bio='null'}
         */
        if (githubUser != null){
            //登录成功，写cookie和session
            request.getSession().setAttribute("user",githubUser);
            User user = new User();

            user.setToken(UUID.randomUUID().toString());
            /**

            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
             *
             */
            user.setName("hrsjw1");
            user.setAccountId("15377001");
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

            // redirect 重定向
            return "redirect:index";
        }else {
            // 登录失败
            return "redirect:index";
        }
    }
}
