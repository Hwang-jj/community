package com.nowcoder.controller;

import com.nowcoder.entity.Comment;
import com.nowcoder.entity.DiscussPost;
import com.nowcoder.entity.Page;
import com.nowcoder.entity.User;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.DiscussPostService;
import com.nowcoder.service.LikeService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.CommunityConstant;
import com.nowcoder.util.CommunityUtil;
import com.nowcoder.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
  * @ClassName DiscussPostController
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/8 19:30
  * @version: 1.0
  */ 
@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

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
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model, Page page){
        // 查询帖子
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post", post);

        // 查询帖子作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user", user);

        // 查询帖子点赞数量
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, discussPostId);
        model.addAttribute("likeCount", likeCount);

        // 查询帖子点赞状态
        int likeStatus = hostHolder.getUser() == null ?
                0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_POST, discussPostId);
        model.addAttribute("likeStatus", likeStatus);

        // 查询评论的分页信息
        page.setLimit(5);
        page.setPath("/discuss/detail/"+discussPostId);
        page.setRows(post.getCommentCount());

        // 评论：给帖子的评论
        // 回复：给评论的评论

        // 该帖子的评论列表
        List<Comment> commentList = commentService.findCommentsByEntity
                (ENTITY_TYPE_POST, post.getId(), page.getOffset(), page.getLimit());

        // 评论VO列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if (commentList != null){
            for (Comment comment : commentList){
                // 评论VO
                Map<String, Object> commentVo = new HashMap<>();
                // 将评论的内容装到该评论的 评论Vo 中
                commentVo.put("comment", comment);
                // 将评论的作者装到该评论的 评论Vo 中
                commentVo.put("user", userService.findUserById(comment.getUserId()));
                // 将评论的点赞数量装到该评论的 评论Vo 中
                likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("likeCount", likeCount);
                // 将评论的点赞状态装到该评论的 评论Vo 中
                likeStatus = hostHolder.getUser() == null ?
                        0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("likeStatus", likeStatus);

                // 该评论的回复列表
                List<Comment> replyList = commentService.findCommentsByEntity
                        (ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
                // 回复的VO列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();
                if (replyList != null){
                    for (Comment reply : replyList){
                        // 回复Vo
                        Map<String, Object> replyVo = new HashMap<>();
                        // 将回复的内容装到该回复的 回复Vo 中
                        replyVo.put("reply", reply);
                        // 将回复的作者装到该回复的 回复Vo 中
                        replyVo.put("user", userService.findUserById(reply.getUserId()));
                        // 将回复的回复目标装到该回复的 回复Vo 中
                        User target = reply.getTargetId() == 0 ? null : userService.findUserById(reply.getTargetId());
                        replyVo.put("target", target);
                        // 将回复的点赞数量装到该回复的 回复Vo 中
                        likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT, reply.getId());
                        replyVo.put("likeCount", likeCount);
                        // 将回复的点赞状态装到该回复的 回复Vo 中
                        likeStatus = hostHolder.getUser() == null ?
                                0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, reply.getId());
                        replyVo.put("likeStatus", likeStatus);

                        // 最后将每一个回复的 回复Vo 添加到 回复Vo集合 中
                        replyVoList.add(replyVo);
                    }
                }
                // 将评论的所有回复装到该评论的 评论Vo 中
                commentVo.put("replys", replyVoList);

                // 将评论的回复数量装到该评论的 评论Vo 中
                int replyCount = commentService.findCommentCount(ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("replyCount", replyCount);

                // 最后将每一个评论的 评论Vo 添加到 评论Vo集合 中
                commentVoList.add(commentVo);
            }
        }

        model.addAttribute("comments", commentVoList);

        return "/site/discuss-detail";
    }

}
