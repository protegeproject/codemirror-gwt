codemirror-gwt
==============

A GWT wrapper around the CodeMirror syntax highlighter and editor.  
The project also includes a Manchester OWL Syntax mode.


To Use
--------------

Add the following Maven dependency to your pom.xml
```xml
<dependency>
  <groupId>edu.stanford.protege</groupId>
  <artifactId>codemirror-gwt</artifactId>
  <version>1.0.0</version>
</dependency>
```

Add the following inherits to your GWT module
```xml
<inherits name="edu.stanford.bmir.gwtcodemirror.GWTCodeMirrorModule"/>
```


In your client side code, create an instance of GWTCodeMirror (which is a Widget).  Use it like you would any other GWT widget.  

You can also use GWTCodeMirror with the GWT UI binder if you wish.  For example

```xml
<gwtcodemirror:GWTCodeMirror ui:field="editor"/>
```

where the namspace gwtcodemirror binds as

```xml
xmlns:gwtcodemirror="urn:import:edu.stanford.bmir.gwtcodemirror.client"
```

