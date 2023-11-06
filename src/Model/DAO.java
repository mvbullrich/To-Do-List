package Model;

public interface DAO<T>{
    public void save(T element) throws DAOException;
    public void delete(int id) throws DAOException;
    public void modify(T element) throws DAOException;
}
