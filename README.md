# What is this?

Reproducer for an issue with Leyden and large number of classes.

## What is the issue?

When there is about 30k classes or more, Leyden fails to use optimized module handling.

## How to run

$JAVA_HOME must be set with a JDK 24+
Look at `run.sh`.
1. build the project a first time and run [GenerateClasses.java](src/main/java/issue/GenerateClasses.java).
This class will create 30k very simple classes in src/main/java/issue/gen. The classes are named Foo0...Foo29999.java.

2. build the project a second time to compile those 30k classes.

3. execute the Leyden training run of [GenerateCDS.java](src/main/java/issue/GenerateCDS.java).
This class will load the 30k classes via Class.forName().

4. generate the AOT archive
> :warning: It fails here
```
[0,267s][error][cds,heap] Reference trace
[0,267s][error][cds,heap] [ 0] {0x00000006005f5950} jdk.internal.loader.ArchivedClassLoaders::appLoader (offset = 20)
[0,267s][error][cds,heap] [ 1] {0x00000006005f55f0} jdk.internal.loader.ClassLoaders$AppClassLoader::parallelLockMap (offset = 40)
[0,267s][error][cds,heap] [ 2] {0x00000006005f57d0} java.util.concurrent.ConcurrentHashMap
[0,267s][error][cds,heap] Cannot archive the sub-graph referenced from [Ljava.util.concurrent.ConcurrentHashMap$Node; object (0x000000061f710f90) size 262160, skipped.
[0,267s][error][cds     ] An error has occurred while writing the shared archive file.
```
