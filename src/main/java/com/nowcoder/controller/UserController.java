package com.nowcoder.controller;

import com.nowcoder.annotation.LoginRequired;
import com.nowcoder.entity.Comment;
import com.nowcoder.entity.DiscussPost;
import com.nowcoder.entity.Page;
import com.nowcoder.entity.User;
import com.nowcoder.service.*;
import com.nowcoder.util.CommunityConstant;
import com.nowcoder.util.CommunityUtil;
import com.nowcoder.util.HostHolder;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.REUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @ClassName UserController
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/8 13:52
  * @version: 1.0
  */ 
@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private CommentService commentService;

    // 访问用户设置页面
    @LoginRequired
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }

    //上传头像
    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model){

        //空值处理
        if (headerImage==null){
            model.addAttribute("error","您还没有选择图片！");
            return "/site/setting";
        }

        //获取文件后缀
        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件的格式不正确！");
            return "/site/setting";
        }

        //生成随机文件名
        fileName = CommunityUtil.generateUUID() + suffix;
        //确定文件存放路径
        File dest = new File(uploadPath+"/"+fileName);
        try {
            //存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败："+e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！",e);
        }

        //更新当前用户的头像的路径(web访问路径)
        // http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(),headerUrl);

        return "redirect:/index";
    }

    //获取头像
    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response){
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 响应图片
        response.setContentType("image/"+suffix);
        try (
                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(fileName);
                ){
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败："+e.getMessage());
        }
    }

    //修改密码
    @LoginRequired
    @RequestMapping(path = "/uploadPassword", method = RequestMethod.POST)
    public String updatePassword(String originalPassword, String newPassword, String confirmPassword,
                                 Model model, @CookieValue("ticket") String ticket){
        //空值处理
        if (originalPassword == null){
            model.addAttribute("originalPasswordMsg","请输入原始密码！");
            return "/site/setting";
        }
        if (newPassword == null){
            model.addAttribute("newPasswordMsg","请输入新的密码！");
            return "/site/setting";
        }
        if (confirmPassword == null){
            model.addAttribute("confirmPasswordMsg","请输入新的密码");
            return "/site/setting";
        }

        //判断输入的原始密码是否正确
        User user = hostHolder.getUser();
        if (!CommunityUtil.md5(originalPassword + user.getSalt()).equals(user.getPassword())){
            model.addAttribute("originalPasswordMsg","原始密码错误！");
            return "/site/setting";
        }

        //判断两次输入的新密码是否一致
        if (!confirmPassword.equals(newPassword)){
            model.addAttribute("confirmPasswordMsg","两次密码输入不一致！");
            return "/site/setting";
        }

        //更新密码、退出账号并重定向至登录页面
        userService.updatePassword(user.getId(), CommunityUtil.md5(newPassword + user.getSalt()));
        userService.logout(ticket);
        return "redirect:/login";
    }

    // 个人主页
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model){
        User user = userService.findUserById(userId);
        if (user == null){
            throw new RuntimeException("该用户不存在");
        }

        // 用户
        model.addAttribute("user", user);
        // 获赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);
        // 关注数量
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);
        // 粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount", followerCount);
        // 是否已关注
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null){
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);

        return "/site/profile";
    }

    // 获取用户的帖子列表
    @RequestMapping(path = "/discussPostList/{userId}", method = RequestMethod.GET)
    public String getDiscussPostList(@PathVariable("userId") int userId, Model model, Page page){
        // 查询用户
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);

        // 查询用户发布帖子数
        int postCount = discussPostService.findDiscussPostRows(userId);
        model.addAttribute("postCount", postCount);

        // 查询帖子的分页信息
        page.setLimit(5);
        page.setPath("/user/discussPostList/" + userId);
        page.setRows(postCount);

        // 帖子的列表
        List<DiscussPost> postList = discussPostService.findDiscussPosts(userId, page.getOffset(), page.getLimit());

        //帖子VO列表
        List<Map<String, Object>> postVoList = new ArrayList<>();
        if (postList != null){
            for (DiscussPost post : postList){
                // 帖子Vo
                Map<String, Object> postVo = new HashMap<>();
                // 将帖子的内容装到该帖子的 帖子Vo 中
                postVo.put("post", post);
                // 帖子的点赞数
                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
                postVo.put("likeCount", likeCount);

                postVoList.add(postVo);
            }
        }

        model.addAttribute("posts", postVoList);

        return "/site/my-post";
    }

    // 获取用户的评论列表
    @RequestMapping(path = "/commentList/{userId}", method = RequestMethod.GET)
    public String getCommentList(@PathVariable("userId") int userId, Model model, Page page){
        // 查询用户
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);

        // 查询用户发布评论数
        int commentCount = commentService.findCommentCountByUserId(userId, ENTITY_TYPE_POST);
        model.addAttribute("commentCount", commentCount);

        // 查询评论的分页信息
        page.setLimit(10);
        page.setPath("/user/commentList/" + userId);
        page.setRows(commentCount);

        // 评论列表
        List<Comment> commentList = commentService.findCommentsByUserId(userId, ENTITY_TYPE_POST, page.getOffset(), page.getLimit());

        // 评论VO列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if (commentList != null){
            for (Comment comment : commentList){
                // 评论Vo
                Map<String, Object> commentVo = new HashMap<>();
                // 将评论的内容装到该评论的 评论Vo 中
                commentVo.put("comment", comment);
                // 将评论的帖子装到该评论的 评论Vo 中
                commentVo.put("post", discussPostService.findDiscussPostById(comment.getEntityId()));

                commentVoList.add(commentVo);
            }
        }
        model.addAttribute("comments", commentVoList);

        return "/site/my-reply";
    }
}
