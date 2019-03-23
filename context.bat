doskey mvn=c:\Users\O\Desktop\apache-maven-3.6.0\bin\mvn $*
doskey java="c:\Program Files\Java\jdk-11.0.2\bin\java" $*
doskey start=c:\Users\O\Desktop\apache-maven-3.6.0\bin\mvn spring-boot:run -pl hu.kleatech.jigsaw $*
set exec="\hu.kleatech.jigsaw\target\hu.kleatech.jigsaw-1.0-SNAPSHOT-exec.jar"
rem run with: mvn spring-boot:run -pl hu.kleatech.jigsaw
cmd /k