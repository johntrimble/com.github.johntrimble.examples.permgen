package com.github.johntrimble.examples.permgen;

import java.util.LinkedList;
import java.util.List;
import java.net.URL; 
import java.net.URLClassLoader;

public class Example1 {
  public static void main(String[] args) throws Exception {
    for( ;; ) {
      ExampleClassLoader loader =
        new ExampleClassLoader(Example1.class.getClassLoader());
      try {
        Class fooClass =
            loader.loadClass("com.meltmedia.examples.permgen.Foo");
        for( int i = 0; i < 50; i++ )
          fooClass.newInstance();
        Class barClass =
            loader.loadClass("com.meltmedia.examples.permgen.Bar");
        for( int i = 0; i < 50; i++ )
          barClass.newInstance();
        Thread.sleep(200);
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    }
  }
}
