
eggDriver for Java
================

eggDriver is a library used to communicate with eggPlant Functional in "drive" mode (called eggDrive). It is intended to be used as-is, but you may modify it as you see fit.


## Sessions ##

You must start an eggDrive session before performing additional actions, and only one session may be active at a time. The `overridePreviousSession` property (default=`true`) will control whether or not the previous session (if it exists) should be overridden.

```java

EggDriver eggDriver = new EggDriver();
eggDriver.connectDrive("127.0.0.1", 5400);
//eggDriver.connectDrive("127.0.0.1", 5400, "/path/to/suite");

System.out.println ("Starting first session...");
eggDriver.StartSession ();

try {
    System.out.println ("Starting another session without overriding...");
    eggDriver.overridePreviousSession = false;
    eggDriver.StartSession ();
} catch (EggDriveException e) {
    System.out.println ("Failed to start another session, as expected");
}

System.out.println ("Starting another session with overriding...");
eggDriver.overridePreviousSession = true;
eggDriver.StartSession ();
//eggDriver.StartSession ("/path/to/suite");

System.out.println("Ending session...");
eggDriver.EndSession ();
```


## Performing Built-In Actions ##

eggDriver supports a number of built-in actions. You can get finer control of certain commands by changing these parameters:

```java
public Double imageSearchTimeout = null;
public Map<String, Object> textSearchOptions = null;
public Double pinchDuration = null;
public Integer pinchDistance = null;
```

EggDriver API:

```java
//  Connection
public void connect(String serverID)  
public void connect(String serverID, Integer portNum)  
public void connect(String serverID, Integer portNum, String type)  
public void connect(String serverID, Integer portNum, String type, String username, String password)  
public void connect(String serverID, Integer portNum, String type, String sshHost, String sshUser, String sshPassword)  
public void connect(String serverID, Integer portNum, String type, String username, String password, String sshHost, String sshUser, String sshPassword)  
public void connect(String serverID, Integer portNum, String type, String username, String password, String sshHost, String sshUser, String sshPassword, String visible, String colorDepth)  
public void disconnect() 
public Map<String,Object> connectionInfo()  
public Map<String,Object> connectionInfo(String connectionName)  
public Dimension remoteScreenSize()  

//  Logs
public void log(String log)  
public void logExpression(String log)  
public void logError(String error)  
public void logErrorExpression(String error)  

//  Pointer Events
public void click    (Point  p)                              
public void click    (String image)                          
public void clickText(String text)                           

public void doubleClick    (Point  p)                              
public void doubleClick    (String image)                          
public void doubleClickText(String text)                           

public void rightClick    (Point  p)                              
public void rightClick    (String image)                          
public void rightClickText(String text)                           

public void moveTo    (Point  p)                              
public void moveTo    (String image)                          
public void moveToText(String text)                           

//  Mobile Gestures
public void tap    (Point  p)                       
public void tap    (String image)                   
public void tapText(String text)                    

public void swipeLeft    ()                         
public void swipeLeft    (Point  p)                 
public void swipeLeft    (String image)             

public void swipeRight    ()                        
public void swipeRight    (Point  p)                
public void swipeRight    (String image)            

public void swipeDown    ()                         
public void swipeDown    (Point  p)                 
public void swipeDown    (String image)             

public void swipeUp    ()                           
public void swipeUp    (Point  p)                   
public void swipeUp    (String image)               

public void pinchOut ()                                 
public void pinchOut (Point atPoint)                    
public void pinchOut (Point atPoint, Point toPoint)     
public void pinchOut (String atImage)                   
public void pinchOut (String atImage, String toImage)   

public void pinchIn ()                                   
public void pinchIn (Point atPoint)                      
public void pinchIn (Point atPoint, Point fromPoint)     
public void pinchIn (String atImage)                     
public void pinchIn (String atImage, String fromImage)   

//  Mobile Device Control
public void launchApp(String appName)  
public void launchApp(String deviceName, String appName)  

//  Image Searching
public List<Map<String, Object>> imageInfo(String imageName)  
public void waitFor(String imageName)  
public Boolean imageFound(String imageName)  
public List<Point> everyImageLocation(String imageName)  

//  Text
public void typeText(String text)  
public void typeExpression(Object expression)  
public String readText(Point p)  
public String readText(Rectangle r)  
public String readText(String imageName)  
public String remoteClipboard()  
public String remoteClipboard(Double timeout)  

//  Private Functions
private void commandAtPoint(String command, Point p)  
private void commandAtImage(String command, String image, Double searchTimeout)  
private void commandAtText(String command, String text, Map<String,Object> options)  
private void pinch(boolean in, boolean images, Object at, Object from, Object to)
```


## Performing Custom Actions ##

If one of the built-in actions does not suffice, custom actions may be performed using eggPlant SenseTalk syntax. 

```java
EggDriver eggDriver = new EggDriver();
eggDriver.executeString ("put \"Hello from Eggplant!!!\"");
System.out.println ("Output: " + eggDriver.getLastDriveResult().getOutput());
```


## Debugging eggDrive Calls ##

Setting the `verboseLogging` property to `true` for `EggDriver` will cause all eggDrive XML-RPC calls to be logged to the Console.

```java
eggDriver.verboseLogging = true;
```
