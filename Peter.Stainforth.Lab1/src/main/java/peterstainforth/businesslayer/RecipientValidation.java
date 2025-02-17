package peterstainforth.businesslayer;

import peterstainforth.transferobjects.RecipientDTO;

/**
 * Validates data using methods to make sure the attributes of a RecipientsDTO object is valid.
 * @author Peter Stainforth
 * @studentNumber 041121409
 */
public class RecipientValidation {
    private static int NAME_MAX_LENGTH = 40;
    private static int CITY_MAX_LENGTH = 30;
    private static int CATEGORY_MAX_LENGTH = 40;

    /**
     * Validates a string and throws a ValidationException if the string is invalid.
     * @param value
     * @param fieldName
     * @param maxLength
     * @throws ValidationException
     */
    protected void validateString(String value, String fieldName, int maxLength) throws ValidationException{
        if(value == null){
            throw new ValidationException(String.format("%s cannot be null.", fieldName));
        } else if (value.length() == 0) {
            throw new ValidationException(String.format("%s cannot contain only white-spaces or be empty/", fieldName));
        } else if (value.length() > maxLength) {
            throw new ValidationException(String.format("%s cannot exceed a length of %d.", fieldName, maxLength));
        }
    }

    /**
     * uses the validateString method to validate each field in the RecipientDTO object.
     * @param recipient
     * @throws ValidationException
     */
    protected void validateRecipient(RecipientDTO recipient) throws ValidationException {
        validateString(recipient.getName(), "Name", NAME_MAX_LENGTH);
        validateString(recipient.getCity(), "City", CITY_MAX_LENGTH);
        validateString(recipient.getCategory(), "Category", CATEGORY_MAX_LENGTH);
    }
}
