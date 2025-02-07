package com.example.userservice.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "auditLogs")
public class AuditLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private LocalDateTime timestamp;
    private String usuario;
    private String accion;
    private String detalles;

    // Constructor personalizado
    public AuditLog(LocalDateTime timestamp, String usuario, String accion, String detalles) {
        this.timestamp = timestamp;
        this.usuario = usuario;
        this.accion = accion;
        this.detalles = detalles;
    }
}