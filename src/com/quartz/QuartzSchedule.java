package com.quartz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

/**
 * 一个简单的quartz任务管理器
 * Created by lw on 14-7-16.
 */
public class QuartzSchedule {

    private static Scheduler scheduler = getScheduler();


    /**
     * 创建一个调度对象
     *
     * @return
     * @throws SchedulerException
     */
    private static Scheduler getScheduler() {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return scheduler;
    }

    /**
     * 获取调度器
     *
     * @return
     */
    public static Scheduler getInstanceScheduler() {
        return scheduler;
    }

    /**
     * 启动一个调度对象
     *
     * @throws SchedulerException
     */
    public static void start() throws SchedulerException {
        scheduler.start();
    }

    /**
     * 检查调度是否启动
     *
     * @return
     * @throws SchedulerException
     */
    public static boolean isStarted() throws SchedulerException {
        return scheduler.isStarted();
    }

    /**
     * 关闭调度信息
     *
     * @throws SchedulerException
     */
    public static void shutdown() throws SchedulerException {
        scheduler.shutdown();
    }

    /**
     * 添加调度的job信息
     *
     * @param jobdetail
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public static Date scheduleJob(JobDetail jobdetail, Trigger trigger)
            throws SchedulerException {
        return scheduler.scheduleJob(jobdetail, trigger);
    }

    /**
     * 添加相关的触发器
     *
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public static Date scheduleJob(Trigger trigger) throws SchedulerException {
        return scheduler.scheduleJob(trigger);
    }

    /**
     * 添加多个job任务
     *
     * @param triggersAndJobs
     * @param replace
     * @throws SchedulerException
     */
    public static void scheduleJobs(Map<JobDetail, List<Trigger>> triggersAndJobs, boolean replace) throws SchedulerException {
        scheduler.scheduleJobs(triggersAndJobs, replace);
    }

    /**
     * 停止调度Job任务
     *
     * @param triggerkey
     * @return
     * @throws SchedulerException
     */
    public static boolean unscheduleJob(TriggerKey triggerkey)
            throws SchedulerException {
        return scheduler.unscheduleJob(triggerkey);
    }

    /**
     * 停止调度多个触发器相关的job
     *
     * @param triggerKeylist
     * @return
     * @throws SchedulerException
     */
    public static boolean unscheduleJobs(List<TriggerKey> triggerKeylist) throws SchedulerException {
        return scheduler.unscheduleJobs(triggerKeylist);
    }

    /**
     * 重新恢复触发器相关的job任务
     *
     * @param triggerkey
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date rescheduleJob(TriggerKey triggerkey, Trigger trigger)
            throws SchedulerException {
        return scheduler.rescheduleJob(triggerkey, trigger);
    }

    /**
     * 添加相关的job任务
     *
     * @param jobdetail
     * @param flag
     * @throws SchedulerException
     */
    public static void addJob(JobDetail jobdetail, boolean flag)
            throws SchedulerException {
        scheduler.addJob(jobdetail, flag);
    }

    /**
     * 删除相关的job任务
     *
     * @param jobkey
     * @return
     * @throws SchedulerException
     */
    public static boolean deleteJob(JobKey jobkey) throws SchedulerException {
        return scheduler.deleteJob(jobkey);
    }

    /**
     * 删除相关的多个job任务
     *
     * @param jobKeys
     * @return
     * @throws SchedulerException
     */
    public static boolean deleteJobs(List<JobKey> jobKeys)
            throws SchedulerException {
        return scheduler.deleteJobs(jobKeys);
    }

    /**
     * @param jobkey
     * @throws SchedulerException
     */
    public static void triggerJob(JobKey jobkey) throws SchedulerException {
        scheduler.triggerJob(jobkey);
    }

    /**
     * @param jobkey
     * @param jobdatamap
     * @throws SchedulerException
     */
    public static void triggerJob(JobKey jobkey, JobDataMap jobdatamap)
            throws SchedulerException {
        scheduler.triggerJob(jobkey, jobdatamap);
    }

    /**
     * 停止一个job任务
     *
     * @param jobkey
     * @throws SchedulerException
     */
    public static void pauseJob(JobKey jobkey) throws SchedulerException {
        scheduler.pauseJob(jobkey);
    }

    /**
     * 停止多个job任务
     *
     * @param groupmatcher
     * @throws SchedulerException
     */
    public static void pauseJobs(GroupMatcher<JobKey> groupmatcher)
            throws SchedulerException {
        scheduler.pauseJobs(groupmatcher);
    }

    /**
     * 停止使用相关的触发器
     *
     * @param triggerkey
     * @throws SchedulerException
     */
    public static void pauseTrigger(TriggerKey triggerkey)
            throws SchedulerException {
        scheduler.pauseTrigger(triggerkey);
    }

    public static void pauseTriggers(GroupMatcher<TriggerKey> groupmatcher)
            throws SchedulerException {
        scheduler.pauseTriggers(groupmatcher);
    }

    /**
     * 恢复相关的job任务
     *
     * @param jobkey
     * @throws SchedulerException
     */
    public static void resumeJob(JobKey jobkey) throws SchedulerException {
        scheduler.pauseJob(jobkey);
    }

    public static void resumeJobs(GroupMatcher<JobKey> matcher)
            throws SchedulerException {
        scheduler.resumeJobs(matcher);
    }

    public static void resumeTrigger(TriggerKey triggerkey)
            throws SchedulerException {
        scheduler.resumeTrigger(triggerkey);
    }

    public static void resumeTriggers(GroupMatcher<TriggerKey> groupmatcher)
            throws SchedulerException {
        scheduler.resumeTriggers(groupmatcher);
    }

    /**
     * 暂停调度中所有的job任务
     *
     * @throws SchedulerException
     */
    public static void pauseAll() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 恢复调度中所有的job的任务
     *
     * @throws SchedulerException
     */
    public static void resumeAll() throws SchedulerException {
        scheduler.resumeAll();
    }
}
