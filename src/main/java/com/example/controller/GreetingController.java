package com.example.controller;

import com.example.dto.AccessTokenDTO;
import com.example.dto.GithubUser;
import com.example.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @Autowired
    private GithubProvider githubProvider;

    // 从application.properties 配置文件获取 github.Client.id 的值
    @Value("${github.Client.id}")
    private String clientId;

    @Value("${github.Client.secret}")
    private String clientSecret;

    @Value("${github.Client.redirect.uri}")
    private String redirectUri;


    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                        @RequestParam(name = "state") String state,
                        Model model) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        System.out.println("check callback !");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.toString());
        return "index";
    }

    @RequestMapping("callback1")
    public String callback1(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                            Model model){
        System.out.println(code+"::"+state);
        return "index";
    }
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
