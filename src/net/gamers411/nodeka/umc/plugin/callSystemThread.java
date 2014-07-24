package net.gamers411.nodeka.umc.plugin;

import com.lsd.umc.script.ScriptInterface;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class callSystemThread extends Thread
{
  private ScriptInterface script;
  private String command;

  public callSystemThread(ScriptInterface script, String command)
  {
    this.script = script;
    this.command = command;
  }

  public callSystemThread() {
    this.script = null;
    this.command = null;
  }

  public void run() {
    if (this.command.length() < 1) {
      return;
    }
    Process p = null;
    try
    {
      p = Runtime.getRuntime().exec(this.command);
    } catch (IOException ex) {
      ex.printStackTrace();
      this.script.print("[SysCall] Error: " + ex.getMessage() + "\001");
      return;
    }

    InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

    StringBuffer buffer = new StringBuffer();
    try
    {
      while (true) {
        int c = stdoutStream.read();
        if (c == -1) break;
        buffer.append((char)c);
      }

      stdoutStream.close();
    }
    catch (IOException ex) {
      ex.printStackTrace();
      this.script.print("[SysCall] Error: " + ex.getMessage() + "\001");
      return;
    }
    this.script.print(buffer.toString());
  }
}