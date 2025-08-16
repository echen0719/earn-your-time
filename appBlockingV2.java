import java.io.File;

public class appBlocking{
    public static void main(String[] args) {
        // "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
        changeExecutePermissions("/usr/bin/firefox", "remove");
        System.out.println(getExecutePermissions("/usr/bin/firefox")); // something's up'
        changeExecutePermissions("/usr/bin/firefox", "add");
    }

    public static Boolean getExecutePermissions(String appPath) {
        File file = new File(appPath);
        if (file.exists()) {
            try {
                return file.canExecute();
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
            try {
                if (option.equals("remove")) {
                    file.setExecutable(false);
                    System.out.println("Execute permission has been removed for: " + appPath);
                }
                else if (option.equals("add")) {
                    file.setExecutable(true);
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