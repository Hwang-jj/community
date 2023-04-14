package com.nowcoder.controller;

import com.nowcoder.annotation.LoginRequired;
import com.nowcoder.entity.Event;
import com.nowcoder.entity.User;
import com.nowcoder.event.EventProducer;
import com.nowcoder.service.LikeService;
import com.nowcoder.util.CommunityConstant;
import com.nowcoder.util.CommunityUtil;
import com.nowcoder.util.HostHolder;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
  * @ClassName LikeController
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/11 13:45
  * @version: 1.0
  */ 
@Controller
public class LikeController implements CommunityConstant {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId, int postId){
        User user = hostHolder.getUser();
        if (user != null){
            // 点赞
            likeService.like(user.getId(), entityType, entityId, entityUserId);
            // 数量
            long likeCount = likeService.findEntityLikeCount(entityType, entityId);
            // 状态
            int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);

            Map<String, Object> map = new HashMap<>();
            map.put("likeCount", likeCount);
            map.put("likeStatus", likeStatus);

            // 触发点赞事件
            if (likeStatus == 1){
                Event event = new Event()
                        .setTopic(TOPIC_LIKE)
                        .setUserId(hostHolder.getUser().getId())
                        .setEntityType(entityType)
                        .setEntityId(entityId)
                        .setEntityUserId(entityUserId)
                        .setData("postId", postId);
                eventProducer.fireEvent(event);
            }

            if (entityType == ENTITY_TYPE_POST){
                // 计算帖子的分数(先存到redis中)
                String redisKey = RedisKeyUtil.getPostScoreKey();
                redisTemplate.opsForSet().add(redisKey, postId);
            }

            return CommunityUtil.getJSONString(0, null, map);
        }else {
            return CommunityUtil.getJSONString(1, "请先登录！");
        }
    }
}
