#!/bin/sh

set -e

count=30000
jar="classloader-issue-1.0-SNAPSHOT.jar"

echo build the project
mvn package

echo generate classes
pushd target
$JAVA_HOME/bin/java -cp $jar issue.GenerateClasses $count
popd

echo build the project again, to build generated classes
mvn package

echo generate aotconf
pushd target
$JAVA_HOME/bin/java                     \
-XX:+AOTClassLinking                    \
-XX:AOTMode=record                      \
-XX:AOTConfiguration=/tmp/issue.aotconf \
-cp $jar \
issue.GenerateCDS $count

echo generate archive
$JAVA_HOME/bin/java                     \
-XX:+AOTClassLinking                    \
-XX:AOTMode=create                      \
-XX:AOTConfiguration=/tmp/issue.aotconf \
-XX:AOTCache=/tmp/issue.aot             \
-Xlog:cds=debug                         \
-cp $jar \
issue.GenerateCDS
#--add-opens java.base/java.util=ALL-UNNAMED \

popd
