package com.virros.diplom.message.response;

public class StatisticVacancy {

    private Long views;
    private Long bookmarks;
    private Long notifications;
    private Long notificationsToday;

    public StatisticVacancy() {
    }

    public StatisticVacancy(Long views, Long bookmarks, Long notifications, Long notificationsToday) {
        this.views = views;
        this.bookmarks = bookmarks;
        this.notifications = notifications;
        this.notificationsToday = notificationsToday;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Long bookmarks) {
        this.bookmarks = bookmarks;
    }

    public Long getNotifications() {
        return notifications;
    }

    public void setNotifications(Long notifications) {
        this.notifications = notifications;
    }

    public Long getNotificationsToday() {
        return notificationsToday;
    }

    public void setNotificationsToday(Long notificationsToday) {
        this.notificationsToday = notificationsToday;
    }

    @Override
    public String toString() {
        return "StatisticVacancy{" +
                "views=" + views +
                ", bookmarks=" + bookmarks +
                ", notifications=" + notifications +
                ", notificationsToday=" + notificationsToday +
                '}';
    }
}
