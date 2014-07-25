package net.gamers411.nodeka.umc.plugin.syscall;

import com.lsd.umc.script.ScriptInterface;

public class Syscall
{
  private ScriptInterface script;
  protected String version = "1.0.1";
  private ExecuteCommandThread cmd;

  public Syscall()
  {
    this.script = null;
    this.cmd = null;
  }

  public void init(ScriptInterface script) {
    this.script = script;
    this.cmd = new ExecuteCommandThread();

    script.print("SysCall Plugin v" + this.version + " Loaded.");
    script.print("-- Brought to you by Nemesis");
    script.print("-- http://nodeka.gamers411.net/");
    script.print("\001");

    script.registerCommand("SYSCALL", "net.gamers411.nodeka.umc.plugin.syscall.Syscall", "SysCalltem");
    script.registerCommand("KILLCALL", "net.gamers411.nodeka.umc.plugin.syscall.Syscall", "stopSystem");
  }

  public String SysCalltem(String command)
  {
    if (command.length() < 1) {
      this.script.print("Syntax: #syscall <command>\001");
      return "";
    }
    if (!this.cmd.isAlive())
      try {
        this.cmd = new ExecuteCommandThread(this.script, command);
        this.cmd.start();
      } catch (Exception ex) {
        this.script.print("[SysCall] Error: " + ex.getMessage() + "\001");
      }
    else {
      this.script.print("[SysCall] Notice: A command is being processed already.\001");
    }

    return "";
  }

  public String stopSystem(String command) {
    if (this.cmd.isAlive()) {
      this.script.print("[SysCall] Notice: Interrupting current process...\001");
      this.cmd.interrupt();
    } else {
      this.script.print("[SysCall] Notice: There is no process running at the moment.\001");
    }
    return "";
  }
}