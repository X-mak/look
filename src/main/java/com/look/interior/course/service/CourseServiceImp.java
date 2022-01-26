package com.look.interior.course.service;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.look.entity.*;
import com.look.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImp implements CourseService{

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseClassMapper courseClassMapper;

    @Autowired
    PublishMapper publishMapper;

    @Autowired
    BuyMapper buyMapper;

    @Autowired
    CommentsMapper commentsMapper;

    @Autowired
    HistoryMapper historyMapper;

    @Autowired
    LikesMapper likesMapper;

    public int addCourse(String userAccount,Course course){

        try{
            //补全初始值
            course.setClicks(0);
            course.setStatus(0);
            course.setCost(0);
            courseMapper.insertSelective(course);
            int courseId = course.getId();
            CourseClass courseClass = course.getCourseClass();
            courseClass.setId(courseId);
            courseClassMapper.insertSelective(courseClass);
            Publish publish = new Publish(userAccount,courseId);
            publishMapper.insertSelective(publish);
            Buy buy = new Buy(userAccount,courseId);
            buyMapper.insertSelective(buy);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }


    public List<Course> getOnesCourse(String userAccount,String status){
        if(status.equals(""))status="%";
        List<Course> courses = courseMapper.queryPublishCourse(userAccount, status);
        return courses;
    }


    public int updateCourse(Course course){
        try {
            courseMapper.updateByPrimaryKeySelective(course);
            if(course.getCourseClass() != null){
                courseClassMapper.updateByPrimaryKeySelective(course.getCourseClass());
            }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return 1;
    }

    public Course getCourseById(Integer id){
        Course course = courseMapper.querySingleCourse(id);
        Example example = new Example(Comments.class);
        example.createCriteria().andEqualTo("courseId",id);
        List<Comments> comments = commentsMapper.selectByExample(example);
        int sum = 0;
        int size = comments.size();
        if(size == 0)return  course;
        for(Comments c:comments){
            sum += c.getStar();
        }
        course.setRanks((double) (sum/size));
        return course;
    }

    public List<Course> getAllCourse(String keyword,String order,String age,String subject){
        List<Course> courses = null;
        if(keyword.equals("")){
            if(order.equals("")){
                courses = courseMapper.queryCourseInfo(age, subject);
            }else{
                courses = courseMapper.queryCourseInfoByClicks(order,age,subject);
            }
        }else {
            if(order.equals("")){
                courses = courseMapper.queryCourseInfoByKeyword(keyword,age,subject);
            }else{
                courses = courseMapper.queryCourseInfoByKeywordClicks(keyword, order,age,subject);
            }
        }
        return courses;
    }


    public List<Course> getCourseByStatus(Integer status){
        List<Course> courses = courseMapper.queryCourseByStatus(status);
        return courses;
    }

    public List<Course> getBoughtCourse(String userAccount){
        List<Course> courses = courseMapper.queryBoughtCourse(userAccount);
        return courses;
    }

    public int addComment(Comments comments){
        try{
            comments.setDate(DateUtil.now());
            comments.setHot(0);
            commentsMapper.insertSelective(comments);
        }catch (Exception e){
            return -1;
        }
        return 1;
    }

    public List<Comments> getComments(Integer id,String order,String userAccount){
        List<Comments> comments = new ArrayList<>();
        if(order.equals("date")){
            comments = commentsMapper.queryCommentsOrderByTime(id);
        }else if(order.equals("hot")){
            comments = commentsMapper.queryCommentsOrderByHot(id);
        }
        if(!userAccount.equals("")){
            for (Comments c:comments){
                if(c.getUserAccount().equals(userAccount)){
                    c.setOwn(true);
                }
            }
        }
        return comments;
    }

    public int watchedCourse(String userAccount,Integer courseId){
        History history = new History(userAccount,courseId);
        try{
            Example example = new Example(History.class);
            example.createCriteria().andEqualTo("userAccount",userAccount).andEqualTo("courseId",courseId);
            List<History> histories = historyMapper.selectByExample(example);
            if(histories.size() == 1){
                historyMapper.deleteByPrimaryKey(histories.get(0).getId());
            }
            history.setDate(DateUtil.now());
            historyMapper.insertSelective(history);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public int getStars(Integer courseId,String userAccount){
        Example example = new Example(Comments.class);
        example.createCriteria().andEqualTo("userAccount",userAccount).andEqualTo("courseId",courseId);
        List<Comments> comments = commentsMapper.selectByExample(example);
        if(comments.size() == 0){
            return -1;
        }
        return comments.get(0).getStar();
    }

    public int deleteComment(Integer id){
        try{
            commentsMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public int likeComment(Likes likes){
        try{
            likesMapper.insertSelective(likes);
            Comments comments = commentsMapper.selectByPrimaryKey(likes.getCommentId());
            comments.setHot(comments.getHot()+1);
            commentsMapper.updateByPrimaryKeySelective(comments);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public int cancelLikes(Integer commentId,String userAccount){
        try{
            Example example = new Example(Likes.class);
            example.createCriteria().andEqualTo("commentId",commentId).andEqualTo("userAccount",userAccount);
            likesMapper.deleteByExample(example);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public PageInfo<Course> getWatchHistory(String userAccount,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,true);
        List<Course> courses = courseMapper.queryHistoryCourse(userAccount);

        PageInfo<Course> res = new PageInfo<>(courses);
        return res;
    }


}
