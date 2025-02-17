package peterstainforth.businesslayer;

import java.util.List;

import peterstainforth.dataaccesslayer.*;
import peterstainforth.transferobjects.RecipientDTO;

/**
 * RecipientBusinessLogic depends on the dataaccess layer to perform operations on the database and print the recipients table.
 * @author Peter Stainforth
 * @studentNumber 041121409
 */
public class RecipientBusinessLogic {
    private RecipientValidation validate = new RecipientValidation();
    /**
     * Empty constructor.
     */
    public RecipientBusinessLogic(){}

    /**
     * Prints all recipients using RecipientsDAO.
     */
    public void printAllRecipients(){
        RecipientDAO recipientDAO = RecipientDAOImpl.getInstance();
        List<RecipientDTO> list = recipientDAO.getAllRecipients();
        printRecipients(list);
    }

    /**
     * Adds a recipient row to the recipients table by inputting a RecipientDTO object into a method in the RecipientDAO.
     * @param recipient Row to be added.
     */
    public void addRecipient(RecipientDTO recipient) throws ValidationException{
        RecipientDAO recipientDAO = RecipientDAOImpl.getInstance();
        validate.validateRecipient(recipient);
        recipientDAO.addRecipient(recipient);
    }

    /**
     * Removes a recipient row from the recipients table by inputing a RecipientDTO object into the RecipientDAO.
     * @param recipient Row to be deleted.
     */
    public void deleteRecipient(RecipientDTO recipient){
        RecipientDAO recipientDAO = RecipientDAOImpl.getInstance();
        recipientDAO.deleteRecipient(recipient);
    }

    /**
     * Prints recipients table metadata using the RecipientDAO.
     */
    public void printRecipientsTableMetaData(){
        RecipientDAO recipientDAO = RecipientDAOImpl.getInstance();
        recipientDAO.printRecipientsTableMetaData();
    }

    /**
     * Prints all rows from the recipients table.
     * @param list
     */
    private void printRecipients(List<RecipientDTO> list){
        StringBuilder builder = new StringBuilder();
        String formatting = "%-10s%-30s%-8s%-20s%-20s\n";
        builder.append(String.format(formatting, "AwardID", "Name", "Year", "City", "Category"));
        for(RecipientDTO recipient: list){
            builder.append(String.format(formatting, 
                String.valueOf(recipient.getAwardID()),
                recipient.getName(),
                String.valueOf(recipient.getYear()),
                recipient.getCity(),
                recipient.getCategory()
            ));
        }
        System.out.println(builder);
    }
}
