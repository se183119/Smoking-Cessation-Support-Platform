package com.swpteam.smokingcessation.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SuccessCode {

    // Membership
    MEMBERSHIP_CREATED(1000, "Membership has been created"),
    MEMBERSHIP_UPDATED(1001, "Membership has been updated"),
    MEMBERSHIP_DELETED(1002, "Membership has been deleted"),
    MEMBERSHIP_GET_ALL(1003, "Success returning a page of membership"),
    MEMBERSHIP_GET_BY_ID(1004, "Success returning a membership with given id"),

    // Subscription
    SUBSCRIPTION_CREATED(1000, "Subscription has been created"),
    SUBSCRIPTION_UPDATED(1001, "Subscription has been updated"),
    SUBSCRIPTION_DELETED(1002, "Subscription has been deleted"),
    SUBSCRIPTION_GET_ALL(1003, "Success returning a page of subscription"),
    SUBSCRIPTION_GET_BY_ID(1004, "Success returning a subscription with given id"),
    SUBSCRIPTION_GET_BY_ACCOUNT(1004, "Success returning a subscription with given account id"),

    // Health
    HEALTH_CREATED(2000, "Health record has been created"),
    HEALTH_UPDATED(2001, "Health record has been updated"),
    HEALTH_DELETED(2002, "Health record has been deleted"),
    HEALTH_GET_ALL(1003, "Success returning a page of health"),
    HEALTH_GET_BY_ID(1004, "Success returning a health with given id"),
    HEALTH_GET_BY_ACCOUNT(1004, "Success returning a health with given account id"),

    // Record
    RECORD_CREATED(3000, "Record has been created"),
    RECORD_UPDATED(3001, "Record has been updated"),
    RECORD_DELETED(3002, "Record has been deleted"),
    RECORD_GET_ALL(1003, "Success returning a page of record"),
    RECORD_GET_BY_ID(1004, "Success returning a record with given id"),
    RECORD_GET_BY_ACCOUNT(1004, "Success returning a record with given account id"),

    //Message
    MESSAGE_CREATED(2000, "Message has been created"),
    MESSAGE_UPDATED(2001, "Message has been updated"),
    MESSAGE_DELETED(2002, "Message has been deleted"),
    MESSAGE_GET_BY_ID(2003, "Success returning a membership with given id"),

    // Account
    ACCOUNT_CREATED(3000, "Account has been created"),
    ACCOUNT_UPDATED(3001, "Account has been updated"),
    ACCOUNT_DELETED(3002, "Account has been deleted"),
    ACCOUNT_BANNED(3003, "Account has been banned"),
    PASSWORD_CHANGE_SUCCESS(3004, "Password is changed successfully"),
    ROLE_UPDATED(3005, "Account role is updated successfully"),

    // Member
    MEMBER_CREATED(3000, "Member has been created"),
    MEMBER_UPDATED(3001, "Member has been updated"),

    // Mail
    SEND_MAIL_SUCCESS(1004, "Success sending mail"),


    ;
    int code;
    String message;
}
