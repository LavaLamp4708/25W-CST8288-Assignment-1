package dataaccesslayer;

import java.sql.ResultSetMetaData;
import java.util.List;

import transferobjects.RecipientDTO;

public interface RecipientDAO {

    void printRecipientsTableMetaData(ResultSetMetaData rsmd);

    List<RecipientDTO> getRecipientByID(int recipientID);
}
