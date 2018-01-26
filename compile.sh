javac $(find src/backend src/frontend -name '*.java')
java -classpath ./src frontend.gui.windows.Main
