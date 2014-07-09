package work.caiyun;

import java.util.Date;

/**
 * Created by lw on 14-7-9.
 * 问题表设计------楼主
 */
public class Issue {

    private String id;//问题-主键
    private String issue_Name;//问题提问者
    private String title;//问题标题
    private String content;//问题描述
    private boolean isOK;//是否解决
    private Date create_Time;

}
/**
 * 回复表设计------楼层
 */
class Replay {

    private String id;//回复-主键
    private String issue_Id;//回复的问题-关联Issue中id-外键
    private String issue_Name;//问题回复者
    private Date create_Time;
}
/**
 * 回复-楼层表设计------楼层中的回复
 */
class Replay2Replay {

    private String id;//回复-主键
    private String issue_Id;//回复的问题-关联Issue中id-外键
    private String replay_Id;//回复的问题-关联Replay中id-外键
    private String issue_Name;//问题回复者
    private Date create_Time;
}