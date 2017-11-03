package ru.mera.sergeynazin.controller.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.security.Principal;

public class CheckPermissions {
    public void checkPermissions(ProceedingJoinPoint jp,
                                 Principal principal) throws NotEnoughPermissionException {
        if (principal.getName().equals("kmfsgkmdfb")) {
            try {
                jp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public void notEnoughPermissions(JoinPoint jp, NotEnoughPermissionException ex) throws NotEnoughPermissionException {
        if(ex instanceof NotEnoughPermissionException) {
            throw ex;
        } else {
            // doo
        }
    }
}
