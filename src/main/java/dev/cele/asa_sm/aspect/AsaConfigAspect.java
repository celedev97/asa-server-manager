package dev.cele.asa_sm.aspect;

import dev.cele.asa_sm.dto.AsaServerConfigDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AsaConfigAspect {

    //pointcut for setter methods in AsaServerConfigDto
    @Pointcut("execution(* dev.cele.asa_sm.dto.AsaServerConfigDto.set*(..))")
    public void asaServerConfigDtoSetters() {}

    //pointcut for setUnsaved method in AsaServerConfigDto
    @Pointcut("execution(* dev.cele.asa_sm.dto.AsaServerConfigDto.setUnsaved(..))")
    public void asaServerConfigDtoSetUnsaved() {}

    //after advice for setter methods in AsaServerConfigDto, except for setUnsaved
    @After("asaServerConfigDtoSetters() && !asaServerConfigDtoSetUnsaved()")
    public void afterAsaServerConfigDtoSetters(JoinPoint joinPoint) {
        System.out.println("After setter: " + joinPoint.getSignature().getName());
        //get the AsaServerConfigDto object
        var config = (AsaServerConfigDto)joinPoint.getTarget();
        //set the unsaved flag
        config.setUnsaved(true);
    }

}
