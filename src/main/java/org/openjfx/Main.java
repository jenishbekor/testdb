package org.openjfx;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        DatabaseConnection dbConnection = new PostgreSQLConnection();

        try {
            Connection conn = dbConnection.getConnection();
            TaskDAO taskDAO = new TaskDAO(conn);

            Task tt = taskDAO.getTask(6);
            System.out.println(tt.getTaskname());

            //int id = taskData.insertTask("Assignment", "Java and Database assignment with database", tt.getDeadline(), "MEDIUM");
            //System.out.println(id);

            /*
            System.out.println(tt);

            taskData.updateTask(6, "new task", tt.getDescription(), tt.getDeadline(), tt.getPriority());

            taskData.deleteTask(1);


            ArrayList<Task> tasks = taskData.getAllTasks();

            for(Task e:tasks){
                System.out.println(e);
            }
            */
        } catch (SQLException e) {
            System.out.println(e.toString());
        }



    }
}