javac $(find -path ./src/tests -prune -o -type f -name '*.java')
java -classpath ./src frontend.gui.windows.Main
