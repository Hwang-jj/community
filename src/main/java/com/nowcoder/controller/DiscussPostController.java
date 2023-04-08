package com.nowcoder.controller;

import com.nowcoder.entity.DiscussPost;
import com.nowcoder.entity.User;
import com.nowcoder.service.DiscussPostService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.CommunityUtil;
import com.nowcoder.util.HostHolder;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
  * @ClassName DiscussPostController
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/8 19:30
  * @version: 1.0
  */ 
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title, String content){
        User user = hostHolder.getUser();
        if (user == null){
            // 403：无权限
            return CommunityUtil.getJSONString(403, "你还没有登录！");
        }

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);

        // 报错的情况将来统一处理！
        return CommunityUtil.getJSONString(0,"发布成功！");
    }

    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model){
        // 查询帖子
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post", post);
        // 查询帖子作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user", user);

        return "/site/discuss-detail";
    }

}
