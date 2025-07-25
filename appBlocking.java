import java.util.Set;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;

public class hi {
    public static void main(String[] args) {
        changeExecutablePermission("/usr/bin/firefox", "remove");
        changeExecutablePermission("/usr/bin/firefox", "add");
    }

    public static Set<PosixFilePermission> getCurrentPermissions(String appPath) {
        try {
            File file = new File(appPath);
            return Files.getPosixFilePermissions(file.toPath());
        }
        catch (Exception e) {
            System.out.println("Yah, something's wrong. Figure it out, I guess...");
        }
        return null;
    }

    public static void changeExecutablePermission(String appPath, String option) {
        File file = new File(appPath);
        if (file.exists()) {
            Set<PosixFilePermission> perms = getCurrentPermissions(appPath);
            if (option.equals("remove")) {
                perms.remove(PosixFilePermission.OWNER_EXECUTE);
                perms.remove(PosixFilePermission.GROUP_EXECUTE);
                perms.remove(PosixFilePermission.OTHERS_EXECUTE);
            }
            else if (option.equals("add")) {
                perms.add(PosixFilePermission.OWNER_EXECUTE);
                perms.add(PosixFilePermission.GROUP_EXECUTE);
                perms.add(PosixFilePermission.OTHERS_EXECUTE);
            }
            else {
                System.out.println("The choice you gave was invalid. Pick \"add\" or \"remove\".");
            }
            try {
                Files.setPosixFilePermissions(file.toPath(), perms);
                System.out.println("Execute permission has been " + option + "-ed for: " + appPath);
                // not interested in dealing with more if statements (remove-ed works)
            }
            catch (Exception e) {
                System.out.println("Execute permission has not been " + option + "-ed for: " + appPath);
            }
        }
        else {
            System.out.println("File does not exist: " + appPath);
            return;
        }
    }
}