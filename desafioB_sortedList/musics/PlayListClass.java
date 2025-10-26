package musics;

import dataStructures.*;


public class PlayListClass implements PlayList {
    // Musics order by name
    private SortedList<Music> musics;

    /**
     * Constructor
     */
    public PlayListClass(){
        musics= new SortedDoublyLinkedList<>(new MusicComparator());
    }

    /**
     *
     * @param name
     * @param author
     */
    public void insert(String name,String author) {
        if (musics.contains(new MusicClass(name,null)))
            throw new MusicAlreadyExistsException();
        musics.add(new MusicClass(name,author));
    }

    /**
     *
     * @return
     */
    public Iterator<Music> iterator() {
        return musics.iterator();
    }

 
    /**
     *
     * @param name
     * @return
     */
    public Music remove(String name) {
        return musics.remove(new MusicClass(name,null));
    }

    /**
     * @param name
     * @return true if exists a music with this name
     */
    public boolean contains(String name) {
        return musics.contains(new MusicClass(name,null));
    }


    public Iterator<Music> allSinger(String singer) {
        return new FilterIterator<>(musics.iterator(),new IsSinger(singer));
    }
}
