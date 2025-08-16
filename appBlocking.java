import java.util.Set;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;

public class appBlocking {
    public static void main(String[] args) {
        changeExecutePermissions("/usr/bin/firefox", "remove");
        changeExecutePermissions("/usr/bin/firefox", "add");
    }

    public static Set<PosixFilePermission> getExecutePermissions(String appPath) {
        File file = new File(appPath);
        if (file.exists()) {
            try {
                return Files.getPosixFilePermissions(file.toPath());
            }
            catch (Exception e) {
                System.out.println("Yah, something's wrong. Figure it out, I guess...");
                System.out.println("Error is: " + e);
            }
        }
        else {
            System.out.println("File does not exist: " + appPath);
        }
        return null;
    }

    public static void changeExecutePermissions(String appPath, String option) {
        File file = new File(appPath);
        if (file.exists()) {
            Set<PosixFilePermission> perms = getExecutePermissions(appPath);
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
                System.out.println("Error is: " + e);
            }
        }
        else {
            System.out.println("File does not exist: " + appPath);
            return;
        }
    }
}
