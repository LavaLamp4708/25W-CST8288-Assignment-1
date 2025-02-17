/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peterstainforth.run;

import peterstainforth.businesslayer.RecipientBusinessLogic;
import peterstainforth.businesslayer.ValidationException;
import peterstainforth.transferobjects.RecipientDTO;

/**
 * Main class housing the main() method
 * @author Peter Stainforth
 * @studentNumber 041121409
 */
public class Main {
    public static void main(String[] args) throws ValidationException {
        RecipientBusinessLogic rbl = new RecipientBusinessLogic();
        rbl.printRecipientsTableMetaData();
        System.out.println();
        System.out.println("--PRINTING INITIAL TABLE--\n");
        rbl.printAllRecipients();
        RecipientDTO recipient = new RecipientDTO();
        recipient.setName("Simpson; Homer");
        recipient.setYear(1997);
        recipient.setCity("Springfield");
        recipient.setCategory("Arts-Dance");
        rbl.addRecipient(recipient);
        System.out.println("--PRINTING TABLE AFTER ADDITION--\n");
        rbl.printAllRecipients();
        rbl.deleteRecipient(recipient);
        System.out.println("--PRINTING TABLE AFTER DELETION--\n");
        rbl.printAllRecipients();
        rbl.printRecipientsTableMetaData();
    }
}
