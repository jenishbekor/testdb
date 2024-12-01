package org.openjfx;

import java.sql.*;
import java.util.ArrayList;

public class TaskData {

    private Connection conn;

    public TaskData(Connection conn){
        this.conn = conn;
    }

    public ArrayList<Task> getAllTasks(){
        ArrayList<Task> tasks = new ArrayList<Task>();

        try {

            String sql = "SELECT * FROM TASKS";
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            //preparedStatement.setString(1, "HIGH");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("taskId");
                String name = resultSet.getString("taskname");
                String desc = resultSet.getString("description");
                Date deadline = resultSet.getDate("deadline");
                String p = resultSet.getString("priority");


                Task task = new Task(id, name, desc, deadline, p);
                tasks.add(task);
                //System.out.println(task);
            }
            resultSet.close();
        }
        catch(SQLException e){
            System.out.println(e.toString());
        }


        return tasks;
    }

    public int insertTask(String uname, String udesc, Date udate, String upri){
        int newid=0;

        try{
            String sql = "INSERT INTO tasks (taskname, description, deadline, priority)"+
                    " VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, udesc);
            preparedStatement.setDate(3,udate);
            preparedStatement.setString(4, upri);

            int affectedRows = preparedStatement.executeUpdate();

            if( affectedRows == 0){
                throw new SQLException("Creating task failed.");
            }

            try( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){

                if(generatedKeys.next()){
                    newid = generatedKeys.getInt(1);

                }
            }

            System.out.println("New record is added with id: " + newid);

        }
        catch(SQLException e){
            System.out.println(e.toString());
        }


        return newid;
    }

    public void deleteTask(int id){

        try{
            String sql = "DELETE FROM tasks WHERE taskid=?";
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            System.out.println("Affected rows: " + affectedRows);

        }
        catch (SQLException e){
            System.out.println(e.toString());
        }


    }

    public void updateTask(int id, String uname, String udesc, Date udate, String upri){

        try{
            String sql = "UPDATE tasks SET taskname = ?, description = ?, deadline = ?, priority=? WHERE taskid=?";
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, udesc);
            preparedStatement.setDate(3,udate);
            preparedStatement.setString(4,upri);
            preparedStatement.setInt(5,id);

            int affectedRows = preparedStatement.executeUpdate();

            if( affectedRows == 0){
                throw new SQLException("Creating task failed.");
            }

            System.out.println("Update is success.");

        }
        catch(SQLException e){
            System.out.println(e.toString());
        }


    }


    public Task getTask(int id){
        Task task=null;

        try{
            String sql = "SELECT * FROM TASKS WHERE TASKID=?";
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int taskid = resultSet.getInt("taskId");
                String name = resultSet.getString("taskname");
                String desc = resultSet.getString("description");
                Date deadline = resultSet.getDate("deadline");
                String p = resultSet.getString("priority");

                task = new Task(taskid, name, desc, deadline, p);
            }

        }
        catch(SQLException e){
            System.out.println(e.toString());
        }


        return task;
    }

}
