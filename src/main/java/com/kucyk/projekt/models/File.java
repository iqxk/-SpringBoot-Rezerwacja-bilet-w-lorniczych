package com.kucyk.projekt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Base64Utils;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 36)
    private String filename;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] fileContent;

    public String getFileContent() { return Base64Utils.encodeToString(fileContent); }
}
