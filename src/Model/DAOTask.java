package Model;

import Controler.Task;

import java.sql.*;
import java.util.ArrayList;

import static Model.Config.*;
import static Model.Config.DB_PASSWORD;

public class DAOTask implements DAO<Task>{
    @Override
    public void save(Task element) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO Task (title, description, finished) VALUES (?, ?, ?)");
            preparedStatement.setString(1, element.getTitle());
            preparedStatement.setString(2, element.getDescription());
            preparedStatement.setBoolean(3, element.isFinished());
            int res = preparedStatement.executeUpdate();
            System.out.println("Task saved successfully");
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM Task WHERE id=?");
            preparedStatement.setInt(1, id);
            int res = preparedStatement.executeUpdate();
            System.out.println("Task deleted successfully");
        } catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public void modify(Task element) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Task SET title=?, description=?, finished=? WHERE id=?");
            preparedStatement.setString(1, element.getTitle());
            preparedStatement.setString(2, element.getDescription());
            preparedStatement.setBoolean(3, element.isFinished());
            preparedStatement.setInt(4, element.getId());
            int res = preparedStatement.executeUpdate();
            System.out.println("Task modified successfully");
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Task> getAllTasks() throws DAOException{
        ArrayList<Task> tasks = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Task");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setFinished(resultSet.getBoolean("finished"));

                tasks.add(task);
            }
            return tasks;
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
    }
}
