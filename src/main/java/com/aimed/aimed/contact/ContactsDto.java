package com.aimed.aimed.contact;

import com.aimed.aimed.user.enums.ContactType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ContactsDto implements Serializable {
    private String email;
    private String phone;
    private String messenger;
    private ContactType primaryContactType;
}
