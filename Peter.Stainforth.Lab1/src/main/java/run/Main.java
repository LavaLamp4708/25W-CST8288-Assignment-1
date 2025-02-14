/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package run;

import java.io.*;
import java.security.SecureRandom;
import java.sql.*;
import java.util.*;

import transferobjects.RecipientDTO;

/**
 * Main class containing the logic that generates a random year between 1987 and 2020 and prints rows related to the year from the Recipients table along with the metadata of that table.
 * @author Peter Stainforth
 * @studentNumber 041121409
 */
public class Main {
    /**
     * Connects to the database using a Properties object containing properties loaded from the 
     * <a href="../../../classes/lab1/database.properties">database.properties </a>
     * file.
     * @param props
     * @return {@link java.sql.Connection}
     * @throws SQLException
     */
    private static Connection getConnection(Properties props) throws SQLException {
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    /**
     * Using the established connection and the generated year, queries the database and returns the result.
     * @param connection
     * @param randomYear
     * @return {@link java.sql.ResultSet} for the year generated by main.
     * @throws SQLException
     */
    private static ResultSet executeQuery(Connection connection, int randomYear) throws SQLException {
        String query = "SELECT * FROM Recipients WHERE Year = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, randomYear);
        return ps.executeQuery();
    }

    /**
     * Prints metadata from the Recipients table.
     * @param rsmd
     * @throws SQLException
     */
    private static void printMetaData(ResultSetMetaData rsmd) throws SQLException {
        int columCount = rsmd.getColumnCount();
        StringBuilder builder = new StringBuilder();
        builder.append("Recipients Table - Column Attributes:\n");
        for(int i = 1; i<=columCount; i++){
            builder.append(
                String.format(
                    "%-20s%-20s%s\n",
                    rsmd.getColumnName(i),
                    rsmd.getColumnTypeName(i),
                    rsmd.getColumnClassName(i)
                )
            );
        }
        System.out.println(builder);
    }

    /**
     * Prints the results if the result set is not empty.
     * @param list
     */
    private static void printResults(List<RecipientDTO> list){
        StringBuilder builder = new StringBuilder();
        String formatting = "%-15s%-40s%-10s%-15s%-40s\n";
        builder.append(String.format(formatting, "AwardID", "Name", "Year", "City", "Category"));
        for(RecipientDTO recipient : list){
            builder.append(String.format(
                formatting, 
                String.valueOf(recipient.getAwardID()), 
                recipient.getName(),
                String.valueOf(recipient.getYear()),
                recipient.getCity(),
                recipient.getCategory()
            ));
        }
        System.out.print(builder);
    }

    /**
     * Prints a message if the result set is empty with the year that has been generated.
     * @param year
     */
    private static void printIfEmpty(int year){
        System.out.println("No result(s) found for the year " + year + ".");
    }

    /**
     * <p>The entry point of the program. Calls all the other private methods within the main class.</p>
     * <p>Connects to the database using the <a href="../../../classes/lab1/database.properties">database.properties</a> file, then generates a random year between 1987 and 2020 using the {@link java.security.SecureRandom} class, then queries the database and displays the resulting rows, if there are any, along with the metadata of the table.</p>
     * @param args
     */
    public static void main(String[] args){
        Properties props = new Properties();
        Connection connection = null;
        ResultSet rs = null;
        List<RecipientDTO> recipientResultsList = new ArrayList<RecipientDTO>();
        Boolean isEmptyRS = false;
        int randomYear = new SecureRandom().nextInt(1987, 2020);

        try {
            InputStream in = Main.class.getResourceAsStream("/lab1/database.properties");
            props.load(in);
            in.close();
            connection = getConnection(props);
            rs = executeQuery(connection, randomYear);
            if(!rs.isBeforeFirst()){
                isEmptyRS = true;
            } else {
                while(rs.next()){
                    recipientResultsList.add(
                        new RecipientDTO(
                            rs.getInt("AwardID"),
                            rs.getString("Name"),
                            rs.getInt("Year"),
                            rs.getString("City"),
                            rs.getString("Category")
                        )
                    );
                }
            }
            ResultSetMetaData rsmd = rs.getMetaData();
            printMetaData(rsmd);
        } catch(SQLException | IOException e){
            e.printStackTrace();
        }

        if(isEmptyRS){
            printIfEmpty(randomYear);
        } else {
            printResults(recipientResultsList);
        }
        try {connection.close();} catch(SQLException e){e.printStackTrace();}
    }
}
