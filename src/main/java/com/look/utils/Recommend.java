package com.look.utils;

import com.look.entity.Course;
import com.look.entity.UserInfo;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Recommend {
    /**
     * 计算最近邻
     */
    private Map<Double, String> computeNearestNeighbor(String username, List<UserInfo> users) {
        Map<Double, String> distances = new TreeMap<>();
        UserInfo u1 = new UserInfo();
        u1.setUserName(username);
        for (UserInfo user:users) {
            if (username.equals(user.getUserName())) {
                u1 = user;
            }
        }
        for (UserInfo u2 : users) {
            if (!u2.getUserName().equals(username)) {
                double distance = pearson_dis(u2.getRecentCourse(), u1.getRecentCourse());
                distances.put(distance, u2.getUserName());
            }

        }
        return distances;
    }

    /**
     * 计算peason距离
     */
    private double pearson_dis(List<Course> rating1, List<Course> rating2) {
        int n=rating1.size();
        List<Integer> rating1ScoreCollect = rating1.stream().map(Course::getClicks).collect(Collectors.toList());
        List<Integer> rating2ScoreCollect = rating2.stream().map(Course::getClicks).collect(Collectors.toList());

        double Ex= rating1ScoreCollect.stream().mapToDouble(x->x).sum();
        double Ey= rating2ScoreCollect.stream().mapToDouble(y->y).sum();
        double Ex2=rating1ScoreCollect.stream().mapToDouble(x->Math.pow(x,2)).sum();
        double Ey2=rating2ScoreCollect.stream().mapToDouble(y->Math.pow(y,2)).sum();
        double Exy= IntStream.range(0,n).mapToDouble(i->rating1ScoreCollect.get(i)*rating2ScoreCollect.get(i)).sum();
        double numerator=Exy-Ex*Ey/n;
        double denominator=Math.sqrt((Ex2-Math.pow(Ex,2)/n)*(Ey2-Math.pow(Ey,2)/n));
        if (denominator==0) return 0.0;
        return numerator/denominator;
    }


    /**
     *  推荐算法
     */
    public List<Course> recommend(String username, List<UserInfo> users) {
        //找到最近邻
        Map<Double, String> distances = computeNearestNeighbor(username, users);
        String nearest = distances.values().iterator().next();

        //计算推荐
        UserInfo neighborRatings = new UserInfo();
        for (UserInfo user:users) {
            if (nearest.equals(user.getUserName())) {
                neighborRatings = user;
            }
        }

        UserInfo userRatings = new UserInfo();
        for (UserInfo user:users) {
            if (username.equals(user.getUserName())) {
                userRatings = user;
            }
        }

        //根据自己和邻居计算推荐
        List<Course> recommendationCourse = new ArrayList<>();
        for (Course course : neighborRatings.getRecentCourse()) {
            if (userRatings.find(course.getCourseName()) == null) {
                recommendationCourse.add(course);
            }
        }
        //Collections.sort(recommendationCourse);
        return recommendationCourse;
    }

}
