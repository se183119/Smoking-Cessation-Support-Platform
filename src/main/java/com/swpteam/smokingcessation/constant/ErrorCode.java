package com.swpteam.smokingcessation.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_MESSAGE_KEY(9999, "Invalid message key", HttpStatus.BAD_REQUEST),

    // Common
    ACCOUNT_REQUIRED(1000, "Account is required", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(1001, "Email is required", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT(1002, "Wrong email format", HttpStatus.BAD_REQUEST),
    START_DATE_REQUIRED(1003, "Start date is required", HttpStatus.BAD_REQUEST),
    END_DATE_REQUIRED(1004, "End date is required", HttpStatus.BAD_REQUEST),
    PAYMENT_STATUS_REQUIRED(1005, "Payment status is required", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_BLANK(1006, "Account ID cannot be blank", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(1007, "Password field cannot be empty", HttpStatus.BAD_REQUEST),
    CODE_REQUIRED(1008, "Google code field cannot be empty", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_REQUIRED(1009, "Refresh token field cannot be empty", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1010, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(1011, "Phone number must be 10 digits and consists of numbers only", HttpStatus.BAD_REQUEST),
    RESET_TOKEN_REQUIRED(1012, "Reset token cannot be empty", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1013, "Token is invalid", HttpStatus.INTERNAL_SERVER_ERROR),
    ID_REQUIRED(1007, "ID is required", HttpStatus.BAD_REQUEST),
    ID_NOT_BLANK(1008, "ID cannot be blank", HttpStatus.BAD_REQUEST),
    PAGE_NO_MIN(1009, "Page number must be at least 0", HttpStatus.BAD_REQUEST),
    PAGE_SIZE_MIN(1010, "Page size must be at least 1", HttpStatus.BAD_REQUEST),
    PAGE_SIZE_MAX(1011, "Page size can not pass 100", HttpStatus.BAD_REQUEST),
    INVALID_SORT_FIELD(1013, "Invalid sort field", HttpStatus.BAD_REQUEST),
    INVALID_RESET_TOKEN(1012, "Reset token is invalid", HttpStatus.BAD_REQUEST),

    // Authentication
    UNAUTHENTICATED(2000, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2001, "You do not have permission", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(2002, "Token is expired", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(2003, "Wrong password for email", HttpStatus.BAD_REQUEST),
    INVALID_SIGNATURE(2004, "Token signature is invalid", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(2005, "Failed to send email. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR),
    USED_TOKEN(2006, "Token has expired or has been used", HttpStatus.BAD_REQUEST),
    SELF_BAN(2007, "You cannot ban yourself", HttpStatus.BAD_REQUEST),
    FORBIDDEN(403, "You do not have permission to access this resource", HttpStatus.FORBIDDEN),

    // Account
    ACCOUNT_NOT_FOUND(3000, "Account does not exist", HttpStatus.BAD_REQUEST),
    ACCOUNT_EXISTED(3001, "Account already existed", HttpStatus.BAD_REQUEST),
    ACCOUNT_DELETED(3002, "Account has been deleted", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTED(3003, "Phone number is registered to another account", HttpStatus.BAD_REQUEST),
    IDENTICAL_PASSWORD(3004, "The new password must be different from the old password", HttpStatus.BAD_REQUEST),

    // Membership
    MEMBERSHIP_NOT_FOUND(4000, "Membership does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    MEMBERSHIP_NAME_UNIQUE(4001, "Membership name must be unique", HttpStatus.BAD_REQUEST),
    MEMBERSHIP_NAME_REQUIRE(4002, "Membership name must not be empty", HttpStatus.BAD_REQUEST),
    MEMBERSHIP_MIN_SIZE(4003, "Membership name length must be at least {min} characters", HttpStatus.BAD_REQUEST),
    DURATION_NEGATIVE(4004, "Duration must be a positive number", HttpStatus.BAD_REQUEST),
    PRICE_NEGATIVE(4005, "Price must be a positive number", HttpStatus.BAD_REQUEST),
    DURATION_REQUIRED(4006, "Duration is required", HttpStatus.BAD_REQUEST),
    PRICE_REQUIRED(4007, "Price is required", HttpStatus.BAD_REQUEST),

    // Transaction
    AMOUNT_REQUIRED(5001, "Transaction amount is required", HttpStatus.BAD_REQUEST),
    NAME_REQUIRED(5002, "Name is required", HttpStatus.BAD_REQUEST),
    CURRENCY_REQUIRED(5003, "Currency is required", HttpStatus.BAD_REQUEST),
    AMOUNT_NEGATIVE(5004, "Transaction amount must be a positive number", HttpStatus.BAD_REQUEST),

    // Subscription
    SUBSCRIPTION_NOT_FOUND(4000, "Subscription does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    START_DATE_MUST_BE_TODAY_OR_FUTURE(6001, "Start date must be today or in the future", HttpStatus.BAD_REQUEST),
    END_DATE_MUST_BE_IN_FUTURE(6002, "End date must be today or in the future", HttpStatus.BAD_REQUEST),

    // Setting
    THEME_REQUIRED(7000, "Theme is required", HttpStatus.BAD_REQUEST),
    LANGUAGE_REQUIRED(7001, "Language is required", HttpStatus.BAD_REQUEST),
    TRACKING_MODE_REQUIRED(7002, "Tracking mode is required", HttpStatus.BAD_REQUEST),
    MOTIVATION_REQUIRED(7003, "Motivation per day is required", HttpStatus.BAD_REQUEST),
    MOTIVATION_MIN(7004, "Motivation per day must be at least 1", HttpStatus.BAD_REQUEST),
    MOTIVATION_MAX(7005, "Motivation per day must be at most 100", HttpStatus.BAD_REQUEST),
    DEADLINE_REQUIRED(7006, "Report deadline is required", HttpStatus.BAD_REQUEST),

    //Member
    MEMBER_EXISTED(8000, "Member fields already exist", HttpStatus.BAD_REQUEST),
    MEMBER_NOT_FOUND(8001, "Member doesn't exist", HttpStatus.BAD_REQUEST),
    // Message
    MESSAGE_NOT_FOUND(8001, "Message does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    MESSAGE_CONTENT_REQUIRED(8002, "Message content is required", HttpStatus.BAD_REQUEST),

    // Health
    HEALTH_RECORD_NOT_FOUND(4000, "Health record does not exist", HttpStatus.NOT_FOUND),
    CIGARETTES_PER_DAY_INVALID(4002, "Cigarettes per day must be non-negative", HttpStatus.BAD_REQUEST),
    CIGARETTES_PER_PACK_INVALID(4003, "Cigarettes per pack must be non-negative", HttpStatus.BAD_REQUEST),
    FND_LEVEL_INVALID_MIN(4004, "FND level must be >= 0", HttpStatus.BAD_REQUEST),
    FND_LEVEL_INVALID_MAX(4005, "FND level must be <= 10", HttpStatus.BAD_REQUEST),
    PACK_PRICE_INVALID(4006, "Pack price must be non-negative", HttpStatus.BAD_REQUEST),
    REASON_TO_QUIT_REQUIRED(4007, "Reason to quit is required", HttpStatus.BAD_REQUEST),
    REASON_TO_QUIT_TOO_LONG(4008, "Reason to quit must not exceed 255 characters", HttpStatus.BAD_REQUEST),
    SMOKE_YEAR_INVALID(4009, "Smoke year must be non-negative", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(4001, "Access denied", HttpStatus.FORBIDDEN),
    PACK_PRICE_TOO_HIGH(4010, "Pack price must not exceed 500.0", HttpStatus.BAD_REQUEST),

    // Record
    RECORD_NOT_FOUND(5000, "Record does not exist", HttpStatus.NOT_FOUND),
    CIGARETTES_SMOKED_INVALID(5002, "Cigarettes smoked must be non-negative", HttpStatus.BAD_REQUEST),
    RECORD_DATE_REQUIRED(5003, "Date is required", HttpStatus.BAD_REQUEST),
    RECORD_DATE_INVALID(5004, "Date must be today or in the future", HttpStatus.BAD_REQUEST),
    RECORD_ALREADY_EXISTS(5005, "Record for this date already exists", HttpStatus.BAD_REQUEST),

    // Currency
    CURRENCY_RATE_ERROR(8000, "Error while updating currency rates", HttpStatus.BAD_REQUEST),
    INVALID_CURRENCY(8000, "Invalid currency", HttpStatus.BAD_REQUEST),

    // Transaction
    TRANSACTION_NOT_FOUND(4000, "Transaction does not exist or have been deleted", HttpStatus.NOT_FOUND),


    ;
    int code;
    String message;
    HttpStatusCode httpCode;
}
