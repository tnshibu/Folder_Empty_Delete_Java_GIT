call SET_JAVA_HOME.bat
rem del Folder_Empty_Delete.class
rem %JAVA_HOME%\bin\javac Folder_Empty_Delete.java
%JAVA_HOME%\bin\java -cp . -Xmx512m Folder_Empty_Delete > empty.log.bat

rem PAUSE
