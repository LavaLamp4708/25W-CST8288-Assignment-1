package peterstainforth.dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import peterstainforth.transferobjects.RecipientDTO;

/**
 * Implements recipient DAO interface. 
 * @author Peter Stainforth
 * @studentNumber 041121409
 */
public class RecipientDAOImpl implements RecipientDAO{

    private volatile static RecipientDAOImpl instance;

    private RecipientDAOImpl() {}

    /**
     * Double-checked locking singleton pattern.
     * @return
     */
    public static RecipientDAO getInstance(){
        if(instance == null){
            synchronized(RecipientDAOImpl.class) {
                if(instance == null){
                    instance = new RecipientDAOImpl();
                }
            }
        }
        return instance;
    }

    /**
     * Prints the metadata of the recipients table.
     */
    @Override
    public void printRecipientsTableMetaData() {
        Connection conn = DataSource.getConnection();
        String query = "SELECT * FROM Recipients";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            if(!(rsmd == null)) {
                int columnCount = 0;
                StringBuilder builder = new StringBuilder();
                builder.append("Recipients Table - Column Atrributes:\n");
                try {
                    columnCount = rsmd.getColumnCount();
                    for(int i = 1; i<=columnCount; i++){
                        builder.append(
                            String.format(
                                "%-20s%-20s%-20s\n",
                                rsmd.getColumnName(i),
                                rsmd.getColumnTypeName(i),
                                rsmd.getColumnClassName(i)
                            )
                        );
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(builder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(!conn.isClosed() || conn != null) conn.close();
                if(!ps.isClosed() || ps != null) ps.close();
                if(!rs.isClosed() || rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Selects all rows in the recipients talb eand returns them as a List<> object.
     */
    @Override
    public List<RecipientDTO> getAllRecipients(){
        Connection conn = DataSource.getConnection();
        String query = "SELECT AwardID, Name, Year, City, Category FROM Recipients";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<RecipientDTO> list = new ArrayList<RecipientDTO>();

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                RecipientDTO recipient = new RecipientDTO();
                recipient.setAwardID(rs.getInt(1));
                recipient.setName(rs.getString(2));
                recipient.setYear(rs.getInt(3));
                recipient.setCity(rs.getString(4));
                recipient.setCategory(rs.getString(5));
                list.add(recipient);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if(!conn.isClosed() || conn != null) conn.close();
                if(!ps.isClosed() || ps != null) ps.close();
                if(!rs.isClosed() || rs != null) rs.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * Inserts a recipient to the table without specifying AwardID. This works because the AwardID is auto-incremented.
     */
    @Override
    public void addRecipient(RecipientDTO recipient) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO recipients (Name, Year, City, Category) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(statement);

            ps.setString(1, recipient.getName());
            ps.setInt(2, recipient.getYear());
            ps.setString(3, recipient.getCity());
            ps.setString(4, recipient.getCategory());

            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if(!conn.isClosed() || conn != null) conn.close();
                if(!ps.isClosed() || ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes from the recipient table using the specific combination of all attributes besides AwardID.
     */
    @Override
    public void deleteRecipient(RecipientDTO recipient) {
        Connection conn = DataSource.getConnection();
        String statement = "DELETE FROM recipients WHERE Name = ? AND Year = ? AND City = ? AND Category = ?";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(statement);
            ps.setString(1, recipient.getName());
            ps.setInt(2, recipient.getYear());
            ps.setString(3, recipient.getCity());
            ps.setString(4, recipient.getCategory());
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if(!conn.isClosed() || conn != null) conn.close();
                if(!ps.isClosed() || ps != null) ps.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
