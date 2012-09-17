This project contains two examples which demonstrate that, despite an apparently common misconception, the HotSpot JVM's PermGen heap is garbage collected, and also the basic conditions under which this garbage collection happens. The first example, `com.github.johntrimble.examples.permgen.Example1`, reloads two classes, `Foo` and `Bar`, repeatedly, without running out of PermGen space, as no outstanding references are held to the classes `Foo` or `Bar` or to their class loader. You can run this example by executing the following from the project root:

```
mvn clean install
java -XX:+TraceClassUnloading -XX:PermSize=10M -XX:MaxPermSize=10M -cp target/com.github.johntrimble.examples.permgen-1.0.0-SNAPSHOT.jar com.github.johntrimble.examples.permgen.Example1
```

The `-XX:+TraceClassUnloading` option causes the HotSpot JVM to print out when a class is unloaded. The `-XX:PermSize=10M` and `-XX:MaxPermSize=10M` limit the size of the PermGen heap, so that the garbage collection behavior of the PermGen heap can be more readily shown.

The second example, `com.github.johntrimble.examples.permgen.Example2`, demonstrates a (rather contrived) memory leak in the PermGen by maintaining a reference to an instantiation of each `Foo` class loaded. This will result in not only no `Foo` classes being unloaded, but also in no `Bar` classes being unloaded, which will eventually lead to a PermGen error:

```
mvn clean install
java -XX:+TraceClassUnloading -XX:PermSize=10M -XX:MaxPermSize=10M -cp target/com.github.johntrimble.examples.permgen-1.0.0-SNAPSHOT.jar com.github.johntrimble.examples.permgen.Example2
```
