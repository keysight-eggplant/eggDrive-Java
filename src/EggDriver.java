
import java.awt.*;
import java.util.Map;
import java.util.List;


public class EggDriver extends RpcConnector {

    public Double imageSearchTimeout = null;
    public Map<String, Object> textSearchOptions = null;
    public Double pinchDuration = null;
    public Integer pinchDistance = null;

    private SenseTalkFormatter formatter = new SenseTalkFormatter();

    //region Connection
    //********************************************************************************

    public void connect(String serverID) throws EggDriveException { connect(serverID,null,null,null,null,null,null,null,null,null);}
    public void connect(String serverID, Integer portNum) throws EggDriveException { connect(serverID,portNum,null,null,null,null,null,null,null,null);}
    public void connect(String serverID, Integer portNum, String type) throws EggDriveException { connect(serverID,portNum,type,null,null,null,null,null,null,null);}
    public void connect(String serverID, Integer portNum, String type, String username, String password) throws EggDriveException { connect(serverID,portNum,type,username,password,null,null,null,null,null);}
    public void connect(String serverID, Integer portNum, String type, String sshHost, String sshUser, String sshPassword) throws EggDriveException { connect(serverID,portNum,type,null,null,sshHost,sshUser,sshPassword,null,null);}
    public void connect(String serverID, Integer portNum, String type, String username, String password, String sshHost, String sshUser, String sshPassword) throws EggDriveException { connect(serverID,portNum,type,username,password,sshHost,sshUser,sshPassword,null,null);}
    public void connect(String serverID, Integer portNum, String type, String username, String password, String sshHost, String sshUser, String sshPassword, String visible, String colorDepth) throws EggDriveException{
        executeString(formatter.reset()
                .addQuotedPListParameter ("ServerID", serverID)
                .addPListParameter       ("PortNum", portNum)
                .addPListParameter       ("Type", type)
                .addQuotedPListParameter ("Username", username)
                .addQuotedPListParameter ("Password", password)
                .addQuotedPListParameter ("sshHost", sshHost)
                .addQuotedPListParameter ("sshUser", sshUser)
                .addQuotedPListParameter ("sshPassword", sshPassword)
                .addPListParameter       ("Visible", visible)
                .addPListParameter       ("ColorDepth", colorDepth)
                .asCommand("Connect"));
    }

    public void disconnect() throws EggDriveException{
        executeString(formatter.reset().asCommand("Disconnect"));
    }

    public Map<String,Object> connectionInfo() throws EggDriveException{
        return connectionInfo(null);
    }

    public Map<String,Object> connectionInfo(String connectionName) throws EggDriveException{
        executeString(formatter.reset().addQuotedParameter(connectionName).asFunction("ConnectionInfo"));
        return getLastDriveResult().getReturnValueAsPList();
    }

    public Dimension remoteScreenSize() throws EggDriveException{
        executeString(formatter.reset().asFunction("RemoteScreenSize"));
        return getLastDriveResult().getReturnValueAsDimension();
    }

    //endregion

    //region Logs
    //********************************************************************************

    public void log(String log) throws EggDriveException{
        executeString(formatter.reset().addQuotedParameter(log).asCommand("Log"));
    }

    public void logExpression(String log) throws EggDriveException{
        executeString(formatter.reset().addParameter(log).asCommand("Log"));
    }

    public void logError(String error) throws EggDriveException{
        executeString(formatter.reset().addQuotedParameter(error).asCommand("LogError"));
    }

    public void logErrorExpression(String error) throws EggDriveException{
        executeString(formatter.reset().addParameter(error).asCommand("LogError"));
    }

    //endregion

    //region Pointer Events
    //********************************************************************************

    public void click    (Point  p)                             throws EggDriveException { commandAtPoint("Click", p); }
    public void click    (String image)                         throws EggDriveException { commandAtImage("Click", image, imageSearchTimeout); }
    public void clickText(String text)                          throws EggDriveException { commandAtText ("Click", text, textSearchOptions); }

    public void doubleClick    (Point  p)                             throws EggDriveException { commandAtPoint("DoubleClick", p); }
    public void doubleClick    (String image)                         throws EggDriveException { commandAtImage("DoubleClick", image, imageSearchTimeout); }
    public void doubleClickText(String text)                          throws EggDriveException { commandAtText ("DoubleClick", text, textSearchOptions); }

    public void rightClick    (Point  p)                             throws EggDriveException { commandAtPoint("RightClick", p); }
    public void rightClick    (String image)                         throws EggDriveException { commandAtImage("RightClick", image, imageSearchTimeout); }
    public void rightClickText(String text)                          throws EggDriveException { commandAtText ("RightClick", text, textSearchOptions); }

    public void moveTo    (Point  p)                             throws EggDriveException { commandAtPoint("MoveTo", p); }
    public void moveTo    (String image)                         throws EggDriveException { commandAtImage("MoveTo", image, imageSearchTimeout); }
    public void moveToText(String text)                          throws EggDriveException { commandAtText ("MoveTo", text, textSearchOptions); }

    //endregion

    //region Mobile Gestures
    //********************************************************************************

    public void tap    (Point  p)                      throws EggDriveException       { commandAtPoint("Tap", p); }
    public void tap    (String image)                  throws EggDriveException       { commandAtImage("Tap", image, imageSearchTimeout); }
    public void tapText(String text)                   throws EggDriveException       { commandAtText ("Tap", text, textSearchOptions); }

    public void swipeLeft    ()                        throws EggDriveException             { executeString(formatter.reset().asCommand("SwipeLeft")); }
    public void swipeLeft    (Point  p)                throws EggDriveException             { commandAtPoint("SwipeLeft", p); }
    public void swipeLeft    (String image)            throws EggDriveException             { commandAtImage("SwipeLeft", image, null); }

    public void swipeRight    ()                       throws EggDriveException              { executeString(formatter.reset().asCommand("SwipeRight")); }
    public void swipeRight    (Point  p)               throws EggDriveException              { commandAtPoint("SwipeRight", p); }
    public void swipeRight    (String image)           throws EggDriveException              { commandAtImage("SwipeRight", image, null); }

    public void swipeDown    ()                        throws EggDriveException             { executeString(formatter.reset().asCommand("SwipeDown")); }
    public void swipeDown    (Point  p)                throws EggDriveException             { commandAtPoint("SwipeDown", p); }
    public void swipeDown    (String image)            throws EggDriveException             { commandAtImage("SwipeDown", image, null); }

    public void swipeUp    ()                          throws EggDriveException           { executeString(formatter.reset().asCommand("SwipeUp")); }
    public void swipeUp    (Point  p)                  throws EggDriveException           { commandAtPoint("SwipeUp", p); }
    public void swipeUp    (String image)              throws EggDriveException           { commandAtImage("SwipeUp", image, null); }

    public void pinchOut ()                                throws EggDriveException  {pinch(false,false,null,null,null);}
    public void pinchOut (Point atPoint)                   throws EggDriveException  {pinch(false,false,atPoint,null,null);}
    public void pinchOut (Point atPoint, Point toPoint)    throws EggDriveException  {pinch(false,false,atPoint,null,toPoint);}
    public void pinchOut (String atImage)                  throws EggDriveException  {pinch(false,true,atImage,null,null);}
    public void pinchOut (String atImage, String toImage)  throws EggDriveException  {pinch(false,true,atImage,null,toImage);}

    public void pinchIn ()                                  throws EggDriveException {pinch(true,false,null,null,null);}
    public void pinchIn (Point atPoint)                     throws EggDriveException {pinch(true,false,atPoint,null,null);}
    public void pinchIn (Point atPoint, Point fromPoint)    throws EggDriveException {pinch(true,false,atPoint,fromPoint,null);}
    public void pinchIn (String atImage)                    throws EggDriveException {pinch(true,true,atImage,null,null);}
    public void pinchIn (String atImage, String fromImage)  throws EggDriveException {pinch(true,true,atImage,fromImage,null);}

    //endregion

    //region Mobile Device Control
    //********************************************************************************

    public void launchApp(String appName) throws EggDriveException{
        executeString(formatter.reset().addQuotedParameter(appName).asCommand("LaunchApp"));
    }

    public void launchApp(String deviceName, String appName) throws EggDriveException{
        executeString(formatter.reset().addQuotedParameter(deviceName + " : " + appName).asCommand("LaunchApp"));
    }

    //endregion

    //region Image Searching
    //********************************************************************************

    public List<Map<String, Object>> imageInfo(String imageName) throws EggDriveException {
        executeString(formatter.reset().addQuotedParameter(imageName).asFunction("ImageInfo"));
        return getLastDriveResult().getReturnValueAsPLists();
    }

    public void waitFor(String imageName) throws EggDriveException {
        executeString(formatter.reset().addParameter(imageSearchTimeout).addQuotedParameter(imageName).asCommand("WaitFor"));
    }

    public Boolean imageFound(String imageName) throws EggDriveException {
        executeString(formatter.reset().addParameter(imageSearchTimeout).addQuotedParameter(imageName).asFunction("ImageFound"));
        return (Boolean) getLastDriveResult().getReturnValue();
    }

    public List<Point> everyImageLocation(String imageName) throws EggDriveException {
        executeString(formatter.reset().addQuotedParameter(imageName).asFunction("EveryImageLocation"));
        return getLastDriveResult().getReturnValueAsPoints();
    }

    //endregion

    //region Text
    //********************************************************************************

    public void typeText(String text) throws EggDriveException{
        executeString(formatter.reset().addQuotedParameter(text).asCommand("TypeText"));
    }

    public void typeExpression(Object expression) throws EggDriveException{
        executeString(formatter.reset().addParameter(expression).asCommand("TypeText"));
    }

    public String readText(Point p) throws EggDriveException{
        executeString(formatter.reset().addParameter(p).addPListParameters(textSearchOptions).asFunction("ReadText"));
        return (String) getLastDriveResult().getReturnValue();
    }

    public String readText(Rectangle r) throws EggDriveException{
        executeString(formatter.reset().addParameter(r).addPListParameters(textSearchOptions).asFunction("ReadText"));
        return (String) getLastDriveResult().getReturnValue();
    }

    public String readText(String imageName) throws EggDriveException{
        executeString(formatter.reset().addParameter(imageName).addPListParameters(textSearchOptions).asFunction("ReadText"));
        return (String) getLastDriveResult().getReturnValue();
    }

    public String remoteClipboard() throws EggDriveException{
        return remoteClipboard(null);
    }

    public String remoteClipboard(Double timeout) throws EggDriveException{
        executeString(formatter.reset().addParameter(timeout).asFunction("RemoteClipboard"));
        return (String) getLastDriveResult().getReturnValue();
    }

    //endregion

    //region Private Functions
    //********************************************************************************
    private void commandAtPoint(String command, Point p) throws EggDriveException {
        executeString(formatter.reset().addParameter(p).asCommand(command));
    }

    private void commandAtImage(String command, String image, Double searchTimeout) throws EggDriveException {
        executeString(formatter.reset()
                .addQuotedPListParameter("Image", image)
                .addQuotedPListParameter("WaitFor", searchTimeout)
                .asCommand(command));
    }

    private void commandAtText(String command, String text, Map<String,Object> options) throws EggDriveException {
        executeString(formatter.reset()
                .addQuotedPListParameter("Text", text)
                .addPListParameters(options)
                .asCommand(command));
    }

    private void pinch(boolean in, boolean images, Object at, Object from, Object to) throws EggDriveException{
        if ( images )
            executeString(formatter.reset()
                    .addQuotedPListParameter("At",at)
                    .addQuotedPListParameter("From",from)
                    .addQuotedPListParameter("To",to)
                    .addPListParameter("Distance",pinchDistance)
                    .addPListParameter("Duration",pinchDuration)
                    .asCommand(in?"PinchIn":"PinchOut"));
        else
            executeString(formatter.reset()
                    .addPListParameter("At", at)
                    .addPListParameter("From", from)
                    .addPListParameter("To",to)
                    .addPListParameter("Distance",pinchDistance)
                    .addPListParameter("Duration",pinchDuration)
                    .asCommand(in?"PinchIn":"PinchOut"));
    }

    //endregion

}
