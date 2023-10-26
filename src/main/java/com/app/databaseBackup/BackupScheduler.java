package com.app.databaseBackup;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BackupScheduler {

    private final DatabaseBackupTask databaseBackupTask;

    public BackupScheduler(DatabaseBackupTask databaseBackupTask) {
        this.databaseBackupTask = databaseBackupTask;
    }

    @Scheduled(cron = "0 59 23 * * *") // 每天晚上11:59执行
    public void backupDatabaseDaily() {
        databaseBackupTask.backupDatabase();
    }

}
