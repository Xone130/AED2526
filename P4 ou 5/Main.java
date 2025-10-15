import musics.Music;
import musics.MusicAlreadyExistsException;
import musics.PlayList;
import musics.PlayListClass;

import dataStructures.Iterator;

import java.util.Scanner;

public class Main {

    private static final String CMD_NOT_EXIST = "Unknown command. Type help to see available commands.";
    private static final String END = "Bye!";
    private static final String MUSIC="Music: %s.\n";
    private static final String MUSIC_REM = "Music: %s removed.\n";
    private static final String MUSIC_INS = "New music inserted.";
    private static final String NO_MUSIC = "Music does not exist.";
    private static final String YES_MUSIC = "Music %s exists.\n";
    private static final String MUSIC_EXIST = "Music already exist!";

    private static Command getCommand(Scanner input) {
        try {
            String comm = input.next().toUpperCase();
            return Command.valueOf(comm);
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PlayList plan= new PlayListClass();
        Command cmd;
        do {
            cmd = getCommand(in);
            switch (cmd) {
                case HELP-> {executeHelp();in.nextLine();}
                case EXIT-> System.out.println(END);
                case INSERT -> executeInsert(in,plan);
                case REMOVE -> executeRemove(in,plan);
                case HAS -> executeGet(in,plan);
                case LIST -> executeList(in,plan);
                case SINGER-> executeSinger(in,plan);
                case UNKNOWN -> {
                    System.out.println(CMD_NOT_EXIST);
                    in.nextLine();
                }
            }
        }while (!cmd.equals(Command.EXIT));
        in.close();
    }

    private static void executeSinger(Scanner in, PlayList plan) {
        String singer=in.nextLine().trim();
        Iterator<Music> it= plan.allSinger(singer);
        while (it.hasNext()) {
            System.out.println(it.next().getName());
        }
    }

    private static void executeGet(Scanner in, PlayList plan) {
        String name=in.nextLine().trim();
       if (plan.contains(name))
            System.out.printf(YES_MUSIC, name);
        else
            System.out.println(NO_MUSIC);

    }

    private static void executeRemove(Scanner in, PlayList plan) {
        String name=in.nextLine().trim();
        Music m=plan.remove(name);
        if ( m!= null)
                System.out.printf(MUSIC_REM, m.getName());
        else
            System.out.println(NO_MUSIC);
    }

    private static void executeList(Scanner in,PlayList plan) {
        in.nextLine();
        Iterator<Music> it=plan.iterator();
        while (it.hasNext())
            System.out.println(it.next().getName());
    }

    private static void executeInsert(Scanner in, PlayList plan){
        String name=in.nextLine().trim();
        String author=in.nextLine().trim();
        try{
            plan.insert(name,author);
            System.out.println(MUSIC_INS);
        } catch (MusicAlreadyExistsException e) {
            System.out.println(MUSIC_EXIST);
    }
}

private static void executeHelp() {
    Command[] help=Command.values();
    for(int i=0; i<help.length-1;i++)
        System.out.println(help[i].getMsg());
}

}
