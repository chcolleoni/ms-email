package com.ms.email.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@Data
@Entity
@Table(name = "EMAIL_TB")
public class Email implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMAIL_ID")
    private Long emailId;

    private String emailFrom;

    private String emailTo;

    private String subject;

    private Instant sendDateEmail;

    private String statusEmail;

    private String text;


}