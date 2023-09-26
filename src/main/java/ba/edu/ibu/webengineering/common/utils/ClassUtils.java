package ba.edu.ibu.webengineering.common.utils;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {

    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<Class<?>> classes = new ArrayList<>();

        for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
            String name = info.getName().replaceFirst("BOOT-INF.classes.", "");
            if (name.startsWith(packageName) || name.startsWith(packageName.replace(".", "/"))) {
                final Class<?> clazz = Class.forName(name);
                classes.add(clazz);
            }
        }
        return classes;
    }
}


