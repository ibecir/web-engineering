package ba.edu.ibu.webengineering.rest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO figure out how this works
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiPermission {
    PermissionGroup[] value() default {PermissionGroup.READER};
}
