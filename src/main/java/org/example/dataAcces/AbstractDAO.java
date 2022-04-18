package org.example.dataAcces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Generic class for database operations
 *
 * @param <T> tabel from database
 */
public class AbstractDAO<T> {
    /**
     * Logger at database
     */
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    /**
     * type of the class who aces the methods
     */
    private final Class<T> type;

    /**
     * Constructor
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * createSelectQuery
     *
     * @param field from the class T
     * @return the select query for the specified field
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        if (type.getSimpleName().equals("Order")) {
            sb.append("s");
        }
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * createselectQuery
     *
     * @return the select query for all items from class T
     */
    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        if (type.getSimpleName().equals("Order")) {
            sb.append("s");
        }
        return sb.toString();
    }

    /**
     * findAll
     *
     * @return a list of T elements
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * findById
     *
     * @param id of T element
     * @return the T element with the specified id
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("ID");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * createObjects
     *
     * @param resultSet the result after exectue the query
     * @return a list of T elements
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * delete
     *
     * @param id of the element to be deleated
     * @return null
     */
    public T delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Object value = null;

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("deleteStatementString")) {
                try {
                    value = field.get(type);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(value.toString());
            statement.setObject(1, id);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * insert method
     *
     * @param t element to be insert in database
     * @return null
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Object value = null;

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("insertStatementString")) {
                try {
                    value = field.get(type);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(value.toString());
            boolean flag = false;
            int i = 1;
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (flag) {
                    statement.setObject(i++, field.get(t));
                }
                flag = true;
            }
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    /**
     * update method
     *
     * @param t element to be update
     * @return null
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Object value = null;

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("updateStatementString")) {
                try {
                    value = field.get(type);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(value.toString());
            boolean flag = false;
            int i = 1;
            Field idField = null;
            int n = t.getClass().getDeclaredFields().length;
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (flag) {
                    statement.setObject(i++, field.get(t));
                } else {
                    idField = field;
                }
                flag = true;
            }
            statement.setObject(n, idField.get(t));
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;

    }

    /**
     * Populate tables
     * @param ts list of objects
     * @param table table to be populate
     */
    public void populateTable(List<T> ts, TableView<T> table) {
        int i = 0;
        for (Field field : ts.get(0).getClass().getDeclaredFields()) {
            table.getColumns().get(i).setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            i++;
        }
        ObservableList<T> observableList = FXCollections.observableArrayList(ts);
        table.setItems(observableList);
    }

}
