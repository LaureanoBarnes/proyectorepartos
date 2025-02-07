package com.example.userservice.aspect;

import com.example.userservice.audit.AuditLog;
import com.example.userservice.audit.AuditLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @AfterReturning(
            pointcut = "execution(* com.example.userservice.service.UserService.createUser(..)) || " +
                    "execution(* com.example.userservice.service.UserService.updateUser(..)) || " +
                    "execution(* com.example.userservice.service.UserService.deleteUser(..))",
            returning = "result"
    )
    public void logUserActions(JoinPoint joinPoint, Object result) {
        String accion = joinPoint.getSignature().getName();
        String usuario = "anonymous"; // No hay autenticación, se deja un valor por defecto
        String detalles = "Acción: " + accion + " | Resultado: " + result.toString();

        AuditLog log = new AuditLog(LocalDateTime.now(), usuario, accion, detalles);
        auditLogRepository.save(log);
    }
}
