

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

class RpcConnector {

    /// Members ///
    public boolean verboseLogging = false;
    public boolean overridePreviousSession = true;

    private XmlRpcClient client;

    private enum ConnectionState {
        NOT_CONNECTED(0),
        CONNECTED(1),
        IN_SESSION(2);

        private int level;
        ConnectionState(int level) {
            this.level = level;
        }
        boolean isAtLeast(ConnectionState other) {
            return this.level >= other.level;
        }
        boolean isBelow(ConnectionState other) {
            return this.level < other.level;
        }
        boolean is(ConnectionState other) {
            return this.level == other.level;
        }
    }
    private ConnectionState connectionState = ConnectionState.NOT_CONNECTED;

    private DriveResult lastDriveResult = new DriveResult("",0.0,null,null);

    /// Methods ///

    public boolean connectDrive(String ip, int port, String suite) {
        if (!this.connectDrive(ip, port)) {
            return false;
        }

        return startDriveSession(suite);
    }

    public boolean connectDrive(String ip, int port) {
        if (connectionState.isAtLeast(ConnectionState.CONNECTED)) {
            System.err.println("Tried to connect, but connection is already established");
            return false;
        }

        if (verboseLogging) System.out.println("| Connect Drive");

        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http",ip, port,""));
            client = new XmlRpcClient();
            client.setConfig(config);
        } catch (MalformedURLException e) {
            connectionState = ConnectionState.NOT_CONNECTED;
            return false;
        }

        connectionState = ConnectionState.CONNECTED;
        return true;
    }

    public boolean disconnectDrive() {
        if ( connectionState.isBelow(ConnectionState.CONNECTED)) {
            System.err.println("Tried to disconnect, but not connected.");
            return false;
        }

        if (verboseLogging) System.out.println("| Disconnect Drive");

        if ( connectionState.isAtLeast(ConnectionState.IN_SESSION))
            endDriveSession();

        connectionState = ConnectionState.CONNECTED;

        client = null;

        connectionState = ConnectionState.NOT_CONNECTED;

        return true;
    }

    public boolean startDriveSession(String session) {
        if ( connectionState.isAtLeast(ConnectionState.IN_SESSION) ) {
            System.err.println("Tried to start a session, but session already exists.");
            return false;
        } else if ( connectionState.is(ConnectionState.NOT_CONNECTED) ) {
            System.err.println("Tried to start a session, but not connected.");
            return false;
        }

        if (verboseLogging) System.out.println("| Start Session: " + session);

        try {
            if (session!=null )
                client.execute("StartSession", new Object[]{session} );
            else
                client.execute("StartSession", (Object[]) null);
        } catch (XmlRpcException e) {
            if(overridePreviousSession && e.code == EggDriveException.EggDriveFaultCode.SessionBusy.value()){
                try {
                    endDriveSession(true);
                    if (session!=null )
                        client.execute("StartSession", new Object[]{session} );
                    else
                        client.execute("StartSession", (Object[]) null);
                } catch (XmlRpcException e1) {
                    System.err.println(String.format("Could not start session: %s.", e1.toString()));
                    return false;
                }
            } else {
                System.err.println(String.format("Could not start session(%d): %s.",e.code, e.getMessage()));
                return false;
            }
        }

        connectionState = ConnectionState.IN_SESSION;
        return true;
    }

    public boolean endDriveSession(){return endDriveSession(false);}
    public boolean endDriveSession(boolean force) {
        if ( connectionState.isBelow(ConnectionState.IN_SESSION) && !force) {
            System.err.println("Tried to end a session, but not in session.");
            return false;
        }

        if (verboseLogging) System.out.println("| End Session");

        try {
            client.execute("EndSession", (Object[]) null);
        } catch (XmlRpcException e) {
            System.err.println("Session could not be ended.");
            return false;
        }
        connectionState = ConnectionState.CONNECTED;
        return true;
    }

    public boolean executeString(String command) throws EggDriveException{
        if ( connectionState.isBelow(ConnectionState.IN_SESSION)) {
            System.err.println("Tried to execute a command while not in session.");
            return false;
        }

        if (verboseLogging) System.out.println("| Execute: " + command);

        try {
            HashMap ret = (HashMap) client.execute("Execute", new Object[]{command});
            lastDriveResult = new DriveResult((String) ret.get("Output"), (Double) ret.get("Duration"), ret.get("ReturnValue"), ret.get("Result"));
        } catch (XmlRpcException e) {
            lastDriveResult = new DriveResult(e.code, e.getMessage());
            throw new EggDriveException(e.code, e.getMessage());
        }
        return true;
    }


    public DriveResult getLastDriveResult(){
        return lastDriveResult;
    }

    public String getLastOutput() {
        return lastDriveResult.getOutput();
    }
}
