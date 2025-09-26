import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

public class listAndMod {
    public static void main(String[] args) {
        boolean[] permissions = getExecutePermissions("/usr/bin/sl");
        System.out.println("Owner: " + permissions[0]);
        System.out.println("Group: " + permissions[1]);
        System.out.println("Others: " + permissions[2]);
    }

    public static boolean fileExists(String appPath) {
        return new File(appPath).exists();
    }

    public static boolean[] getExecutePermissions(String appPath) {
        if (fileExists(appPath)) {
            String lsOutput = null;

            try {
                ProcessBuilder peanutButter = new ProcessBuilder("ls", "-l", appPath);
                Process process = peanutButter.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                lsOutput = reader.readLine();
            }
            catch (Exception e) {
                    System.out.println("Yah, something's wrong. Figure it out, I guess...");
                    System.out.println("Error is: " + e);
            }

            if (lsOutput != null && lsOutput.length() > 0) {
                String permissions = lsOutput.split("\\s+")[0];
                System.out.println(permissions);
                boolean[] executePermissions = new boolean[3];

                executePermissions[0] = permissions.charAt(3) == 'x';  // Owner (index 3)
                executePermissions[1] = permissions.charAt(6) == 'x';  // Group (index 6)
                executePermissions[2] = permissions.charAt(9) == 'x';  // Others (index 9)

                return executePermissions;
            }
        }
        return null;
    }

    public static void changeExecutePermissions(String appPath, String option) {
        if (fileExists(appPath)) {
            try {
                if (option.equals("remove")) {
                    // something
                    System.out.println("Execute permission has been removed for: " + appPath);
                }
                else if (option.equals("add")) {
                    // something
                    System.out.println("Execute permission has been added for: " + appPath);
                }
                else {
                    System.out.println("The choice you gave was invalid. Pick \"add\" or \"remove\".");
                }
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