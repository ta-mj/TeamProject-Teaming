package com.example.teamproject;

public class Constants {
    //메인 리스트 아이템 key 값
    public static final int TEAM_TASK = 1;
    public static final int TEAM_SCHEDULE = 2;
    // 알림 설정 Preference Key 값
    public static final String SHARED_PREF_NOTIFICATION_KEY = "Notification Value";

    // 알림 채널 ID 값
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    // 한국 TimeZone
    public static final String KOREA_TIMEZONE = "Asia/Seoul";

    // 챌린지 랭킹 시작 시각
    public static final Integer A_MORNING_EVENT_TIME = 9;
    public static final Integer A_NIGHT_EVENT_TIME = 17;

    // 푸시알림 허용 Interval 시간
    public static final Integer NOTIFICATION_INTERVAL_HOUR = 1;

    // 백그라운드 work Unique 이름
    public static final String WORK_A_NAME = "Challenge Notification";
}