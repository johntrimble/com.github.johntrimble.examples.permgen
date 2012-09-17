package com.github.johntrimble.examples.permgen;

import java.util.LinkedList;
import java.util.List;

public class Example2 {
  public static void main(String[] args) throws Exception {
    List foos = new LinkedList();
    for( ;; ) {
      ExampleClassLoader loader =
        new ExampleClassLoader(Example2.class.getClassLoader());
      try {
        Class fooClass = 
            loader.loadClass("com.github.johntrimble.examples.permgen.Foo");
        foos.add(fooClass.newInstance());
        for( int i = 0; i < 50; i++ )
          fooClass.newInstance();
        Class barClass = 
            loader.loadClass("com.github.johntrimble.examples.permgen.Bar");
        for( int i = 0; i < 50; i++ )
          barClass.newInstance();
        Thread.sleep(200);
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    }
  }
}  