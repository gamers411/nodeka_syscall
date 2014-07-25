package net.gamers411.nodeka.umc.plugin.syscall;

import com.lsd.umc.script.ScriptInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteCommandThread extends Thread {

    private ScriptInterface script;

    private String command;

    private static String OS = System.getProperty("os.name").toLowerCase();

    public ExecuteCommandThread(ScriptInterface script, String command) {
        this.script = script;
        this.command = command;
    }

    public ExecuteCommandThread() {
        this.script = null;
        this.command = null;
    }

    public void run() {
        if (this.command.length() < 1) {
            return;
        }

        StringBuffer sb = new StringBuffer();

        try {

            ProcessBuilder pb;

            if (isWindows()) {
                pb = new ProcessBuilder("CMD", "/C", this.command);
            } else {
                pb = new ProcessBuilder("bash", "-c", this.command);
            }
        
            pb.redirectErrorStream(true);
            Process p = pb.start();

            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            this.script.print("[SysCall] Error: " + ex.getMessage() + "\001");
            return;
        }

        this.script.print(sb.toString());
    }

    public static boolean isWindows() {

        return OS.contains("win");

    }

    public static boolean isMac() {

        return OS.contains("mac");

    }

    public static boolean isUnix() {

        return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");

    }

    public static boolean isSolaris() {

        return OS.contains("sunos");

    }

}
