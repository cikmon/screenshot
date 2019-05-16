
Set FSO = CreateObject("Scripting.FileSystemObject")
Set F = FSO.GetFile(Wscript.ScriptFullName)
path = FSO.GetParentFolderName(F)


  Dim fso, tf
  Set fso = CreateObject("Scripting.FileSystemObject")
  Set tf = fso.CreateTextFile("script.cmd", True)
  
  ' Записать строку с переводом на новую строку.
  tf.WriteLine("@echo off") 
  tf.WriteLine("cd "+path) 
  tf.WriteLine("java -jar screenshot.jar") 
  

  
  
  tf.Close
  
  
 Set WshShell = CreateObject("WScript.Shell")
RetCode = WshShell.Run("script.cmd", 0, False)