package com.app.databaseBackup;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DatabaseBackupTask {
    public static void main(String[] args) {
        DatabaseBackupTask databaseBackupTask = new DatabaseBackupTask();
        databaseBackupTask.backupDatabase();
    }
    public void backupDatabase() {
        try {
            String backupCommand = "mysqldump --user=root --password=123456 --host=localhost attendance_sheet > I:\\JavaProjects\\AttendanceManagementSecurityTest\\src\\main\\resources\\backup\\backup.sql";
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", backupCommand);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("数据库备份成功！");
            } else {
                System.out.println("数据库备份失败！"+exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
