package Database;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * It is a generic class used to query the database for information from any table.
 * The T parameter represents the class that corresponds toan entry into a table
 */
public class AbstractDAO<T> {

    private final Class<T> type;
    private Connection connection;
    private PreparedStatement statement;


    public AbstractDAO(Class<T> type) {
        this.type = type;
        try{
            connection=ConnectionFactory.getConnection();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Creating the fields names list
    private String getTableColumnList(){
        Field[] fields = type.getDeclaredFields();
        StringBuilder command = new StringBuilder();
        for(int i=0;i<fields.length-1;i++){
            fields[i].setAccessible(true);
            String fieldName = fields[i].getName();
            command.append(fieldName+",");
        }
        fields[fields.length-1].setAccessible(true);
        String fieldName = fields[fields.length-1].getName();
        command.append(fieldName+") values(");
        return command.toString();
    }
    //Creating the fields value list
    private String getColumnValues(T entry) throws IllegalAccessException {
        Field[] fields = type.getDeclaredFields();
        StringBuilder command = new StringBuilder();
        for(int i=0;i<fields.length-1;i++){
            fields[i].setAccessible(true);
            Object value=fields[i].get(entry);
            if(value instanceof String){
                command.append("'"+value+"',");
            }else if(value instanceof Date) {
                command.append("'"+value+"',");
            }else{
                command.append(value + ",");
            }
        }
        fields[fields.length-1].setAccessible(true);
        Object value=fields[fields.length-1].get(entry);
        if(value instanceof String){
            command.append("'"+value+"');");
        }else if(value instanceof Date) {
            command.append("'"+value+"');");
        }
        else{
            command.append(value + ");");
        }
        return command.toString();
    }

    /**
     * Adds the data from an object into a corresponding table into the database
     * @param entry represents an object of class T that is added into a table
     */
    public void addEntry(T entry) {
        try {
            StringBuilder command=new StringBuilder("insert into "+ type.getSimpleName()+"s");
            command.append(" (");
            Field[] fields = type.getDeclaredFields();

            command.append(getTableColumnList());

            command.append(getColumnValues(entry));


            statement=connection.prepareStatement(command.toString());
            statement.executeUpdate();

            closeStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String getSetStatements(T entry) throws IllegalAccessException {
        Field[] fields = type.getDeclaredFields();
        StringBuilder command = new StringBuilder();
        for(int i=1;i<fields.length-1;i++){
            fields[i].setAccessible(true);
            Object value=fields[i].get(entry);
            String fieldName = fields[i].getName();
            if(value instanceof String){
                command.append(fieldName+"='"+value+"',");
            } else if (value instanceof Date) {
                command.append(fieldName+"='"+value+"',");
            }else{
                command.append(fieldName+"="+value+",");
            }
        }
        fields[fields.length-1].setAccessible(true);
        Object value=fields[fields.length-1].get(entry);
        String fieldName = fields[fields.length-1].getName();

        if(value instanceof String){
            command.append(fieldName+"='"+value+"'");
        } else if (value instanceof Date) {
            command.append(fieldName+"='"+value+"'");
        }else{
            command.append(fieldName+"="+value);
        }
        fields[0].setAccessible(true);
        command.append(" where id="+fields[0].get(entry));
        return command.toString();
    }
    /**
     * Updates an existing entry the data with the data of a corresponding object
     * @param entry represents an object of class T that is used to update an entry into a table
     */
    public void updateEntry(T entry) {
        try {
            StringBuilder command=new StringBuilder("update "+ type.getSimpleName()+"s");
            command.append(" set ");
            command.append(getSetStatements(entry));
            statement=connection.prepareStatement(command.toString());
            statement.executeUpdate();

            closeStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes an entry from a table based on a primary kez
     * @param id represents the primary key of the selected entry into a table
     */
    public void deleteEntry(int id) {
        try {
            statement=connection.prepareStatement("delete from "+ type.getSimpleName()+"s where id=?");
            statement.setInt(1, id);

            statement.executeUpdate();
            closeStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A more generic version of deleteEntry that deletes elements based on any field
     * @param fieldNr the corresponding position of the desired field in the class T
     * @param value the value of the selected field
     * @param <K> the class of the value
     */
    public <K> void deleteEntryByAttribute(int fieldNr,K value) {
        try {
            StringBuilder command=new StringBuilder("delete from "+ type.getSimpleName()+"s where ");
            Field[] fields = type.getDeclaredFields();
            fields[fieldNr].setAccessible(true);
            String name = fields[fieldNr].getName();
            if(value instanceof String){
                command.append(name+"='"+value+"';");
            }else if(value instanceof Date) {
                command.append(name+"='"+value+"'");
            }else {
                command.append(name + "=" + value + ";");
            }
            System.out.println(command.toString());
            statement=connection.prepareStatement(command.toString());

            statement.executeUpdate();
            closeStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByAttributes(Map<String, Object> criteria) {
        if (criteria == null || criteria.isEmpty()) {
            System.out.println("ERROR: Attempted to delete with null or empty criteria. Operation aborted for safety.");
            return;
        }

        // 1. Build the DELETE SQL statement
        StringBuilder queryBuilder = new StringBuilder("DELETE FROM ");
        queryBuilder.append(type.getSimpleName().toLowerCase()).append("s WHERE ");

        List<String> keys = new ArrayList<>(criteria.keySet());
        for (int i = 0; i < keys.size(); i++) {
            queryBuilder.append(keys.get(i)).append(" = ?");
            if (i < keys.size() - 1) {
                queryBuilder.append(" AND ");
            }
        }

        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = ConnectionFactory.getConnection();
            }

            statement = connection.prepareStatement(queryBuilder.toString());

            // 2. Map the values to the statement placeholders (1-based index)
            for (int i = 0; i < keys.size(); i++) {
                statement.setObject(i + 1, criteria.get(keys.get(i)));
            }

            // 3. Execute the update
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Successful delete operation. Rows affected: " + rowsDeleted);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing delete operation", e);
        } finally {
            closeStatement();
        }
    }

    /**
     * @return a resultSet consisting of all entries into the table
     */
    public ResultSet getAllEntries() {
        try{
            statement=connection.prepareStatement("select * from "+ type.getSimpleName()+"s");

            ResultSet rs=statement.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet returnByAttributes(Map<String, Object> criteria) {
        if (criteria == null || criteria.isEmpty()) {
            return getAllEntries();
        }

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ");
        queryBuilder.append(type.getSimpleName().toLowerCase()).append("s WHERE ");

        // 1. Build the WHERE clause (e.g., "attr1 = ? AND attr2 = ?")
        List<String> keys = new ArrayList<>(criteria.keySet());
        for (int i = 0; i < keys.size(); i++) {
            queryBuilder.append(keys.get(i)).append(" = ?");
            if (i < keys.size() - 1) {
                queryBuilder.append(" AND ");
            }
        }

        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = ConnectionFactory.getConnection();
            }

            statement = connection.prepareStatement(queryBuilder.toString());

            // 2. Map the values to the statement placeholders
            for (int i = 0; i < keys.size(); i++) {
                // PreparedStatement indexes are 1-based
                statement.setObject(i + 1, criteria.get(keys.get(i)));
            }

            ResultSet resultSet = statement.executeQuery();
            return resultSet;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement();
        }
        return null;
    }

    /**
     * Transforms a resultSet into a list of objects corresponding to the query
     * @param resultSet represents the resultSet that is transformed
     * @return a list of objects of class T
     * @throws SQLException
     */
    public List<T> createObject(ResultSet resultSet) throws SQLException {
        List<T> list = new ArrayList<T>();
        Constructor<?>[] constructors = type.getDeclaredConstructors();
        Constructor constructor = null;
        for (int i = 0; i < constructors.length; i++) {
            constructor = constructors[i];
            if (constructor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                constructor.setAccessible(true);
                T instance = (T)constructor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Finds entries in the table where a specific attribute matches a given value.
     * @param attribute The column name in the database.
     * @param value The value to search for.
     * @return A list of objects of class T, or null if an error occurs.
     */
    public List<T> findByAttribute(String attribute, Object value) {
        ResultSet resultSet = null;
        // Note: using ? for a PreparedStatement is safer and handles data types automatically
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() + "s WHERE " + attribute + " = ?";

        try {
            // We ensure we have a fresh connection if needed
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = ConnectionFactory.getConnection();
            }

            statement = connection.prepareStatement(query);

            // setObject automatically handles mapping Java types to SQL types
            statement.setObject(1, value);

            resultSet = statement.executeQuery();

            // We return the whole list because searching by attribute might return multiple rows
            return createObject(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // createObject already closes the resultSet, but we close the statement here
            closeStatement();
        }
        return null;
    }

    public List<T> findByAttributes(Map<String, Object> criteria) throws SQLException {
        if (criteria == null || criteria.isEmpty()) {
            // Optionally handle finding all records if map is empty
            return createObject(getAllEntries());
        }

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ");
        queryBuilder.append(type.getSimpleName().toLowerCase()).append("s WHERE ");

        // 1. Build the WHERE clause (e.g., "attr1 = ? AND attr2 = ?")
        List<String> keys = new ArrayList<>(criteria.keySet());
        for (int i = 0; i < keys.size(); i++) {
            queryBuilder.append(keys.get(i)).append(" = ?");
            if (i < keys.size() - 1) {
                queryBuilder.append(" AND ");
            }
        }

        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = ConnectionFactory.getConnection();
            }

            statement = connection.prepareStatement(queryBuilder.toString());

            // 2. Map the values to the statement placeholders
            for (int i = 0; i < keys.size(); i++) {
                // PreparedStatement indexes are 1-based
                statement.setObject(i + 1, criteria.get(keys.get(i)));
            }

            ResultSet resultSet = statement.executeQuery();
            return createObject(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement();
        }
        return new ArrayList<>(); // Returning empty list is safer than null
    }

    public void closeConnection(){
        try {
            ConnectionFactory.closeConnection(this.connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes a statement opened in another method of the class
     */
    public void closeStatement(){
        try {
            ConnectionFactory.closeStatement(this.statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
