package com.aimed.aimed.contact.entity;

import com.aimed.aimed.contact.ContactsDto;
import com.aimed.aimed.user.entity.User;
import com.aimed.aimed.user.enums.ContactType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "user_contacts")
@Getter
@Setter
@NoArgsConstructor
public class Contact {

    public Contact(
            User user,
            ContactType type,
            String value
    ) {
        this.user = user;
        this.type = type;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Enumerated
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "type")
    private ContactType type;

    @NotNull
    private String value;

    @NotNull
    private Boolean isPrimary;
}
