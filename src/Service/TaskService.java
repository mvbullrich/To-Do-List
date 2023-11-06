package Service;

import Controler.Task;
import Model.DAOException;
import Model.DAOTask;

import java.util.ArrayList;

public class TaskService{
    private DAOTask daoTask;
    public TaskService(){
        daoTask = new DAOTask();
    }

    public void save(Task task) throws ServiceException{
        try {
            daoTask.save(task);
            System.out.println("Task saved successfully.");
        } catch (DAOException e) {
            System.out.println("Error when adding the task: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public void delete(int id) throws ServiceException{
        try{
            daoTask.delete(id);
            System.out.println("Task deleted successfully.");
        } catch (DAOException e){
            System.out.println("Error when deleting the task: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public void modify(Task task) throws ServiceException{
        try{
            daoTask.modify(task);
            System.out.println("Task modified successfully");
        } catch (DAOException e){
            System.out.println("Error when deleting the task");
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Task> getAllTasks() throws ServiceException{
        try{
            return daoTask.getAllTasks();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
