public enum Command {
    INS("ins - Insert a music"),
    INSF("insF - Insert as first music"),
    INSL("insL - Insert as last music"),
    REM("rem - remove a given music"),
    REMF("remF - remove the first music"),
    REML("remL - remove the last music"),
    GET("get - get a music"),
    GETF("getF - get the first music"),
    GETL("getL - get the last music"),
    LIST("list - list all music from fast to last"),
    LISTR("listR - list all music from last to first"),
    SINGER("singer - list all music of a given singer"),
    HELP("help - list all commands"),
    EXIT("exit - exit the program"),
    UNKNOWN("");

    String helpMsg;

    Command(String s) {
        helpMsg=s;
    }

    public String getMsg() {
        return helpMsg;
    }
}
