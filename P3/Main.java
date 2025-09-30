import musics.Music;
import musics.PlayList;
import musics.PlayListClass;

import dataStructures.Iterator;
import dataStructures.TwoWayIterator;
import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

import java.util.Scanner;

public class Main {

    private static final String CMD_NOT_EXIST = "Unknown command. Type help to see available commands.";
    private static final String END = "Bye!";
    private static final String MUSIC="Music: %s.\n";
    private static final String MUSIC_REM = "Music: %s removed.\n";
    private static final String MUSIC_INS = "New music inserted.";
    private static final String NO_MUSIC = "Music does not exist.";
    private static final String INVALID_POSITION = "Invalid position!";

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
                case INS -> executeInsert(in,plan);
                case INSF -> executeInsertFirst(in,plan);
                case INSL -> executeInsertLast(in,plan);
                case REM -> executeRemove(in,plan);
                case REMF -> executeRemoveFirst(in,plan);
                case REML -> executeRemoveLast(in,plan);
                case GET -> executeGet(in,plan);
                case GETF -> executeGetFirst(in,plan);
                case GETL -> executeGetLast(in,plan);
                case LIST -> executeList(in,plan);
                case LISTR-> executeFullFor(in,plan);
                case SINGER-> executeSinger(in,plan);
                case UNKNOWN -> {System.out.println(CMD_NOT_EXIST);
                    in.nextLine();}
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

    private static void executeFullFor(Scanner in, PlayList plan) {
        in.nextLine();
        TwoWayIterator<Music> it=plan.TwoWayIterator();
        while (it.hasPrevious())
            System.out.println(it.previous().getName());
    }

    private static void executeGetLast(Scanner in, PlayList plan) {
        in.nextLine();
        try {
            Music m = plan.getLast();
            System.out.printf(MUSIC, m.getName());
        } catch (NoSuchElementException e) {
            System.out.println(NO_MUSIC);
        }
    }

    private static void executeGetFirst(Scanner in, PlayList plan) {
        in.nextLine();
        try {
            Music m = plan.getFirst();
            System.out.printf(MUSIC, m.getName());
        } catch (NoSuchElementException e) {
            System.out.println(NO_MUSIC);
        }
    }

    private static void executeGet(Scanner in, PlayList plan) {
        int position=in.nextInt();
        in.nextLine();
        try {
            Music m=plan.get(position);
            System.out.printf(MUSIC, m.getName());
        } catch (InvalidPositionException e) {
            System.out.println(INVALID_POSITION);
        }
    }

    private static void executeRemoveLast(Scanner in, PlayList plan) {
        in.nextLine();
        try {
            Music m = plan.removeLast();
            System.out.printf(MUSIC_REM, m.getName());
        } catch (NoSuchElementException e) {
            System.out.println(NO_MUSIC);
        }
    }

    private static void executeRemoveFirst(Scanner in, PlayList plan) {
        in.nextLine();
        try {
            Music m = plan.removeFirst();
            System.out.printf(MUSIC_REM, m.getName());
        } catch (NoSuchElementException e) {
            System.out.println(NO_MUSIC);
        }
    }

    private static void executeRemove(Scanner in, PlayList plan) {
        int position=in.nextInt();
        in.nextLine();
        try {
            Music m=plan.remove(position);
            System.out.printf(MUSIC_REM, m.getName());
        } catch (InvalidPositionException e) {
            System.out.println(INVALID_POSITION);
        }
    }

    private static void executeList(Scanner in,PlayList plan) {
        in.nextLine();
        Iterator<Music> it=plan.iterator();
        while (it.hasNext())
            System.out.println(it.next().getName());
    }

    private static void executeInsertFirst(Scanner in, PlayList plan){
        String name=in.nextLine().trim();
        String author=in.nextLine().trim();
        plan.insertFirst(name,author);
        System.out.println(MUSIC_INS);
    }
    private static void executeInsertLast(Scanner in, PlayList plan){
        String name=in.nextLine().trim();
        String author=in.nextLine().trim();
        plan.insertLast(name,author);
        System.out.println(MUSIC_INS);
    }
    private static void executeInsert(Scanner in, PlayList plan){
        int position=in.nextInt();
        String name=in.nextLine().trim();
        String author=in.nextLine().trim();
        try {
            plan.insert(position, name,author);
            System.out.println(MUSIC_INS);
        } catch (InvalidPositionException e) {
            System.out.println(INVALID_POSITION);
        }
    }

    private static void executeHelp() {
        Command[] help=Command.values();
        for(int i=0; i<help.length-1;i++)
            System.out.println(help[i].getMsg());
    }
}
