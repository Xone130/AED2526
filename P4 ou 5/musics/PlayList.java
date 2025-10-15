package musics;

import dataStructures.Iterator;


import java.io.Serializable;

public interface PlayList extends Serializable {
    /**
     * Insert a new music, if does not exist music with this name
     * @param name
     * @param author
     */
    void insert(String name,String author);

    /**
     * Musics iterator
     * @return
     */
    Iterator<Music> iterator();


    /**
     * Remove the music with the given name, if exists
     * @param name
     * @return
     */
    Music remove(String name);

    /**
     *
     * @param name
     * @return true if exists a music with this name
     */
    boolean contains(String name);

    /**
     * Iterator of musics, which has the given singer
     * @param singer
     * @return
     */
    Iterator<Music> allSinger(String singer);
}
