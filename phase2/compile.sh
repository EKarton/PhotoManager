javac $(find /src/backend .  -name '*.java')
javac $(find /src/frontend . -name '*.java')
java -classpath ./src frontend.gui.windows.Main
