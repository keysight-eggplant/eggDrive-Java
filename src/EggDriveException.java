import java.io.PrintStream;

public class EggDriveException extends Exception {

    public enum EggDriveFaultCode {
        NoError(0),
        UnknownMethod(1),
        SessionBusy(2),
        NoActiveSession(3),
        Exception(4),
        SessionSuiteFailure(5),
        UnknownFault(-1);

        private int value;
        EggDriveFaultCode(int value){ this.value = value;}
        int value() {return value;}
    }

    private static final EggDriveFaultCode[] faultCodeValues = EggDriveFaultCode.values();

    private EggDriveFaultCode faultCode;

    EggDriveException(Integer faultCode, String message){
        super(message);
        if (faultCode >= 0 && faultCode <= 5)
            this.faultCode = faultCodeValues[faultCode];
        else
            this.faultCode = EggDriveFaultCode.UnknownFault;
    }

    public EggDriveFaultCode getFaultCode() {
        return faultCode;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", getFaultCode().value, getMessage());
    }
}
