public enum Command {
    INSERT("insert - Insert a music"),
    REMOVE("remove - remove a given music"),
    HAS("has - indicate whether a given song exists"),
    LIST("list - list all music in alphabetic order"),
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
