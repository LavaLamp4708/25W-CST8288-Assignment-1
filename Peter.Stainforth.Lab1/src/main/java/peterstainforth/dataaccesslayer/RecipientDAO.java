package peterstainforth.dataaccesslayer;

import java.util.List;

import peterstainforth.transferobjects.RecipientDTO;

/**
 * Recipient DAO interface.
 * @author Peter Stainforth
 * @studentNumber 041121409
 */
public interface RecipientDAO {

    void printRecipientsTableMetaData();

    List<RecipientDTO> getAllRecipients();

    void addRecipient(RecipientDTO recipient);

    void deleteRecipient(RecipientDTO recipient);
}
