package com.github.johntrimble.examples.permgen;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

public class ExampleClassLoader extends ClassLoader {
  private final ClassLoader cl;
  
  public ExampleClassLoader(ClassLoader cl) {
    this.cl = cl;
  }
  
  public synchronized Class loadClass( String className ) throws ClassNotFoundException {
    if( !className.startsWith("com.github.johntrimble.examples.permgen") ) {
      return this.cl.loadClass(className);
    }
    Class c = findLoadedClass(className);
    if( c == null ) {
      String path = className.replaceAll("[.]", "/") + ".class";
      URL classUrl = cl.getResource(path);
      if( classUrl != null ) {
        InputStream in = null;
        try {
          in = classUrl.openStream();
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
          int read;
          while( (read = in.read(buffer)) > -1 ) {
            out.write(buffer, 0, read);
          }
          c = defineClass(className, out.toByteArray(), 0, out.size());
        } catch(IOException e) {
          throw new RuntimeException(e);
        } finally {
          if( in != null ) { 
            try { in.close(); } catch(IOException e) { /* silently ignore this one */ }
          }
        }
      }
    }
    
    if( c == null ) {
      throw new ClassNotFoundException(className);
    }
    
    return c;
  }
}
